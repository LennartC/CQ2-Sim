package be.lacerta.cq2.sim.hbn;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


public class XPLog extends HbnObject implements java.io.Serializable {
	int id;
	int userid;
	int xp;
	Date time;
	
	public XPLog() {
		setTime(new Date());
	}
	
	public XPLog (int userid, int xp) {
		setUserid(userid);
		setXp(xp);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getXp() {
		return xp;
	}
	public void setXp(int xp) {
		this.xp = xp;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		final XPLog other = (XPLog) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	public static int getInterpolatedXp(XPLog log1, XPLog log2,Calendar wanted) {
		Calendar date1 = Calendar.getInstance();
		date1.setTime(log1.getTime());
		Calendar date2 = Calendar.getInstance();
		date2.setTime(log2.getTime());
		int xp1 = log1.getXp();
		int xp2 = log2.getXp();
		float slope = (xp2-xp1)/((float)(date2.getTimeInMillis()-date1.getTimeInMillis()));
		float interpolatedXp=xp1+slope*(Math.abs(wanted.getTimeInMillis()-date1.getTimeInMillis()));
		//System.out.println("Interpolated xp: (" + date1.getTime() + "," +xp1 + "), (" + date2.getTime() + "," +xp2+"), (" + wanted.getTime() + "," +interpolatedXp+")");
		return Math.round(interpolatedXp);
	}
	
	public static int getLastXp(int userId) {
		List<XPLog> list = getXPLogs(userId,1);
		if(list.size()>0) return list.get(0).getXp();
		else return 0;
	}
	
	public static List<XPLog> getXPLogs(int userId) {
		return getXPLogs(userId,-1);
	}
	
	public static List<XPLog> getXPLogs(int userId, int limit) {
		List<XPLog> xpLogs = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(XPLog.class);
			c.add(Restrictions.eq("userid", userId));
			c.addOrder(Order.desc("time"));
			if (limit > 0) c.setMaxResults(limit);
			xpLogs = c.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return xpLogs;
	}

	public static int getAmountOfRecentXP(int userId) {
		int lastXp = getLastXp(userId);
		Calendar threeDaysBefore = Calendar.getInstance();
		threeDaysBefore.add(Calendar.HOUR, -72);
		List<XPLog> logs = getXPLogs(userId);
		int interpolatedXp=lastXp;
		XPLog prevLog =null;
		if(logs.size()>1) {
			for(XPLog log : logs) {
				Calendar logCal = Calendar.getInstance();
				logCal.setTime(log.getTime());
				if(((threeDaysBefore.getTime().compareTo(log.getTime()))>=0) && prevLog!=null && (threeDaysBefore.getTime().compareTo(prevLog.getTime()))<=0) {
					interpolatedXp=getInterpolatedXp(log, prevLog, threeDaysBefore);
					break;
				}
				prevLog=log;
			}
		}
		
		return Math.abs(lastXp-interpolatedXp);
	}
	
	public static void main(String args[]) {
		System.out.println(getAmountOfRecentXP(16));
	}
	
}
