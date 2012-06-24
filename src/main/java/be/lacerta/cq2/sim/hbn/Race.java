package be.lacerta.cq2.sim.hbn;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class Race extends HbnObject implements java.io.Serializable {

	int id;
	String creatureClass;
	String name;
	int FD;
	int DD;
	int AD;
	int ED;
	private Set<Item> items = new TreeSet<Item>();
	
	public Race() {

	}

	public Race(int id) {
		this.id = id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setCreatureClass(String creatureClass) {
		this.creatureClass = creatureClass;
	}

	public String getCreatureClass() {
		return creatureClass;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setFD(int FD) {
		this.FD = FD;
	}

	public int getFD() {
		return FD;
	}

	public void setDD(int DD) {
		this.DD = DD;
	}

	public int getDD() {
		return DD;
	}

	public void setAD(int AD) {
		this.AD = AD;
	}

	public int getAD() {
		return AD;
	}

	public void setED(int ED) {
		this.ED = ED;
	}

	public int getED() {
		return ED;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Race))
			return false;
		Race o = (Race) other;
		return (o.getId() == this.getId());
	}
	
	public int hashCode() {
	      return id;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Race> getRaces() {
		List<Race> races = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.getTransaction();
		Criteria c = session.createCriteria(Race.class);
		races = c.list();
		return races;
	}

	@SuppressWarnings("unchecked")
	public static Race createFromDB(String name) {
		Race race = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Race.class);
			c.add(Restrictions.eq("name", name));
			List<Race> results = c.list();
			if (results != null && results.size() > 0) {
				race = results.get(0);
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return race;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public Set<Item> getItems() {
		return items;
	}
}