package be.lacerta.cq2.sim.hbn;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CursedMage extends HbnObject implements java.io.Serializable {
	int id;
	User user;
	Mage mage;
	Date endTime;
	int shards;
	
	public CursedMage() {
		setEndTime(new Date());
	}
	
	public int getShards() {
		return shards;
	}
	public void setShards(int shards) {
		this.shards = shards;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Mage getMage() {
		return mage;
	}
	public void setMage(Mage mage) {
		this.mage = mage;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date end) {
		this.endTime = end;
	}

	public static CursedMage getByMageName(String name) {
		CursedMage mage = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(CursedMage.class);
			c.add(Restrictions.eq("mage", Mage.getOrCreateMage(name)));
			c.add(Restrictions.gt("endTime", new Date()));
			List<CursedMage> result = c.list();
			if (result.size() > 0) { 
				mage = result.get(0);
			}
			
			
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return mage;
	}

	public static List<CursedMage> getByUser(User user) {
		List<CursedMage> curses = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(CursedMage.class);
			c.add(Restrictions.eq("user", user));
			curses = c.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return curses;
	}
	
	public static List<CursedMage> getActiveCurses() {
		List<CursedMage> curses = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(CursedMage.class);
			c.add(Restrictions.ge("endTime", new Date()));
			c.addOrder(Order.desc("endTime"));
			curses = c.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return curses;
	}
}
