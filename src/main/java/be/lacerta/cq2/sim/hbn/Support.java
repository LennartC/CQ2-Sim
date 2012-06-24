package be.lacerta.cq2.sim.hbn;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class Support extends HbnObject implements java.io.Serializable {
	int id;
	User fromUser;
	String toMage;
	int amount;
	Date time;
	String reason;
	
	public Support() {
		setTime(new Date());
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	public String getToMage() {
		return toMage;
	}
	public void setToMage(String toMage) {
		this.toMage = toMage;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	public static List<Support> getSupport(User u) {
		List<Support> support = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Support.class);
			c.add(Restrictions.eq("fromUser", u));
			c.add(Restrictions.gt("amount", 0));
			c.addOrder(Order.desc("time"));
			support = c.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return support;
	}
	
	public static int[] getTotals(User user) {
		int[] total = {0, 0};
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Query q = session.createQuery("select sum(amount), count(*) from Support as s where s.fromUser = :user");
			q.setParameter("user", user);
			ScrollableResults result = q.scroll();
			if (result.next()) { 
				try {
					total[0] = new Integer(""+result.get(0));
					total[1] = new Integer(""+result.get(1));
				} catch (NullPointerException npe) {
				} catch (NumberFormatException nfe) {
				}
			}
			

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return total;
	}
	
	public static boolean addDonation(Date time, User from, String to, int amount, String reason) {
		List<Support> support = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Support.class);
			c.add(Restrictions.eq("fromUser", from));
			c.add(Restrictions.eq("time", time));
			support = c.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		
		if (support == null || support.size()==0) {
			Support s = new Support();
			s.setFromUser(from);
			s.setToMage(to);
			s.setAmount(amount);
			s.setTime(time);
			s.setReason(reason);
			s.save();
			return true;
		} else {
			return false;
		}
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
