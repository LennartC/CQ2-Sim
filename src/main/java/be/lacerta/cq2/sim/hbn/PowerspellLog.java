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

public class PowerspellLog extends HbnObject implements java.io.Serializable {
	int id;
	
	int userid;
	int forestSkill;
	int deathSkill;
	int airSkill;
	int earthSkill;
	int level;

	Date time;
	
	int amount;
	String result;
	
	public PowerspellLog() {
		setTime(new Date());
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
	public int getForestSkill() {
		return forestSkill;
	}
	public void setForestSkill(int forestSkill) {
		this.forestSkill = forestSkill;
	}
	public int getDeathSkill() {
		return deathSkill;
	}
	public void setDeathSkill(int deathSkill) {
		this.deathSkill = deathSkill;
	}
	public int getAirSkill() {
		return airSkill;
	}
	public void setAirSkill(int airSkill) {
		this.airSkill = airSkill;
	}
	public int getEarthSkill() {
		return earthSkill;
	}
	public void setEarthSkill(int earthSkill) {
		this.earthSkill = earthSkill;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Reveal))
			return false;
		ShardDonation r = (ShardDonation) other;
		return (r.getId() == this.getId());
	}
	
	public int hashCode() {
	      return id;
	}
	
	
	public static List<PowerspellLog> getLatest(int userid, int latest) {
		List<PowerspellLog> psps = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(PowerspellLog.class);
			c.add(Restrictions.eq("userid", userid));
			c.addOrder(Order.desc("time"));
			if (latest > 0)	c.setMaxResults(latest);
			psps = c.list();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return psps;
	}
	
	public static int[] getTotals(int userid) {
		int[] total = {0, 0};
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Query q = session.createQuery("select sum(amount), count(*) from PowerspellLog as pspl where pspl.userid = :userid");
			q.setInteger("userid", userid);
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
	
}
