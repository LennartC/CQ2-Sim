package be.lacerta.cq2.sim.hbn;

import java.io.Serializable;
import java.util.Date;

public class LastseenTopic extends HbnObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7554053087048150612L;
	int userID;
	int topicID;
	Date lasttime;
	
	public LastseenTopic() {
		setLasttime(new Date());
	}
	
	public LastseenTopic(User u, Topic t) {
		setTopicID(t.getTopicID());
		setUserID(u.getId());
		setLasttime(new Date());
	}
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getTopicID() {
		return topicID;
	}
	public void setTopicID(int topicID) {
		this.topicID = topicID;
	}
	public Date getLasttime() {
		return lasttime;
	}
	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LastseenTopic))
			return false;
		LastseenTopic lst = (LastseenTopic) other;
		return (lst.getTopicID() == this.getTopicID() 
				&& lst.getUserID() == this.getUserID());
	}
	
	public int hashCode() {
	      int result = 17; 
	      result += 41 * this.getTopicID();
	      result += 73 * this.getUserID();
	      return result;
	}
	
}
