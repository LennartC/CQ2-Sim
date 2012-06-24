package be.lacerta.cq2.battlecalc.objects;

public class Enchant {
	public final static int RUBY = 1;
	public final static int SAPPHIRE = 2;
	public final static int CARNELIAN = 3;
	public final static int OPAL = 4;
	
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
