package be.lacerta.cq2.sim.hbn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import be.lacerta.cq2.utils.StringUtils;


public class News extends HbnObject implements java.io.Serializable {
	 
int newsID;
String title;
User user;
Date time;
String directlink;
String newsfor;

public News(){
	 setTime(new Date());
}

public void setNewsID(int newsID) {
 this.newsID = newsID;
}

public int getNewsID() {
 return newsID;
}

public void setTitle(String title) {
 this.title = StringUtils.stripHTML(title);
}

public String getTitle() {
 return title;
}

public void setUser(User user) {
 this.user = user;
}

public User getUser() {
 return user;
}

public void setTime(Date time) {
 this.time = time;
}

public Date getTime() {
 return time;
}

public void setDirectlink(String directlink) {
 this.directlink = directlink;
}

public String getDirectlink() {
 return directlink;
}

public void setNewsfor(String newsfor) {
 this.newsfor = newsfor;
}

public String getNewsfor() {
 return newsfor;
}

@SuppressWarnings("unchecked")
public static List<News> getNews(int limit, int offset) {
	List<News> result = new ArrayList<News>();
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	Criteria c = session.createCriteria(News.class);
	c.addOrder(Order.desc("time"));
	c.setMaxResults(limit);
	c.setFirstResult(offset);
	result = c.list();

	return result;
}

}