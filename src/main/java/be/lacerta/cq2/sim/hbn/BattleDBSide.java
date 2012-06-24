package be.lacerta.cq2.sim.hbn;

public class BattleDBSide extends HbnObject implements java.io.Serializable {
	int id;
	String name;
	String critClass;
	int damage;
	int health;
	int def;
	String curse;
	int ithdamage;
	int ithhealth;
	int ithdef;
	String item;
	String itemenchant;
	int same;
	int diff;
	int waveenchanttype;
	int waveenchantlevel;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCritClass() {
		return critClass;
	}
	public void setCritClass(String critClass) {
		this.critClass = critClass;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public String getCurse() {
		return curse;
	}
	public void setCurse(String curse) {
		this.curse = curse;
	}
	public int getIthdamage() {
		return ithdamage;
	}
	public void setIthdamage(int ithdamage) {
		this.ithdamage = ithdamage;
	}
	public int getIthhealth() {
		return ithhealth;
	}
	public void setIthhealth(int ithhealth) {
		this.ithhealth = ithhealth;
	}
	public int getIthdef() {
		return ithdef;
	}
	public void setIthdef(int ithdef) {
		this.ithdef = ithdef;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getItemenchant() {
		return itemenchant;
	}
	public void setItemenchant(String itemenchant) {
		this.itemenchant = itemenchant;
	}
	public int getSame() {
		return same;
	}
	public void setSame(int same) {
		this.same = same;
	}
	public int getDiff() {
		return diff;
	}
	public void setDiff(int diff) {
		this.diff = diff;
	}
	public int getWaveenchanttype() {
		return waveenchanttype;
	}
	public void setWaveenchanttype(int waveenchanttype) {
		this.waveenchanttype = waveenchanttype;
	}
	public int getWaveenchantlevel() {
		return waveenchantlevel;
	}
	public void setWaveenchantlevel(int waveenchantlevel) {
		this.waveenchantlevel = waveenchantlevel;
	}
	

}
