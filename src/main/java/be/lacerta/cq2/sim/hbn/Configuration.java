package be.lacerta.cq2.sim.hbn;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


public class Configuration extends HbnObject implements java.io.Serializable{

	private static final long serialVersionUID = -9150107579827108995L;
	
	public static final String MASTER = "MASTER"; 
	public static final String QUEUE_USER = "QUEUE_USER"; 
	public static final String PROPAGETE = "PROPAGETE"; 
	
	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Configuration))
			return false;
		Configuration c = (Configuration) other;
		return (c.getKey() == this.getKey());
	}
	
	public int hashCode() {
	    return key.hashCode();
	}
	
	public static String getValue(String key) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			Criteria c = session.createCriteria(Configuration.class);
			c.add(Restrictions.ilike("key", key));
			List<Configuration> l = c.list();
			if (l != null && l.size()>0)
				return ((Configuration)c.list().get(0)).getValue();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException iobe) { iobe.printStackTrace();
		} catch (NullPointerException npe) { npe.printStackTrace(); }
		return null;
	}

}