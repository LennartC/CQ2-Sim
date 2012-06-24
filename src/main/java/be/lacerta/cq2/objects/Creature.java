package be.lacerta.cq2.objects;


public class Creature {
	final public static String SUFFOCATION = "Suffocation";
	final public static String METAMORPHOSIS = "Metamorphosis";
	final public static String DOPPELGANGER = "Doppelganger";
	final public static String JINX = "Jinx";
	final public static String BLESSING = "Surathli's Blessing";

	String name;
	String creatureClass;
	String race;
	int level;
	int damage = 0;
	int forestDef = 0;
	int deathDef = 0;
	int airDef = 0;
	int earthDef = 0;
	int health = 0;
	int skill = 0;
	Item item;
	boolean metad;
	boolean suffocated;
	boolean doppeled;
	boolean jinxed;
	boolean blessed;
	
	public boolean isBlessed() {
		return blessed;
	}

	public void setBlessed(boolean blessed) {
		this.blessed = blessed;
	}

	boolean nether;
	
	  public boolean isNether() {
		return nether;
	}

	public void setNether(boolean nether) {
		this.nether = nether;
	}

	public Creature() {

	  }
	
	public Creature(String name, String creatureClass, String race,
			int damage, int health, int skill, int fd, int dd, int ad,
			int ed, Item item) {
		this.name = name;
		this.creatureClass = creatureClass;
		this.race = race;
		this.damage = damage;
		this.health = health;
		this.skill = skill;
		this.forestDef = fd;
		this.deathDef = dd;
		this.airDef = ad;
		this.earthDef = ed;
		this.item = item;
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
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
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getSkill() {
		return skill;
	}
	public void setSkill(int skill) {
		this.skill = skill;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public boolean isMetad() {
		return metad;
	}
	public void setMetad(boolean metad) {
		this.metad = metad;
	}
	public boolean isSuffocated() {
		return suffocated;
	}
	public void setSuffocated(boolean suffocated) {
		this.suffocated = suffocated;
	}
	public boolean isDoppeled() {
		return doppeled;
	}
	public void setDoppeled(boolean doppeled) {
		this.doppeled = doppeled;
	}
	public boolean isJinxed() {
		return jinxed;
	}
	public void setJinxed(boolean jinxed) {
		this.jinxed = jinxed;
	}
	
	/* EXTENDED FUNCTIONS **/
	
	  public int getDef(String crtClass) {
		  //System.out.println("finding def for "+crtClass+" and def is : F"+getForestDef()+"/D"+getDeathDef()+"/A"+getAirDef()+"/E"+getEarthDef());
	      if (crtClass.toLowerCase().equalsIgnoreCase("forest")) {
	          return getForestDef();
	      } else if (crtClass.toLowerCase().equalsIgnoreCase("death")) {
	          return getDeathDef();
	      } else if (crtClass.toLowerCase().equalsIgnoreCase("air")) {
	          return getAirDef();
	      } else if (crtClass.toLowerCase().equalsIgnoreCase("earth")) {
	          return getEarthDef();
	      }
	      return getForestDef();
	  }
	    
		public static int convertStrength(int strength, int level, int desiredLevel) {
			return Integer.parseInt(""+Math.round((strength*(0.8 + 0.2*desiredLevel))/(0.8+0.2*level)));
		}
		
		
	public void setCurse(String curse) {
		if (curse==null) {
		} else if (curse.equals(Creature.DOPPELGANGER)) {
			setDoppeled(true);
		} else if (curse.equals(Creature.METAMORPHOSIS)) {
			setMetad(true);
		}  else if (curse.equals(Creature.JINX)) {
			setJinxed(true);
		}  else if (curse.equals(Creature.SUFFOCATION)) {
			setSuffocated(true);
		}  else if (curse.equals(Creature.BLESSING)) {
			setBlessed(true);
		} 
	}
}
