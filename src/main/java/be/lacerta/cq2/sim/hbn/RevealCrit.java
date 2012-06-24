package be.lacerta.cq2.sim.hbn;

import be.lacerta.cq2.utils.CreatureFinder;

public class RevealCrit extends HbnObject implements java.io.Serializable, Comparable<RevealCrit> {

	public static String TYPE_NETHER = "N";
	public static String TYPE_ITHERIAN = "I";    
	
	int id;
	Reveal reveal;
	int sortid;
	String name;
	String creatureClass;
	String race;
	int level;
	int damage;
	int health;
	int forestDef;
	int deathDef;
	int airDef;
	int earthDef;
	String item;
	String enchant;
	String curse;
	String type;
	int ith;
	String unparsed;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Reveal getReveal() {
		return reveal;
	}
	public void setReveal(Reveal reveal) {
		this.reveal = reveal;
	}
	public int getSortid() {
		return sortid;
	}
	public void setSortid(int sortid) {
		this.sortid = sortid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatureClass() {
		return creatureClass;
	}
	public void setCreatureClass(String creatureClass) {
		this.creatureClass = creatureClass;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
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
	public int getForestDef() {
		return forestDef;
	}
	public void setForestDef(int forestDef) {
		this.forestDef = forestDef;
	}
	public int getDeathDef() {
		return deathDef;
	}
	public void setDeathDef(int deathDef) {
		this.deathDef = deathDef;
	}
	public int getAirDef() {
		return airDef;
	}
	public void setAirDef(int airDef) {
		this.airDef = airDef;
	}
	public int getEarthDef() {
		return earthDef;
	}
	public void setEarthDef(int earthDef) {
		this.earthDef = earthDef;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		if (item!=null) item = item.trim();
		if (("").equals(item)) item = null;
		this.item = item;
	}
	public String getCurse() {
		return curse;
	}
	public void setCurse(String curse) {
		this.curse = curse;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getIth() {
		return ith;
	}
	public void setIth(int ith) {
		this.ith = ith;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RevealCrit))
			return false;
		RevealCrit r = (RevealCrit) other;
		return (r.getId() == this.getId());
	}
	
	public int hashCode() {
	      return id;
	}

	public String getEnchant() {
		return enchant;
	}

	public void setEnchant(String enchant) {
		this.enchant = enchant;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getUnparsed() {
		return unparsed;
	}

	public void setUnparsed(String unparsed) {
		this.unparsed = unparsed;
	}

	public be.lacerta.cq2.objects.Creature getStandardCreature() {
		try {
			Creature sc = Creature.getCreature(this.getName());
			return CreatureFinder.convertDatabaseCreature(sc, this.getLevel());
		} catch (IndexOutOfBoundsException iobe) {
		} catch (NullPointerException npe) {
		}
		return null;
	}

	public int compareTo(RevealCrit r) {
		if (this.getSortid() == 0 || r.getSortid() == 0)
			return this.getId()-r.getId();
		return this.getSortid()-r.getSortid();
	}
	

}
