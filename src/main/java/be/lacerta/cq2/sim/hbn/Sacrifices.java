package be.lacerta.cq2.sim.hbn;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


public class Sacrifices extends HbnObject implements java.io.Serializable{
	private Integer id;
	private Itherian itemID;
	private Creature crit;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Itherian getItemID() {
		return itemID;
	}
	public void setItemID(Itherian itemID) {
		this.itemID = itemID;
	}
	public Creature getCrit() {
		return crit;
	}
	public void setCrit(Creature crit) {
		this.crit = crit;
	}
	
	public static Sacrifices getSacrifice(int id) {
		Sacrifices result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Sacrifices.class);
			c.add(Restrictions.eq("id", id));
			result = (Sacrifices) c.list().get(0);

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		} catch (IndexOutOfBoundsException iobe) { }
		return result;
	}
	
	public static List<Sacrifices> getSacrifices(Itherian i) {
		List<Sacrifices> sacrifices = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Sacrifices.class);
			c.add(Restrictions.eq("itemID", i));
			sacrifices = c.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return sacrifices;
	}
}
