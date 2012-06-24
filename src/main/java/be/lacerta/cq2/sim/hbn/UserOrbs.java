package be.lacerta.cq2.sim.hbn;

import java.io.Serializable;

public class UserOrbs extends HbnObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6232989504914117773L;
	int userid;
	User user;
	String gems;
	String orbs;
	
	public UserOrbs() {
		
	}
	
	public UserOrbs(User user) {
		this.user = user;
	}
	
	public String getGems() {
		return gems;
	}
	public void setGems(String gems) {
		this.gems = gems;
	}
	public String getOrbs() {
		return orbs;
	}
	public void setOrbs(String orbs) {
		this.orbs = orbs;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
