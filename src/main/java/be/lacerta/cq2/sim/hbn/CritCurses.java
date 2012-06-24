package be.lacerta.cq2.sim.hbn;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CritCurses extends HbnObject implements java.io.Serializable {
	Mage mage;
	Creature creature;
	Skill skill;
	String level;
	public Mage getMage() {
		return mage;
	}
	public void setMage(Mage mage) {
		this.mage = mage;
	}
	public Creature getCreature() {
		return creature;
	}
	public void setCreature(Creature creature) {
		this.creature = creature;
	}
	public Skill getSkill() {
		return skill;
	}
	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public CritCurses(Mage mage, Creature creature, Skill skill, String level) {
		super();
		this.mage = mage;
		this.creature = creature;
		this.skill = skill;
		this.level = level;
	}
	public CritCurses() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((creature == null) ? 0 : creature.hashCode());
		result = prime * result + ((mage == null) ? 0 : mage.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CritCurses other = (CritCurses) obj;
		if (creature == null) {
			if (other.creature != null)
				return false;
		} else if (!creature.equals(other.creature))
			return false;
		if (mage == null) {
			if (other.mage != null)
				return false;
		} else if (!mage.equals(other.mage))
			return false;
		return true;
	}
	
	public static List<CritCurses> getCursesForCreature(Creature crit) {
		List<CritCurses> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(CritCurses.class);
			c.add(Restrictions.eq("creature", crit));
			result = c.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return result;
	}

	public static List<CritCurses> getCursesForSkill(Skill skill) {
		List<CritCurses> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(CritCurses.class);
			c.add(Restrictions.eq("skill", skill));
			result = c.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return result;
	}
	public static List<CritCurses> getCursesForMage(Mage mage) {
		List<CritCurses> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(CritCurses.class);
			c.add(Restrictions.eq("mage", mage));
			result = c.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return result;
	}
	public static List<CritCurses> getCritCurses(String ...orders) {
		List<CritCurses> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(CritCurses.class);
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
}
