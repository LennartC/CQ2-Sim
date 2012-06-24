package be.lacerta.cq2.sim.hbn;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

public abstract class HbnObject implements java.io.Serializable {

	public static HbnObject get(Class<?> c, Serializable id) {
		HbnObject o = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			o = (HbnObject)session.get(c, id);

		} catch (HibernateException e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			return o;
		}
		return o;	
	}
	
	public boolean save() {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			session.save(this);
//			tx.commit();
		} catch (ConstraintViolationException cve) {
			session.flush();
			cve.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
			return false;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
			return false;
		}
		return true;
	}
	public boolean saveOrUpdate() {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			session.saveOrUpdate(this);

		} catch (ConstraintViolationException cve) {
			session.flush();
			cve.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
			return false;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
			return false;
		}
		return true;
	}
	public boolean update() {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			session.update(this);
			//tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
			return false;
		}
		return true;
	}
	public boolean delete() {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			session.delete(this);

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return true;
	}
	public boolean refresh() {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			session.refresh(this);

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
			return false;
		}
		return true;
	}
}
