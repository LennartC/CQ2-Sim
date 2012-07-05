package be.lacerta.cq2.sim.hbn;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import be.lacerta.cq2.utils.StringUtils;

public class User extends HbnObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2504230279977343746L;
	private int id;

	private String username;
	private String password;
	private long ulvl;
	private boolean disabled;
	private boolean passExpired;
	private boolean hideOnline;
	private boolean systemUser;

	private Mage mage;
	private int forestSkill;
	private int deathSkill;
	private int airSkill;
	private int earthSkill;
	
	private Date lastseen;
	private String imagepac;
	private String email;
	private String phone;
	private Date birthday;
	private String location;
	
	private UserOrbs userOrbs;
	private UserImage userImage;
	
	private String qauth;
	private int travelgain;
	private int raidgain;
	private int raidloss;
	
	private String ipAddress;
	
	public User() {
		setId(-1);
	}
	
	public User(int id, String username) {
		setId(id);
		setUsername(username);
	}
	
	
	public static User createFromDatabase(String username, String password) {
		User u = null;
		
		if (username == null || password == null) {
			return u;
		}
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			Criteria c = session.createCriteria(User.class);
			c.add(Restrictions.eq("username", username));
			c.add(Restrictions.eq("password", StringUtils.encrypt(password)));
			c.add(Restrictions.ne("disabled", true));
			@SuppressWarnings("unchecked")
			List<User> result = c.list();
			if (result.size() > 0) { 
				u = result.get(0);
			}
			
			//tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return u;
	}
	
	public static User createFromDatabase(String username) {
		User user = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			Criteria c = session.createCriteria(User.class);
			c.add(Restrictions.eq("username", username));
			@SuppressWarnings("unchecked")
			List<User> result = c.list();
			if (result.size() > 0) { 
				user = result.get(0);
			}
			

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public static List<User> getUsersByQauth(String qauth) {
		List<User> users = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			Criteria c = session.createCriteria(User.class);
			c.add(Restrictions.eq("qauth", qauth));
			users = c.list();
	
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public static List<User> getUsersByMageName(String mage) {
		return getUsersByMage(Mage.getOrCreateMage(mage));
	}
	
	@SuppressWarnings("unchecked")
	public static List<User> getUsersByMage(Mage mage) {
		List<User> users = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			Criteria c = session.createCriteria(User.class);
			c.add(Restrictions.eq("mage", mage));
			users = c.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	@SuppressWarnings("unchecked")
	public static List<User> getUserList() {
		List<User> users = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			Criteria c = session.createCriteria(User.class);
			c.addOrder(Order.asc("username"));
			users = (List<User>)c.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return users==null?new ArrayList<User>():users;
	}
	
	public long getUlvl() {
		return ulvl;
	}

	public void setUlvl(long ulvl) {
		this.ulvl = ulvl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Deprecated
	public String getCq2class() {
		if (mage!=null) return mage.getCq2class();
		else return null;
	}
	@Deprecated
	public void setCq2class(String cq2class) {
		if (mage!=null) mage.setCq2class(cq2class);
	}
	@Deprecated
	public Integer getLevel() {
		if (mage!=null) return mage.getLevel();
		else return null;
	}
	@Deprecated
	public void setLevel(Integer level) {
		if (mage!=null) mage.setLevel(level);
	}
	
	public Date getLastseen() {
		return lastseen;
	}

	public void setLastseen(Date lastseen) {
		this.lastseen = lastseen;
	}

	public String getImagepac() {
		return imagepac;
	}

	public void setImagepac(String imagepac) {
		this.imagepac = imagepac;
	}

	@Deprecated
	public String getKingdom() {
		if (mage!=null) 
			if (mage.getKingdom()!=null) return mage.getKingdom().getName();
			else return null;
		else return null;
	}

	@Deprecated
	public void setKingdom(String kdName) {
		if (mage!=null) {
			Kingdom kingdom = Kingdom.loadByName(kdName);
			if (kingdom == null) {
				kingdom = new Kingdom();
				kingdom.setName(kdName);
				kingdom.setLevel(1);
				kingdom.saveOrUpdate();
			}
			
			mage.setKingdom(kingdom);
			kingdom.addInhabitant(mage);
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setPlainPassword(String password) {
		setPassword(StringUtils.encrypt(password));
	}
	
	public static boolean hasAccess(User u, long mode) {
		return u.getId() == 1 || (u.getUlvl()>=mode && u.getUlvl()%mode == 0 && !u.isDisabled());
	}
	
	public boolean hasAccess(long mode) {
		return hasAccess(this,mode);
	}
	
	public long getIdleSeconds() {
		return ((System.currentTimeMillis() - lastseen.getTime()) / 1000);
	}
	
	public boolean isOnline() {
		if (isHideOnline() && !isDisabled()) return false; 
		if (lastseen == null) return false;
		return ((System.currentTimeMillis() - lastseen.getTime()) / 1000)<300;
	}

	@Deprecated
	public String getCq2name() {
		return mage==null?"":mage.getName();
	}

	@Deprecated
	public void setCq2name(String mage) {
		if (mage != null)
			this.mage = Mage.getOrCreateMage(mage);
		else
			this.mage = null;
	}
	
	public Mage getMage() {
		return mage;
	}

	public void setMage(Mage mage) {
		this.mage = mage;
	}

	public int getForestSkill() {
		return forestSkill;
	}

	public void setForestSkill(int forestSkill) {
		this.forestSkill = forestSkill;
	}

	public int getDeathSkill() {
		return deathSkill;
	}

	public void setDeathSkill(int deathSkill) {
		this.deathSkill = deathSkill;
	}

	public int getAirSkill() {
		return airSkill;
	}

	public void setAirSkill(int airSkill) {
		this.airSkill = airSkill;
	}

	public int getEarthSkill() {
		return earthSkill;
	}

	public void setEarthSkill(int earthSkill) {
		this.earthSkill = earthSkill;
	}

	public String getEmail() {
		return email==null?"":email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone==null?"":phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof User))
			return false;
		User u = (User) other;
		return (u.getId() == this.getId());
	}
	
	public int hashCode() {
	      return id;
	}

	@Deprecated
	public String getGems() {
		if (userOrbs!=null) return userOrbs.getGems();
		else return "";
	}
	
	@Deprecated
	public void setGems(String gems) {
		if (userOrbs==null) userOrbs = new UserOrbs(this);
		userOrbs.setGems(gems);
	}

	@Deprecated
	public String getOrbs() {
		if (userOrbs!=null) return userOrbs.getOrbs();
		else return "";
	}

	@Deprecated
	public void setOrbs(String orbs) {
		if (userOrbs==null) userOrbs = new UserOrbs(this);
		userOrbs.setOrbs(orbs);
	}
	
	public String getQauth() {
		return qauth==null?"":qauth;
	}

	public void setQauth(String qauth) {
		this.qauth = qauth;
	}
	
	public int getTravelgain() {
		return travelgain;
	}

	public void setTravelgain(int travelgain) {
		this.travelgain = travelgain;
	}
	
	public int getRaidgain() {
		return raidgain;
	}

	public void setRaidgain(int raidgain) {
		this.raidgain = raidgain;
	}
	
	public int getRaidloss() {
		return raidloss;
	}

	public void setRaidloss(int raidloss) {
		this.raidloss = raidloss;
	}

	public Date getBirthday() {
		return birthday;
	}
	
	public String getBirthdayString() {
		if (birthday==null) return "";
		else return new SimpleDateFormat("dd MMM yyyy").format(birthday);
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public boolean hasBirthdayToday() {
		if(birthday!=null) {
			String today = new SimpleDateFormat("dd MM").format(new Date());
			String birthday = new SimpleDateFormat("dd MM").format(this.birthday);
			return today.equals(birthday);
		} else return false;
	}
	
	public int getAge() {
		return new Date().getYear()-birthday.getYear();
	}

	public String getLocation() {
		return location==null?"":location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isPassExpired() {
		return passExpired;
	}

	public void setPassExpired(boolean passExpired) {
		this.passExpired = passExpired;
	}

	public UserOrbs getUserOrbs() {
		return userOrbs;
	}

	public void setUserOrbs(UserOrbs userOrbs) {
		this.userOrbs = userOrbs;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public UserImage getUserImage() {
		return userImage;
	}

	public void setUserImage(UserImage userImage) {
		this.userImage = userImage;
	}


	public boolean isHideOnline() {
		return hideOnline;
	}

	public void setHideOnline(boolean hideOnline) {
		this.hideOnline = hideOnline;
	}

	public boolean isSystemUser() {
		return systemUser;
	}

	public void setSystemUser(boolean systemUser) {
		this.systemUser = systemUser;
	}
}
