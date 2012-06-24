package be.lacerta.cq2.sim.hbn;

import java.util.List;
import java.util.Vector;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import be.lacerta.cq2.utils.CQ2Functions;


public class Item extends HbnObject implements Amulet, java.io.Serializable {

	int id;
	Race race;
	String name;
	String descr;
	int workshop;
	int level;
	int brim;
	int ess;
	String added;
	int tmp;

	public Item() {

	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Race getRace() {
		return race;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDescr() {
		return descr;
	}

	public void setWorkshop(int workshop) {
		this.workshop = workshop;
	}

	public int getWorkshop() {
		return workshop;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setBrim(int brim) {
		this.brim = brim;
	}

	public int getBrim() {
		return brim;
	}

	public void setEss(int ess) {
		this.ess = ess;
	}

	public int getEss() {
		return ess;
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
		if (!(other instanceof Item))
			return false;
		Item o = (Item) other;
		return (o.getId() == this.getId());
	}
	
	public int hashCode() {
	      return id;
	}
	
	public static List<Item> getItems(int raceid) {
		List<Item> result = new Vector<Item>();
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Item.class);

			c.add(Restrictions.eq("race", new Race(raceid)));
			c.addOrder(Order.asc("workshop"));
			c.addOrder(Order.asc("level"));
			c.addOrder(Order.asc("brim"));
			result = c.list();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return result;
	}
	
	public static Item getItem(String name) {
		Item result = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			Criteria c = session.createCriteria(Item.class);
			c.add(Restrictions.ilike("name", name));
			List<Item> l = c.list();
			if (l != null && l.size()>0)
				result = (Item)c.list().get(0);
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException iobe) { iobe.printStackTrace();
		} catch (NullPointerException npe) { npe.printStackTrace(); }
		return result;
	}

	public int getCrys() {
		return getBrim();
	}

	public static List<Item> getItemsWithWorkshop() {
		List<Item> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Item.class);
			c.add(Restrictions.gt("workshop", 0));
			c.addOrder(Order.asc("name"));
			result = c.list();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return result;
	}
	
	public static List<Item> getItems(String ...orders) {
		List<Item> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Item.class);
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
	
	public static List<Item> getItems(String className,boolean showAll) {
		List<Item> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Item.class);
			Query q = session.createQuery("select r from Race as r where creatureClass = :creatureClass");
			q.setString("creatureClass", className);
			c.add(Restrictions.in("race", q.list()));
			if (!showAll) {
				c.add(Restrictions.gt("workshop", 0));
			}
			c.addOrder(Order.asc("workshop"));
			c.addOrder(Order.asc("level"));
			c.addOrder(Order.asc("brim"));
			result = c.list();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return result;
	}
	
	public static Item getItemByShortName(String name, String race) {
		Race r = Race.createFromDB(race);
		List<Item> items = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Item.class);
			if (r != null) c.add(Restrictions.eq("race", new Race(r.getId())));
			c.addOrder(Order.desc("workshop"));
			items = c.list();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		for (Item item : items) {
			if (name.equals(CQ2Functions.convertToShortItemName(item.getName()))) {
				return item;
			}
		}
		
		return null;
	}
}