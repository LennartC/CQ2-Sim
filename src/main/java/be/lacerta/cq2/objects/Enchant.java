package be.lacerta.cq2.objects;

public class Enchant {
	public final static int TOPAZ = 0;
	public final static int QUARTZ = 1;
	public final static int OPAL = 2;
	public final static int CARNELIAN = 3;
	public final static int DIAMOND = 4;
	public final static int SAPPHIRE = 5;
	public final static int EMERALD = 6;
	public final static int RUBY = 7;
	public final static double[] MULTIPLIER = {	0.6, // topaz 
												0.6, // quartz
												0.7, // opal
												0.8, // carnelian
												1,   // diamond
												1,   // sapphire
												1.2, // emerald
												1.2  // ruby
											   };

	public final static double[] NETHER_BONUS = {	600, // topaz 
													15, // quartz
													110, // opal
													130, // carnelian
													15,   // diamond
													110,   // sapphire
													600, // emerald
													130  // ruby
											   };
	int type;
	int level;
	String creature;

	public Enchant(int type, int level) {
		this(type,level,"");
	}
	public Enchant(int type, int level, String creature) {
		this.type = type;
		this.level = level;
		this.creature = creature;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public double getDamage(double damage) {
		if (type == RUBY && level > 0) {
			// 0.0416 * (lvl+4) *basedamage
			double d = Math.floor( (0.0416*(level+4)*damage) + 0.5);
			damage += d;
		} else if  (type == CARNELIAN && level > 0) {
			// 0.0293 * (lvl+4) *basedamage
			double d = Math.floor( (0.0293*(level+4)*damage) + 0.5);
			damage += d;
		} 
		return damage;
	}
	
	public double getDefense(double defense) {
		if (type == SAPPHIRE && level > 0) {
			double d = Math.floor((1.646*(level+4))+0.5);
			defense += d;
		} else if (type == OPAL && level > 0) {
			double d = Math.floor((1.25*(level+4))+0.5);
			defense += d;
		}
		return defense;
	}

	public String getCreature() {
		return creature;
	}

	public void setCreature(String creature) {
		this.creature = creature;
	}
	

}
