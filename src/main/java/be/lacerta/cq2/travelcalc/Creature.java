package be.lacerta.cq2.travelcalc;

public class Creature {
	public Creature(String critname, int dmg, int hlt, int ownDef,
			String itm, int aantal, int aantalander, String oppRace) {
		damage = 0;
		defence = 0;
		health = 0;
		mass = 0;
		massmin = 0;
		name = critname;
		if (itm.equals("SpRu") || itm.contains("Spider Rune")) {
			mass = 45;
			massmin = 3;
		} else if (itm.contains("ViRu") || itm.contains("Viper Rune")) {
			mass = 60;
			massmin = 6;
		} else if (itm.contains("RaRu") || itm.contains("Raptor Rune")) {
			mass = 70;
			massmin = 9;
		} else if (itm.contains("DiRu") || itm.contains("Disciple Rune")) {
			mass = 78;
			massmin = 19;
		} else if (itm.contains("ZeRu") || itm.contains("Zealot Rune")) {
			mass = 86;
			massmin = 24;
		} else if (itm.contains("MiRu") || itm.contains("Mirage Rune")) {
			mass = 97;
			massmin = 29;
		} else if (itm.contains("DeRu") || itm.contains("Delusion Rune")) {
			mass = 108;
			massmin = 34;
		} else if (itm.contains("DiFl") || itm.contains("Diabolic Flag"))
			mass = 35;
		else if (itm.contains("DiPe") || itm.contains("Diabolic Pendant"))
			mass = 30;
		else if (itm.contains("DiBa") || itm.contains("Diabolic Banner"))
			mass = 41;
		else if (itm.contains("DiSt") || itm.contains("Diabolic Standard"))
			mass = 46;
		else if (itm.contains("DiCa") || itm.contains("Diabolic Carrier"))
			mass = 52;
		else if (itm.contains("Diabolic Flame"))
			mass = 58;
		else if (itm.contains("DiIn") || itm.contains("Diabolic Inferno"))
			mass = 64;
		else if (itm.contains("DiAb") || itm.contains("Diabolic Abyss"))
			mass = 76;
		damage = dmg;
		health = hlt;
		defence = ownDef;
		theItem = new Item(itm);

		if (aantal > 0)
			damage = (int) (damage + mass * (double) aantal);
		if (aantalander > 0)
			damage = (int) (damage - massmin * (double) aantalander);
		if (itm.contains("MaBe")) {
			if (itm.contains(oppRace)) {
				damage = (int) (damage * 2.1);
			}
		}
//		logger.info("eigen dam1 :" + ownDmg);
		if (itm.contains("ImOf")) {
			if (itm.contains(oppRace)) {
				damage = (int) (damage * 1.7);
			}
		}
		if (itm.contains("MaIm")) {
			if (itm.contains(oppRace)) {
				defence = 100;
			}
		}
	}

	public Creature(String critname, int ownDmg, int ownHlth, int ownDef,
			String itm, String oppClass, String oppRace) {
		damage = ownDmg;
		health = ownHlth;
		defence = ownDef;
		theItem = new Item(itm);
		name = critname;

		if (itm.contains("DaBrHa") || itm.contains("Dark Brim Hammer")) {
			if (oppClass.equals("air")) {
				damage = (int) (damage + 450);
			} else {
				damage = (int) (damage + 300);
			}
		} else if (itm.contains("DaBrMa") || itm.contains("Dark Brim Maul")) {
			if (oppClass.equals("forest")) {
				damage = (int) (damage + 450);
			} else {
				damage = (int) (damage + 300);
			}
		} else if (itm.contains("BlCrHa")
				|| itm.contains("Black Crystal Hammer")) {
			if (oppClass.equals("earth")) {
				health = (int) (health + 750);
			} else {
				health = (int) (health + 500);
			}
		} else if (itm.contains("BlCrMa") || itm.contains("Black Cristal Maul")) {
			if (oppClass.equals("death")) {
				health = (int) (health + 750);
			} else {
				health = (int) (health + 500);
			}
		} else if (itm.contains("BeIn")) {
			health = (int) (health + 225);
			damage = (int) (damage + 225);

		}
		if (itm.contains("MaBe")) {
			if (itm.contains(oppRace)) {
				damage = (int) (damage * 2.1);
			}
		}
		if (itm.contains("ImOf")) {
			if (itm.contains(oppRace)) {
				damage = (int) (damage * 1.7);
			}
		}
		if (itm.contains("MaIm")) {
			if (itm.contains(oppRace)) {
				defence = 100;
			}
		}
	}

	public double getDamage() {
		return damage;
	}

	public int getHealth() {
		return health;
	}

	public double getDefence() {
		return defence;
	}

	public Item getItem() {
		return theItem;
	}

	public String getName() {
		return name;
	}

	public int damage;

	public int defence;

	public int health;

	public Item theItem;

	public int mass;

	public int massmin;

	public String name;
	
	public String toString() {
		return "(" + this.name + "," + this.damage + "," + this.health + "," + this.defence + "," + this.mass + "," + this.getItem()+")";
	}
}