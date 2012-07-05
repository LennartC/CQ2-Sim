package be.lacerta.cq2.sim.messaging;

import be.lacerta.cq2.sim.hbn.Mage;


public class MageMessage implements Message {
	private static final long serialVersionUID = -8711649101963107856L;
	
	private String name;
	private String cq2class;
	private Integer level;
	private String kingdom;
	private Integer forestSkill;
	private Integer deathSkill;
	private Integer airSkill;
	private Integer earthSkill;
	
	public Integer getForestSkill() {
		return forestSkill;
	}
	public void setForestSkill(Integer forestSkill) {
		this.forestSkill = forestSkill;
	}
	public Integer getDeathSkill() {
		return deathSkill;
	}
	public void setDeathSkill(Integer deathSkill) {
		this.deathSkill = deathSkill;
	}
	public Integer getAirSkill() {
		return airSkill;
	}
	public void setAirSkill(Integer airSkill) {
		this.airSkill = airSkill;
	}
	public Integer getEarthSkill() {
		return earthSkill;
	}
	public void setEarthSkill(Integer earthSkill) {
		this.earthSkill = earthSkill;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCq2class() {
		return cq2class;
	}
	public void setCq2class(String cq2class) {
		this.cq2class = cq2class;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getKingdom() {
		return kingdom;
	}
	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
	}
	
	public static MageMessage fromEntity(Mage entity) {
        MageMessage mage = new MageMessage();
        mage.setName(entity.getName());
        mage.setCq2class(entity.getCq2class());
        mage.setLevel(entity.getLevel());
        if (entity.getKingdom()!=null)
        	mage.setKingdom(entity.getKingdom().getName());
        return mage;
	}
}
