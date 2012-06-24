package be.lacerta.cq2.sim.hbn;

// Generated 11-jun-2009 13:48:02 by Hibernate Tools 3.2.4.GA
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * RaidResult generated by hbm2java
 */
public class RaidResult extends HbnObject implements java.io.Serializable {
	
	public static int TYPE_ALL = 0;
	public static int TYPE_INCOMING = 1;
	public static int TYPE_OUTGOING = 2;

	private Integer id;
	private User user;
	private Mage mage;
	private String text;
	private Integer totalres;
	private Integer totalpwr;
	private Integer totalpb;
	private Integer totalworkers;
	private String totalgem;
	private Date date;
	private boolean incoming;
	private Set<RaidResultNote> notes = new HashSet<RaidResultNote>(0);

	public RaidResult() {
	}

	public RaidResult(User user, Mage mage, String text, Integer totalres,
			Integer totalpwr, Date date, boolean incoming,
			Set<RaidResultNote> notes) {
		this.user = user;
		this.mage = mage;
		this.text = text;
		this.totalres = totalres;
		this.totalpwr = totalpwr;
		this.date = date;
		this.incoming = incoming;
		this.notes = notes;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Mage getMage() {
		return this.mage;
	}

	public void setMage(Mage mage) {
		this.mage = mage;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getTotalres() {
		return this.totalres;
	}

	public void setTotalres(Integer totalres) {
		this.totalres = totalres;
	}

	public Integer getTotalpwr() {
		return this.totalpwr;
	}

	public void setTotalpwr(Integer totalpwr) {
		this.totalpwr = totalpwr;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isIncoming() {
		return this.incoming;
	}

	public void setIncoming(boolean incoming) {
		this.incoming = incoming;
	}

	public Set<RaidResultNote> getNotes() {
		return this.notes;
	}

	public void setNotes(Set<RaidResultNote> notes) {
		this.notes = notes;
	}
	
	public Integer getTotalpb() {
		return totalpb;
	}

	public void setTotalpb(Integer totalpb) {
		this.totalpb = totalpb;
	}

	public Integer getTotalworkers() {
		return totalworkers;
	}

	public void setTotalworkers(Integer totalworkers) {
		this.totalworkers = totalworkers;
	}

	public String getTotalgem() {
		return totalgem;
	}

	public void setTotalgem(String totalgem) {
		this.totalgem = totalgem;
	}

	
	/** custom functions **/
	
	public void addNote(RaidResultNote note) {
		note.setRaidresult(this);
		getNotes().add(note);
		note.saveOrUpdate();
	}
	
	public static List<RaidResult> getRaidResults(int days, int type) {

		List<RaidResult> raidresults = new ArrayList<RaidResult>();
		
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(RaidResult.class);
			if (type == TYPE_INCOMING) {
				c.add(Restrictions.eq("incoming", true));
			} else if (type == TYPE_OUTGOING) {
				c.add(Restrictions.eq("incoming", false));
			}
			
			c.add(Restrictions.ge("date", new Date((new Date()).getTime()-(1000*60*60*24*days))));
			
			c.addOrder(Order.desc("date"));
			
			//c.setMaxResults(Topic.POSTSPERPAGE);
			raidresults = c.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return raidresults;
	}

	public static Integer getTotalResults(int type) {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			String query = "select count(*) from RaidResult";
			if (type == TYPE_INCOMING) {
				query+=" where incoming = 1";
			} else if (type == TYPE_OUTGOING) {
				query+=" where incoming = 0";
			}
			return ((Long) session.createQuery(query).uniqueResult()).intValue();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return 0;
	}
	
	public static List<RaidResult> loadByMageAndType(String mage, int type) {
		List<RaidResult> raidresults = new ArrayList<RaidResult>();
		
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
			Criteria c = session.createCriteria(RaidResult.class);

			Criterion byMage;
			Criterion byUser;
			
			if (type == TYPE_INCOMING) {
				byMage = Restrictions.and(Restrictions.eq("mage", Mage.getOrCreateMage(mage)), Restrictions.eq("incoming", true));
			} else if (type == TYPE_OUTGOING) {
				byMage = Restrictions.and(Restrictions.eq("mage", Mage.getOrCreateMage(mage)), Restrictions.eq("incoming", false));
			} else {
				byMage = Restrictions.eq("mage", Mage.getOrCreateMage(mage));
			}

			List<User> users = User.getUsersByMageName(mage);
			if (users != null && users.size()>0) {
				if (type == TYPE_INCOMING) {
					byUser = Restrictions.and(Restrictions.in("user", users), Restrictions.eq("incoming", false));
				} else if (type == TYPE_OUTGOING) {
					byUser = Restrictions.and(Restrictions.in("user", users), Restrictions.eq("incoming", true));
				} else {
					byUser = Restrictions.in("user", users);
				}
				c.add(Restrictions.or(byMage, byUser));
			} else {
				c.add(byMage);
			}
			
			
			
			c.addOrder(Order.desc("date"));
			raidresults = c.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
		return raidresults;
	}
}
