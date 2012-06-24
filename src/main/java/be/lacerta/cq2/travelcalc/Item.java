package be.lacerta.cq2.travelcalc;

public class Item {
	
	String item;

	public Item(String item) {
		this.item=item;
		heart = 0;

		dmgDecr = 0;
		defDecr = 0;
		defIncr = 0;

		knife = 0;
		pit = 0;
		collar = 0;
		jewel = 0;
		charm = 0;
		golem = 0;
		
		if (item.contains("Anguish Cry") || item.contains("AnCr")) {
		      defDecr = 7;
		    } else if (item.contains("Impaler Cry") || item.contains("ImCr")) {
		      defDecr = 10;
		    } else if (item.contains("Styx Cry") || item.contains("StCr")) {
		      defDecr = 13;
		    } else if (item.contains("Destruction Leech") || item.contains("DeLe")) {
		      defDecr = 8;
		      defIncr = 4;
		    } else if (item.contains("Abyss Leech") || item.contains("AbLe")) {
		      defDecr = 10;
		      defIncr = 5;

		    } else if (item.contains("Fallen Touch") || item.contains("FaTo")) {
		      dmgDecr = 32;
		    } else if (item.contains("Rotten Touch") || item.contains("RoTo")) {
		      dmgDecr = 58;
		    } else if (item.contains("Shadow Touch") || item.contains("ShTo")) {
		      dmgDecr = 75;
		    } else if (item.contains("Tormenter Touch") || item.contains("ToTo")) {
		      dmgDecr = 92;

		    } else if (item.contains("Tiny Charm") || item.contains("TiCh")) {
			charm = 50;
		} else if (item.contains("Small Charm") || item.contains("SmCh")) {
			charm = 60;
		} else if (item.contains("Putrid Charm")
				|| item.contains("PuCh")) {
			charm = 70;
		} else if (item.contains("Rancid Charm")
				|| item.contains("RaCh")) {
			charm = 90;
		} else if (item.contains("Diamond Charm")
				|| item.contains("DiCh")) {
			charm = 120;

		} else if (item.contains("Granite Axe")) {
			golem = 8;
		} else if (item.contains("Magma Blade") || item.contains("MaBl")) {
			golem = 10;
		} else if (item.contains("Pit Razor") || item.contains("PiRa")) {
			golem = 14;
		} else if (item.contains("Pit Scythe") || item.contains("PiSc")) {
			golem = 17;
		} else if (item.contains("Fear Mace") || item.contains("FeMa")) {
			golem = 20;
		} else if (item.contains("Nightmare Cleaver") || item.contains("NiCl")) {
			golem = 25;
		}


	}

	public int getDmgDecr() {
		return dmgDecr;
	}

	public int getDefDecr() {
		return defDecr;
	}

	public int getDefIncr() {
		return defIncr;
	}

	public int getHeart() {
		return heart;
	}

	public int getKnife() {
		return knife;
	}

	public int getPit() {
		return pit;
	}

	public int getCollar() {
		return collar;
	}

	public int getJewel() {
		return jewel;
	}

	public int getCharm() {
		return charm;

	}

	public int getGolem() {
		return golem;
	}

	public int getIndex() {
		return idx;
	}

	private int dmgDecr;

	private int defDecr;

	private int defIncr;

	private int heart;

	private int knife;

	private int pit;

	private int collar;

	private int jewel;

	private int charm;

	private int golem;

	private int idx;
	
	public String toString() {
		return this.item;
	}
}
