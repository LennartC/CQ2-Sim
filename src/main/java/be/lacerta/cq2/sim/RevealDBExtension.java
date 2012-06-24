package be.lacerta.cq2.sim;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import be.lacerta.cq2.sim.hbn.HibernateUtil;
import be.lacerta.cq2.sim.hbn.Kingdom;
import be.lacerta.cq2.sim.hbn.Mage;
import be.lacerta.cq2.sim.hbn.News;
import be.lacerta.cq2.sim.hbn.Note;
import be.lacerta.cq2.sim.hbn.Reveal;
import be.lacerta.cq2.sim.hbn.RevealCrit;
import be.lacerta.cq2.sim.hbn.RevealNote;
import be.lacerta.cq2.utils.PageParser;
import be.lacerta.cq2.utils.SimConstants;

public class RevealDBExtension extends AbstractSimExtension {

	public void run(String page) {
		request.setAttribute("reveal_action", getString("action"));
		request.setAttribute("reveal_unparsed", getString("reveal"));
		request.setAttribute("reveal_mageClass", getString("mageClass"));
		request.setAttribute("reveal_mage", getString("mage"));
		request.setAttribute("reveal_kingdom", getString("kingdom"));
		request.setAttribute("reveal_level", getString("level"));
		request.setAttribute("reveal_general", getString("general"));
		
		if (post) {
			if (getString("action").equals("add")) {

				Reveal r = null;

				Matcher match = Pattern.compile(PageParser.REGEXP_PLAYERINFO_REQUIRED,Pattern.DOTALL).matcher(getString("general"));
				if (match.find()) {
					if (r==null) {
						makeOld(match.group(1).replaceAll(",", ""));
						r = new Reveal();
					}
					r.setMage(Mage.getOrCreateMage(match.group(1).replaceAll(",", "")));
					r.setKingdom(match.group(2).replaceAll(",", ""));
					r.setMageClass(match.group(3));
					r.setLevel(Integer.parseInt(match.group(4)));
					
				} else if (user.getMage()!=null && !user.getMage().equals("") && user.getKingdom() != null){
					match = Pattern.compile(PageParser.REGEXP_CHARACTER_REQUIRED,Pattern.DOTALL).matcher(getString("general"));
					if (match.find()) {
						if (r==null) {
							makeOld(user.getMage().getName());
							r = new Reveal();
						}
						r.setMage(user.getMage());
						r.setKingdom(user.getKingdom());
						r.setMageClass(match.group(1));
						r.setLevel(Integer.parseInt(match.group(2)));
					}
				}
				
				match = Pattern.compile(PageParser.REGEXP_PLAYERINFO_SKILLS,Pattern.DOTALL).matcher(getString("general"));
				if (match.find()) {
					r.setForestSkill(Integer.parseInt(match.group(1)));
					r.setAirSkill(Integer.parseInt(match.group(2)));
					r.setDeathSkill(Integer.parseInt(match.group(3)));
					r.setEarthSkill(Integer.parseInt(match.group(4)));
				} else if (user.getMage()!=null && !user.getMage().equals("") && user.getKingdom() != null){
					match = Pattern.compile(PageParser.REGEXP_CHARACTER_SKILLS,Pattern.DOTALL).matcher(getString("general"));
					if (match.find()) {
						r.setForestSkill(Integer.parseInt(match.group(1)));
						r.setDeathSkill(Integer.parseInt(match.group(2)));
						r.setAirSkill(Integer.parseInt(match.group(3)));
						r.setEarthSkill(Integer.parseInt(match.group(4)));
					} else {
						r=null;
					}
				} else {
					r=null;
				}
				
//				if (r==null && !getString("mage").equals("")) {
//					if (r==null) {
//						makeOld(getString("mage"));
//						r = new Reveal();
//					}
//					r.setName(getString("mage"));
//					r.setMageClass(getString("mageClass"));
//					r.setKingdom(getString("kingdom"));
//					r.setLevel(getInt("level"));
//					r.setForestSkill(getInt("forestSkill"));
//					r.setDeathSkill(getInt("deathSkill"));
//					r.setAirSkill(getInt("airSkill"));
//					r.setEarthSkill(getInt("earthSkill"));
//				}
				
				if (r==null) {
					request.setAttribute("reveal_message", "Unable to add reveal. Could not parse general mage information. Make sure you included the skills when you copy/paste!");
				} else {
					updateMageByReveal(r);
					
					r.setUser(user);
					r.setUnparsed(getString("reveal"));
					r.setTime(new Date());
					r.saveOrUpdate();
					r.getCreatures().clear();
					for (RevealCrit crit : PageParser.parseReveal(r.getUnparsed())) {
						r.addCreature(crit);
					}
					
					News n = new News();
					n.setTitle(r.getName()+" (L"+r.getLevel()+" "+r.getMageClass()+" mage)");
					n.setNewsfor("Reveal");
					n.setTime(new Date());
					n.setDirectlink("?page=reveal&mage="+r.getName());
					n.setUser(user);
					n.save();
					
					publishReavel(r);
					request.setAttribute("reveal_message", "Reveal added");
					request.setAttribute("reveal_action",null);
				}
			} else if (getString("action").equals("addnote")) {
				
				Reveal r = (Reveal)Reveal.get(Reveal.class, getInt("id"));
				
				Note n = new Note();
				n.setDate(new Date());
				n.setUser(user);
				n.setNote(getString("note"));
				n.save();
				
				RevealNote rn = new RevealNote();
				rn.setReveal(r);
				rn.setNote(n);
				
				r.addNote(rn);
				r.saveOrUpdate();
				
				publishReavel(r);
			} else if (getString("action").equals("search")) {
				List<Reveal> reveals = null;
				Transaction tx = null;
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
				try {
					tx = session.getTransaction();
					Criteria c = session.createCriteria(Reveal.class);
					c.add(Restrictions.eq("old",false));
					int i=0;
					while (i<6 && request.getParameter("condField"+i) != null) {
						Object value = null;
						
						// lame way of finding out what type to use :/
						if (getString("condField"+i).matches("level") || getString("condField"+i).matches(".*Skill")) {
							value = getInt("condValue"+i);
						} else {
							System.out.println(getString("condField"+i)+" didn't match");
							value = getString("condValue"+i);
						}
						
						if (getString("condition"+i).equals("like")) {
							if (value instanceof String)
								c.add(Restrictions.ilike(getString("condField"+i), value));
							else
								c.add(Restrictions.eq(getString("condField"+i), value));
						} else if (getString("condition"+i).equals("notlike")) {
							if (value instanceof String)
								c.add(Restrictions.not(Restrictions.ilike(getString("condField"+i), value)));
							else
								c.add(Restrictions.ne(getString("condField"+i), value));
						} else if (getString("condition"+i).equals("gt")) {
							c.add(Restrictions.gt(getString("condField"+i), value));
						} else if (getString("condition"+i).equals("lt")) {
							c.add(Restrictions.lt(getString("condField"+i), value));
						} 
						i++;
					}
					c.addOrder(Order.desc("time"));
					c.setMaxResults(100);
					reveals = c.list();
				} catch (HibernateException e) {
					e.printStackTrace();
					if (tx != null && tx.isActive())
						tx.rollback();
				}
				request.setAttribute("reveal_latest", reveals);
			} else {
				publishReavel(null);
			}
		} else if (getString("action").equals("reparse") && user.hasAccess(SimConstants.RIGHTS_SITEADMIN) && getInt("id")>0) {
			Reveal r = (Reveal)Reveal.get(Reveal.class, getInt("id"));
			r.getCreatures().clear();
			for (RevealCrit crit : PageParser.parseReveal(r.getUnparsed())) {
				r.addCreature(crit);
			}
			publishReavel(r);
		} else if (getString("action").equals("viewold")) {
			request.setAttribute("reveal_allformage", Reveal.getAllForMage(getString("mage")));
		} else if (getString("mage").length()>0 || getInt("id")>0) {
			if (getString("action").equals("add")) {
				Reveal r = Reveal.getRevealByMage(getString("mage"));
				request.setAttribute("reveal_mage", getString("mage"));
				if (r!=null) {
					request.setAttribute("reveal_unparsed", r.getUnparsed());
					request.setAttribute("reveal_mageClass", r.getMageClass());
					request.setAttribute("reveal_kingdom", r.getKingdom());
					request.setAttribute("reveal_level", r.getLevel());	
					request.setAttribute("reveal_forestSkill", r.getForestSkill());	
					request.setAttribute("reveal_deathSkill", r.getDeathSkill());	
					request.setAttribute("reveal_airSkill", r.getAirSkill());	
					request.setAttribute("reveal_earthSkill", r.getEarthSkill());	
				}
			} else {
				publishReavel(null);
			}
		} else if (getString("action").equals("new")) {
			request.setAttribute("reveal_action","add");
		} else {
			request.setAttribute("reveal_latest", Reveal.getLatest(14));
		}
		setPath("/reveal.jsp");
	}
	
	private void publishReavel(Reveal r) {
		if (r==null) {
			if (getInt("id") > 0) {
				r = (Reveal)Reveal.get(Reveal.class, getInt("id"));
			} else {
				r = Reveal.getRevealByMage(getString("mage"));
			}
		}
			
		if (r!=null) {
			request.setAttribute("reveal_reveal", r);
		} else {
			request.setAttribute("reveal_action","add");
			request.setAttribute("reveal_message", "no reveal found");
		}
	}
	
	private void makeOld(String name) {
		Reveal r = Reveal.getRevealByMage(name);
		if (r!=null) {
			r.setOld(true);
			r.update();
		}
	}
	
	public static void updateMageByReveal(Reveal r) {
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
