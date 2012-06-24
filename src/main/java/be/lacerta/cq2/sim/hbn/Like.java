package be.lacerta.cq2.sim.hbn;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

// Generated 20-jun-2009 17:41:24 by Hibernate Tools 3.2.4.GA

/**
 * Like generated by hbm2java
 */
public class Like extends HbnObject implements java.io.Serializable {

	private User user;
	private Integer id;
	private Integer type;

	public Like() {
	}

	public Like(User user, Integer id, Integer type) {
		this.user = user;
		this.id = id;
		this.type = type;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public static Like load(User u, Integer id, Integer type) {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Like.class);
			c.add(Restrictions.eq("user", u));
			c.add(Restrictions.eq("id", id));
			c.add(Restrictions.eq("type", type));
			List<Like> likes = c.list();
			if (likes != null && likes.size() > 0) return likes.get(0);
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return null;
	}
	
	public static List<User> getLikes(Integer id, Integer type) {
		List<User> users = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(Like.class);
			c.add(Restrictions.eq("id", id));
			c.add(Restrictions.eq("type", type));
			List<Like> likes = c.list();
			
			if (likes != null) {
				users = new ArrayList<User>();
				for (Like like : likes) users.add(like.getUser());
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return users;
	}
	
	// The following is extra code specified in the hbm.xml files

	public static Integer TYPE_RAIDRESULT = 0;

	// end of extra code specified in the hbm.xml files

}
