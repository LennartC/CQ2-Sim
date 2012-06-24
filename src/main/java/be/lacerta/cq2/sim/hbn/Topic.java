package be.lacerta.cq2.sim.hbn; 

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Topic extends HbnObject implements java.io.Serializable {
	
public final static int POSTSPERPAGE=20;
public final static int MAXTOPICS=100;


int topicID;
String name;
int starter;
boolean sticky;
boolean closed;
int clearance;
Date started;
Date lastedit;
int location;

User user;
int replies;

public Topic(){
	 setStarted(new Date());
	 setLastedit(new Date());
}

public static List<Topic> getTopics(int location, int offset) {

	List<Topic> topics = new Vector<Topic>();
	
	Transaction tx = null;
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	try {
		tx = session.getTransaction();
		Query q = session.createQuery(
				"select u, t, count(p.postID) " +
				"  from Topic as t, User as u, Post as p " +
				" where t.starter = u.id " +
				"   and t.location = :location " +
				"   and p.topicID = t.topicID " +
				" group by t.topicID " +
				" order by t.sticky desc, t.lastedit desc");
		q.setMaxResults(Topic.MAXTOPICS);
		if (offset>0) {
			q.setFirstResult(offset);
		}
		q.setInteger("location", location);

		for (Iterator<Object[]> it = q.iterate(); it.hasNext(); ) {
			Object[] el = it.next();
			User u = (User)el[0];
			Topic topic = (Topic) el[1];
			Long replies = (Long) el[2];
			topic.setUser(u);
			topic.setReplies(replies==null?0:replies.intValue());
			topics.add(topic);
		}


	} catch (HibernateException e) {
		e.printStackTrace();
		if (tx != null && tx.isActive())
			tx.rollback();
	}
	return topics;
}

public static Topic createFromDB(int topicID) {
	Topic topic = null;
	Transaction tx = null;
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	try {
		tx = session.getTransaction();
		Query q = session.createQuery(
				"select t, count(p.postID) " +
				"  from Topic as t, Post as p " +
				" where t.topicID = p.topicID " +
				"   and t.topicID = :topicid " +
				" group by t.topicID");
		q.setInteger("topicid", topicID);

		for (Iterator<Object[]> it = q.iterate(); it.hasNext(); ) {
			Object[] el = it.next();
			topic = (Topic)el[0];
			Long replies = (Long) el[1];
			topic.setReplies(replies==null?0:replies.intValue());
		}


	} catch (HibernateException e) {
		e.printStackTrace();
		if (tx != null && tx.isActive())
			tx.rollback();
	}
	return topic;
}

public boolean delete(boolean cascade) {
	if (cascade) {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Query qp = session.createQuery(
					"delete from Post as p " +
					" where p.topicID = :topicid");
			qp.setInteger("topicid", topicID);
			qp.executeUpdate();
			
			Query ql = session.createQuery(
					"delete from LastseenTopic as l " +
					" where l.topicID = :topicid");
			ql.setInteger("topicid", topicID);
			ql.executeUpdate();
			

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	return delete();
}

public static boolean needsHighlight(Topic t, User u) {
	boolean highlight = true;
	Transaction tx = null;
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	try {
		tx = session.getTransaction();
		Query q = session.createQuery(
				"select ls " +
				"  from LastseenTopic as ls " +
				" where ls.userID = :userID " +
				"   and ls.topicID = :topicID " +
				"   and ls.lasttime > :lastedit");
		q.setInteger("userID", u.getId());
		q.setInteger("topicID", t.getTopicID());
		q.setDate("lastedit", t.getLastedit());
		List<LastseenTopic> lsts = q.list();
		if (lsts.size() > 0) {
			highlight = lsts.get(0).getLasttime().before(t.getLastedit());
		}


	} catch (HibernateException e) {
		e.printStackTrace();
		if (tx != null && tx.isActive())
			tx.rollback();
	}
	return highlight;
}


public static HashMap<Integer,Integer> getUnseenTopics(User user) {

	HashMap<Integer,Integer> unseenHash = new HashMap<Integer,Integer>();
	Transaction tx = null;
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	try {
		tx = session.getTransaction();
		Query q = session.createQuery(
				"select location, count(*) from Topic as t" +
				" where t.topicID not in (" +
				"		select topicID from LastseenTopic as l where l.userID = :userid and l.lasttime >= t.lastedit" +
				"       )" +
				" group by location"
				);
		q.setInteger("userid", user.getId());
		
		List<Object[]> lsts = q.list();
		for (Object[] o : lsts) {
			unseenHash.put(new Integer(""+o[0]), new Integer(""+o[1]));
		}

	} catch (HibernateException e) {
		e.printStackTrace();
		if (tx != null && tx.isActive())
			tx.rollback();
	}
	return unseenHash;
}


public void setTopicID(int topicID) {
 this.topicID = topicID;
}

public int getTopicID() {
 return topicID;
}

public void setName(String name) {
 this.name = name;
}

public String getName() {
 return name;
}

public void setStarter(int starter) {
 this.starter = starter;
}

public int getStarter() {
 return starter;
}

public void setSticky(boolean sticky) {
 this.sticky = sticky;
}

public void setClosed(boolean closed) {
 this.closed = closed;
}

public void setClearance(int clearance) {
 this.clearance = clearance;
}

public int getClearance() {
 return clearance;
}

public void setStarted(Date started) {
 this.started = started;
}

public Date getStarted() {
 return started;
}

public void setLastedit(Date lastedit) {
 this.lastedit = lastedit;
}

public Date getLastedit() {
 return lastedit;
}

public void setLocation(int location) {
 this.location = location;
}

public int getLocation() {
 return location;
}

public void setUser(User user) {
	this.user = user;
}

public User getUser() {
	return user;
}

public int getReplies() {
	return replies;
}

public void setReplies(int replies) {
	this.replies = replies;
}

public boolean isSticky() {
	return sticky;
}

public boolean isClosed() {
	return closed;
}

public boolean equals(Object other) {
	if ((this == other))
		return true;
	if ((other == null))
		return false;
	if (!(other instanceof Topic))
		return false;
	Topic t = (Topic) other;
	return (t.getTopicID() == this.getTopicID());
}

public int hashCode() {
      return this.getTopicID();
}

public User getLastPoster() {
	List<Post> posts = Post.getPosts(this.getTopicID(), -1);
	return posts.get(posts.size()-1).getUser();
}


}