package be.lacerta.cq2.sim.hbn;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class UserImage extends HbnObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2700840720904548978L;
	int userid;
	User user;
	String text;
	
	public UserImage() {
		
	}
	
	public UserImage(User user, String text) {
		setUser(user);
		setText(text);
	}
	

	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public static UserImage getImageForUser(int userid) {
		UserImage image = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(UserImage.class);
			c.add(Restrictions.eq("userid", userid));
			List<UserImage> result = c.list();
			if (result.size() > 0) { 
				image = result.get(0);
			}
			

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return image;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		this.userid = user.getId();
	}
}
