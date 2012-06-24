package be.lacerta.cq2.sim.hbn;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import be.lacerta.cq2.utils.StringUtils;

public class Announcement extends HbnObject implements java.io.Serializable  {
	 
int annID;
String title;
String content;
String user;
Date time;



public Announcement(){
	 
}

public static List<Announcement> getAnouncements(int limit) {
	List<Announcement> result = null;
	Transaction tx = null;
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	try {
		tx = session.getTransaction();
		Criteria c = session.createCriteria(Announcement.class);
		c.addOrder(Order.desc("time"));
		c.setMaxResults(limit);
		result = c.list();
		//tx.commit();
	} catch (HibernateException e) {
		e.printStackTrace();
		if (tx != null && tx.isActive())
			tx.rollback();
	}
	return result;
}

public void setAnnID(int annID) {
 this.annID = annID;
}

public int getAnnID() {
 return annID;
}

public void setTitle(String title) {
 this.title = StringUtils.stripHTML(title);
}

public String getTitle() {
 return title;
}

public void setContent(String content) {
 this.content = content;
}

public String getContent() {
 return content;
}

public void setUser(String user) {
 this.user = user;
}

public String getUser() {
 return user;
}

public void setTime(Date time) {
 this.time = time;
}

public Date getTime() {
 return time==null?new Date():time;
}


}