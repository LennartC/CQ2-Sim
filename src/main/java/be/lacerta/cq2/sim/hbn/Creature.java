package be.lacerta.cq2.sim.hbn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import be.lacerta.cq2.utils.StringUtils;

public class Creature extends HbnObject implements Amulet, java.io.Serializable{

	int id;
	Race race;
	String name;
	int type;
	int damage;
	int health;
	int skill;
	int l2m;
	int l2u;
	int brim;
	int crys;
	String added;
	int tmp;

	public Creature() {
		race = null;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

	public void setSkill(int skill) {
		this.skill = skill;
	}

	public int getSkill() {
		return skill;
	}
	
	public int getSkillToUse() {
		return (int)Math.floor(getSkill()/2);
	}

	public void setL2m(int l2m) {
		this.l2m = l2m;
	}

	public int getL2m() {
		return l2m;
	}

	public void setL2u(int l2u) {
		this.l2u = l2u;
	}

	public int getL2u() {
		return l2u;
	}

	public void setBrim(int brim) {
		this.brim = brim;
	}

	public int getBrim() {
		return brim;
	}

	public void setCrys(int crys) {
		this.crys = crys;
	}

	public int getCrys() {
		return crys;
	}

	public void setAdded(String added) {
		this.added = added;
	}

	public String getAdded() {
		return added;
	}

	public void setTmp(int tmp) {
		this.tmp = tmp;
	}

	public int getTmp() {
		return tmp;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Creature))
			return false;
		Creature c = (Creature) other;
		return (c.getId() == this.getId());
	}
	
	public int hashCode() {
	      return id;
	}
	
	public static List<Creature> getCreatures(HttpServletRequest request) {
		List<Creature> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Creature.class);

			if (StringUtils.isInteger(request.getParameter("race"))) {
				c.add(Restrictions.eq("race", new Race(new Integer(request.getParameter("race")))));
			}

			if (request.getParameter("class") != null && !request.getParameter("class").equals("")) {
				Query q = session.createQuery("select r from Race as r where creatureClass = :creatureClass");
				q.setString("creatureClass", request.getParameter("class"));
				c.add(Restrictions.in("race", q.list()));
			} else if (request.getAttribute("critlist.class") != null && !request.getAttribute("critlist.class").equals("")) {
				Query q = session.createQuery("select r from Race as r where creatureClass = :creatureClass");
				q.setString("creatureClass", ""+request.getAttribute("critlist.class"));
				c.add(Restrictions.in("race", q.list()));
			}
			
			if (request.getParameter("name") != null && !request.getParameter("name").equals("")) {
				c.add(Restrictions.eq("name", request.getParameter("name")));
			}
			
			if (StringUtils.isInteger(request.getParameter("type"))) {
				c.add(Restrictions.eq("type", new Integer(request.getParameter("type"))));
			}
			
			if (StringUtils.isInteger(request.getParameter("mindmg"))) {
				c.add(Restrictions.gt("damage", new Integer(request.getParameter("mindmg"))));
			}
			
			if (StringUtils.isInteger(request.getParameter("maxdmg"))) {
				c.add(Restrictions.lt("damage", new Integer(request.getParameter("maxdmg"))));
			}	
			
			if (StringUtils.isInteger(request.getParameter("minhlt"))) {
				c.add(Restrictions.gt("health", new Integer(request.getParameter("minhlt"))));
			}
			
			if (StringUtils.isInteger(request.getParameter("maxhlt"))) {
				c.add(Restrictions.lt("health", new Integer(request.getParameter("maxhlt"))));
			}	
			
			if (StringUtils.isInteger(request.getParameter("minskl"))) {
				c.add(Restrictions.gt("skill", new Integer(request.getParameter("minskl"))));
			}
			
			if (StringUtils.isInteger(request.getParameter("maxskl"))) {
				c.add(Restrictions.lt("skill", new Integer(request.getParameter("maxskl"))));
			}	
			
			if (StringUtils.isInteger(request.getParameter("minlvl"))) {
				c.add(Restrictions.gt("l2u", new Integer(request.getParameter("minlvl"))));
			}
			
			if (StringUtils.isInteger(request.getParameter("maxlvl"))) {
				c.add(Restrictions.lt("l2u", new Integer(request.getParameter("maxlvl"))));
			}	
			c.addOrder(Order.asc("skill"));
			result = c.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return result;
	}

	public static List<Creature> getCreatures(String className,boolean showAll) {
		List<Creature> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Creature.class);
			Query q = session.createQuery("select r from Race as r where creatureClass = :creatureClass");
			q.setString("creatureClass", className);
			c.add(Restrictions.in("race", q.list()));
			if (!showAll) {
				c.add(Restrictions.eq("type",1));
			}
			c.addOrder(Order.asc("skill"));
			c.addOrder(Order.asc("l2u"));
			c.addOrder(Order.asc("damage"));
			result = c.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return result;
	}
	
	public static List<Creature> getItherians() {
		List<Creature> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Creature.class);
			c.add(Restrictions.eq("type", 4));
			c.addOrder(Order.asc("name"));
			result = c.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return result;
	}
	
	public static Creature getCreature(String name) {
		Creature result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Creature.class);
			c.add(Restrictions.ilike("name", name));
			result = (Creature) c.list().get(0);

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		} catch (IndexOutOfBoundsException iobe) { }
		return result;
	}
	
	public void setRace(Race race) {
		this.race = race;
	}
	
	public Race getRace() {
		return race;
	}

	public int getEss() {
		return 0;
	}

	public static List<Creature> getCreatures(String ...orders) {
		List<Creature> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Creature.class);
			for (String order: orders) {
				c.addOrder(Order.asc(order));
			}
			result = c.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return result;
	}
	
	public int maxLevel(String classValue, Creature crit){
		double classMultiplier;
		int userLevel = 1;
		double compareValue = userLevel*5.5 -60;
		
		if(classValue.equals("Main class")){
			classMultiplier = 1;
		}
		else classMultiplier = 1.5;
		
		//crit uselvl *1(.5) > own lvl*5.5 -60
		while (crit.getSkill()>compareValue){
			userLevel++;
			compareValue = userLevel*5.5 -60;
		}
		return userLevel;
	}

}