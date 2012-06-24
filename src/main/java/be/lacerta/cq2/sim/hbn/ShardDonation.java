package be.lacerta.cq2.sim.hbn;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ShardDonation extends HbnObject implements java.io.Serializable {
	int id;
	Mage mage;
	String shard;
	Date time;
	int toUserId;

	public ShardDonation() {
		setTime(new Date());
	}
	
	@SuppressWarnings("unchecked")
	public static List<ShardDonation> getShardsForMage(String mage) {
		List<ShardDonation> shards = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(ShardDonation.class);
			c.add(Restrictions.eq("mage", Mage.getOrCreateMage(mage)));
			shards = c.list();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return shards;
	}
	
	@SuppressWarnings("unchecked")
	public static List<ShardDonation> getShardDonations(int max) {
		List<ShardDonation> shards = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(ShardDonation.class);
			c.addOrder(Order.desc("time"));
			c.setMaxResults(max);
			shards = c.list();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return shards;
	}
	
	public static TreeMap<Mage,TreeMap<String,Integer>> getShardsPerMage() {
		TreeMap<Mage,TreeMap<String,Integer>> pw = new TreeMap<Mage,TreeMap<String,Integer>>();
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			Criteria c = session.createCriteria(ShardDonation.class);
			c.addOrder(Order.desc("time"));
			List<ShardDonation> shards = c.list();
			
			for (ShardDonation sd : shards) {
				TreeMap<String,Integer> hm = pw.containsKey(sd.getMage())?pw.get(sd.getMage()):new TreeMap<String,Integer>();
				int amount = hm.containsKey(sd.getShard())?hm.get(sd.getShard())+1:1;
				hm.put(sd.getShard(),amount);
				pw.put(sd.getMage(), hm);
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return pw;
	}
	
	public static TreeMap<Integer,TreeMap<String,Integer>> getStock() {
		TreeMap<Integer,TreeMap<String,Integer>> pw = new TreeMap<Integer,TreeMap<String,Integer>>();
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			Criteria c = session.createCriteria(ShardDonation.class);
			c.addOrder(Order.desc("time"));
			List<ShardDonation> shards = c.list();
			
			for (ShardDonation sd : shards) {
				TreeMap<String,Integer> hm = pw.containsKey(sd.getToUserId())?pw.get(sd.getToUserId()):new TreeMap<String,Integer>();
				int amount = hm.containsKey(sd.getShard())?hm.get(sd.getShard())+1:1;
				hm.put(sd.getShard(),amount);
				pw.put(sd.getToUserId(), hm);
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return pw;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Reveal))
			return false;
		ShardDonation r = (ShardDonation) other;
		return (r.getId() == this.getId());
	}
	
	public int hashCode() {
	      return id;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Mage getMage() {
		return mage;
	}


	public void setMage(Mage mage) {
		this.mage = mage;
	}


	public String getShard() {
		return shard;
	}


	public void setShard(String shard) {
		this.shard = shard;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}

	public void setTime(String time) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd @ HH:mm");
		this.time = df.parse(time);
	}

	public int getToUserId() {
		return toUserId;
	}


	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}
}
