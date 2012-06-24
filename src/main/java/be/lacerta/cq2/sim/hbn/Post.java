package be.lacerta.cq2.sim.hbn; 

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import be.lacerta.cq2.utils.StringUtils;

public class Post extends HbnObject implements java.io.Serializable  {
	 
int postID;
String post;
int topicID;
int editflag;
Date edit;
Date date;

User user;


public Post(){
	setDate(new Date());
	setEdit(new Date());
}

@SuppressWarnings("unchecked")
public static List<Post> getPosts(int topicid, int offset) {

	List<Post> posts = new Vector<Post>();
	
	Transaction tx = null;
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	try {
		tx = session.getTransaction();
		
		Criteria c = session.createCriteria(Post.class);
		c.add(Restrictions.eq("topicID", topicid));
		
		if (offset>=0) {
			c.addOrder(Order.asc("postID"));
			c.setFirstResult(offset);
			c.setMaxResults(Topic.POSTSPERPAGE);
		} else {
			c.addOrder(Order.desc("postID"));
			c.setMaxResults(1);
		}

		posts = c.list();
		

//		for (Iterator<Object[]> it = q.iterate(); it.hasNext(); ) {
//			Object[] el = it.next();
//			Post post = (Post) el[0];
//			User user = (User)el[1];
//			post.setUser(user);
//			posts.add(post);
//		}

		
	} catch (HibernateException e) {
		e.printStackTrace();
		if (tx != null && tx.isActive())
			tx.rollback();
	}
	return posts;
}

public void setPostID(int postID) {
 this.postID = postID;
}

public int getPostID() {
 return postID;
}

public void setPost(String post) {
 this.post = StringUtils.stripHTML(post);
}

public String getPost() {
 return post;
}

public String getConvertedPost() {
	return StringUtils.convertBBCode(post);
}

public void setTopicID(int topicID) {
 this.topicID = topicID;
}

public int getTopicID() {
 return topicID;
}

public void setEditflag(int editflag) {
 this.editflag = editflag;
}

public int getEditflag() {
 return editflag;
}

public void setEdit(Date edit) {
 this.edit = edit;
}

public Date getEdit() {
 return edit;
}

public void setDate(Date date) {
 this.date = date;
}

public Date getDate() {
 return date;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}


public boolean equals(Object other) {
	if ((this == other))
		return true;
	if ((other == null))
		return false;
	if (!(other instanceof Post))
		return false;
	Post p = (Post) other;
	return (p.getPostID() == this.getPostID());
}

public int hashCode() {
      return this.getPostID();
}

}