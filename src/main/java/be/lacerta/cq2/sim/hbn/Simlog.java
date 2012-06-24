package be.lacerta.cq2.sim.hbn;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

public class Simlog extends HbnObject implements java.io.Serializable {

	int id;
	Date date;
	int userid;
	String ip;
	String action;
	
	public Simlog() {
		setDate(new Date());
	}
	
	public static List<Simlog> getSimlogs(int limit) {
		List<Simlog> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Simlog.class);
			c.addOrder(Order.desc("date"));
			c.setMaxResults(limit);
			result = c.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return result;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Simlog))
			return false;
		Simlog s = (Simlog) other;
		return (s.getId() == this.getId());
	}
	
	public int hashCode() {
	      return id;
	}
}
