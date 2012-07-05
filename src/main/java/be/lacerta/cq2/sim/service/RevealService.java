package be.lacerta.cq2.sim.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import be.lacerta.cq2.sim.SimException;
import be.lacerta.cq2.sim.hbn.Configuration;
import be.lacerta.cq2.sim.hbn.HibernateUtil;
import be.lacerta.cq2.sim.hbn.Kingdom;
import be.lacerta.cq2.sim.hbn.Mage;
import be.lacerta.cq2.sim.hbn.Note;
import be.lacerta.cq2.sim.hbn.Reveal;
import be.lacerta.cq2.sim.hbn.RevealCrit;
import be.lacerta.cq2.sim.hbn.RevealNote;
import be.lacerta.cq2.sim.hbn.User;
import be.lacerta.cq2.sim.messaging.RevealMessage;
import be.lacerta.cq2.sim.messaging.TopicService;
import be.lacerta.cq2.utils.Condition;
import be.lacerta.cq2.utils.PageParser;
import be.lacerta.cq2.utils.Pair;
import be.lacerta.cq2.utils.SimConstants;

public enum RevealService {
	INSTANCE;

	public Reveal addReveal(User user, Mage mage, String cq2class, Integer level, String kingdom, Integer forestSkill, Integer deathSkill, Integer airSkill, Integer earthSkill, String reveal) throws SimException {
		Reveal entity = null;

		makeOld(mage.getName());
		
		entity = new Reveal();
		entity.setUser(user);
		entity.setMage(mage);
		entity.setKingdom(kingdom);
		entity.setMageClass(cq2class);
		entity.setLevel(level);
		entity.setForestSkill(forestSkill);
		entity.setDeathSkill(deathSkill);
		entity.setAirSkill(airSkill);
		entity.setEarthSkill(earthSkill);
		entity.setUnparsed(reveal);
		entity.setTime(new Date());
		entity.saveOrUpdate();
		entity.getCreatures().clear();
		for (RevealCrit crit : PageParser.parseReveal(entity.getUnparsed())) {
			entity.addCreature(crit);
		}
		
		updateMageByReveal(entity);
		
		return entity;
	}
	
	public void propagate(int revealId) {
		Reveal entity = (Reveal)Reveal.get(Reveal.class, revealId);
		if (entity != null) propagate(entity);
	}
	
	public void propagate(Reveal entity) {
		try {
			TopicService.sendMessage("reveal."+Configuration.getValue(Configuration.QUEUE_USER),RevealMessage.fromEntity(entity));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Reveal addReveal(User user, String character, String reveal) throws SimException {
		Reveal entity = null;
		
		Mage mage;
		String cq2class;
		Integer level;
		String kingdom;
		Integer forestSkill;
		Integer deathSkill;
		Integer airSkill;
		Integer earthSkill;

		
		Matcher match = Pattern.compile(PageParser.REGEXP_PLAYERINFO_REQUIRED,Pattern.DOTALL).matcher(character);
		if (match.find()) {
			mage = Mage.getOrCreateMage(match.group(1).replaceAll(",", ""));
			kingdom = match.group(2).replaceAll(",", "");
			cq2class = match.group(3);
			level = Integer.parseInt(match.group(4));
		} else if (user.getMage()!=null && !user.getMage().equals("") && user.getMage().getKingdom() != null){
			match = Pattern.compile(PageParser.REGEXP_CHARACTER_REQUIRED,Pattern.DOTALL).matcher(character);
			if (match.find()) {
				if (entity==null) {
					makeOld(user.getMage().getName());
					entity = new Reveal();
				}
				mage = user.getMage();
				kingdom = user.getMage().getKingdom().getName();
				cq2class = match.group(1);
				level = Integer.parseInt(match.group(2));
			} else {
				throw new SimException("Could not parse general mage information. Make sure you included the skills when you copy/paste!");
			}
		} else {
			throw new SimException("Could not parse general mage information. Make sure you included the skills when you copy/paste!");
		}
		
		match = Pattern.compile(PageParser.REGEXP_PLAYERINFO_SKILLS,Pattern.DOTALL).matcher(character);
		if (match.find()) {
			forestSkill = Integer.parseInt(match.group(1));
			deathSkill = Integer.parseInt(match.group(2));
			airSkill = Integer.parseInt(match.group(3));
			earthSkill = Integer.parseInt(match.group(4));
		} else if (user.getMage()!=null && !user.getMage().equals("") && user.getMage().getKingdom() != null){
			match = Pattern.compile(PageParser.REGEXP_CHARACTER_SKILLS,Pattern.DOTALL).matcher(character);
			if (match.find()) {
				forestSkill = Integer.parseInt(match.group(1));
				deathSkill = Integer.parseInt(match.group(2));
				airSkill = Integer.parseInt(match.group(3));
				earthSkill = Integer.parseInt(match.group(4));
			} else {
				throw new SimException("Could not parse general mage information. Make sure you included the skills when you copy/paste!");
			}
		} else {
			throw new SimException("Could not parse general mage information. Make sure you included the skills when you copy/paste!");
		}
		
		return addReveal(user, mage, cq2class, level, kingdom, forestSkill, deathSkill, airSkill, earthSkill, reveal);
	}
	
	
	public Reveal addNote(User user, int revealId, String note) {
		Reveal entity = (Reveal)Reveal.get(Reveal.class, revealId);
		
		Note n = new Note();
		n.setDate(new Date());
		n.setUser(user);
		n.setNote(note);
		n.save();
		
		RevealNote rn = new RevealNote();
		rn.setReveal(entity);
		rn.setNote(n);
		
		entity.addNote(rn);
		entity.saveOrUpdate();
		
		return entity;
	}	
	
	public List<Reveal> findByMage(String mage) {
		return Reveal.getAllForMage(mage);
	}
	
	@SuppressWarnings("unchecked")
	public List<Reveal> find(Map<Pair<String,Condition>,Object> map) {
		List<Reveal> reveals = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Reveal.class);
			c.add(Restrictions.eq("old",false));
		
			for (Pair<String,Condition> key : map.keySet()) {
				String field = key.getFirst();
				Condition condition = key.getSecond();
				Object value = map.get(key);
				
				switch (condition) {
				case LIKE:
					if (value instanceof String)
						c.add(Restrictions.ilike(field, value));
					else
						c.add(Restrictions.eq(field, value));
					break;
				case NOT_LIKE:
					if (value instanceof String)
						c.add(Restrictions.not(Restrictions.ilike(field, value)));
					else
						c.add(Restrictions.ne(field, value));
					break;
				case GREATER_THAN:
					c.add(Restrictions.gt(field, value));
					break;
				case LOWER_THAN:
					c.add(Restrictions.lt(field, value));
					break;
				}
			}
			
			c.addOrder(Order.desc("time"));
			c.setMaxResults(100);
			reveals = (List<Reveal>)c.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return reveals;
	}
	
	public Reveal reparse(User user, int revealId) {
		if (user.hasAccess(SimConstants.RIGHTS_SITEADMIN)) {
			Reveal r = (Reveal)Reveal.get(Reveal.class, revealId);
			r.getCreatures().clear();
			for (RevealCrit crit : PageParser.parseReveal(r.getUnparsed())) {
				r.addCreature(crit);
			}
			return r;
		} else {
			throw new SimException("access denied");
		}
	}
	
	private void makeOld(String name) {
		Reveal r = Reveal.getRevealByMage(name);
		if (r!=null) {
			r.setOld(true);
			r.update();
		}
	}

	private void updateMageByReveal(Reveal r) {
		Kingdom kd = Kingdom.loadByName(r.getKingdom());
		if (kd==null) {
			kd = new Kingdom();
			kd.setName(r.getKingdom());
			kd.saveOrUpdate();
		}
		Mage m = r.getMage();
		m.setCq2class(r.getMageClass());
		m.setLevel(r.getLevel());
		m.setKingdom(kd);
		m.saveOrUpdate();
	}
}
