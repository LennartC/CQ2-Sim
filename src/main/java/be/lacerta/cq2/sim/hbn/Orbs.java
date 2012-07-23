package be.lacerta.cq2.sim.hbn;


import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class Orbs extends HbnObject implements java.io.Serializable {
	Integer id, cq2ID;
	Itherian itherianID;
	String name, tokens;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCq2ID() {
		return cq2ID;
	}
	public void setCq2ID(Integer cq2id) {
		cq2ID = cq2id;
	}
	public Itherian getItherianID() {
		return itherianID;
	}
	public void setItherianID(Itherian itherianID) {
		this.itherianID = itherianID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTokens() {
		return tokens;
	}
	public void setTokens(String tokens) {
		this.tokens = tokens;
	}
	
	public static Orbs getOrb(int id) {
		Orbs result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Sacrifices.class);
			c.add(Restrictions.eq("id", id));
			result = (Orbs) c.list().get(0);

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		} catch (IndexOutOfBoundsException iobe) { }
		return result;
	}
	
	public static List<Orbs> getOrbs(Itherian i) {
		List<Orbs> orbs = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Sacrifices.class);
			c.add(Restrictions.eq("itherianID", i));
			orbs = c.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return orbs;
	}
}
