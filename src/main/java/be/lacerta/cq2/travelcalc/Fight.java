package be.lacerta.cq2.travelcalc;


public class Fight {

	public Fight(Creature crit1, Creature crit2) {
		myCrit = crit1;
		oppCrit = crit2;
		myItem = myCrit.getItem();
		oppItem = oppCrit.getItem();
		fight = "";
	}

	public double win() {
		int turns = 20;
		fight = "\n";
		for (int i = 0; i < turns; i++) {
			double myDmg = myCrit.getDamage();
			double myDef = myCrit.getDefence();
			int myHlt = myCrit.getHealth();
			double oppDmg = oppCrit.getDamage();
			double oppDef = oppCrit.getDefence();
			int oppHlt = oppCrit.getHealth();

			int maxBdef = (int) Math.max(140D, oppCrit.getDefence());
			int maxAdef = (int) Math.max(140D, myCrit.getDefence());
			int oppRDp = (int) Math
					.floor(oppDmg
							* ((150D - Math.min(maxAdef, Math.max(0.0D, myDef))) / 100D)
							+ 0.5D);
			for (int j = 1; myHlt > 0 && oppHlt > 0 && j <= 40; j++) {
				int myRD = (int) Math.floor(myDmg
						* ((150 - Math.min(maxBdef, Math
								.max(0, (oppDef - j
										* (myItem.getDefDecr() - oppItem
												.getDefIncr()))))) / 100) + .5);
				int oppRD = (int) Math.floor(oppDmg
						* ((150 - Math.min(maxAdef, Math
								.max(0, (myDef - j
										* (oppItem.getDefDecr() - myItem
												.getDefIncr()))))) / 100) + .5);
				oppRD -= j * myItem.getDmgDecr();
				if (oppRD <= 0)
					if (maxAdef == 150)
						oppRD = 0;
					else
						oppRD = 1;
				myHlt += myItem.getCharm();
				oppHlt -= myItem.getCharm();

				if (myItem.getGolem() != 0) {
					myRD = calcGolem(myRD, myItem.getGolem(), j);
				} else if (myItem.getHeart() != 0
						&& (int) (Math.random() * 100D) < 60)
					myHlt += myItem.getHeart();
				else if (myItem.getPit() != 0
						&& (int) (Math.random() * 100D) < myItem.getPit())
					myRD *= 2;
				else if (myItem.getCollar() != 0
						&& (int) (Math.random() * 100D) < myItem.getCollar())
					oppRD = 0;
				else if (myItem.getKnife() != 0
						&& (int) (Math.random() * 100D) < myItem.getKnife())
					myRD = (int) Math.floor(myDmg * 1.5D + 0.5D);
				else if (myItem.getJewel() != 0
						&& (int) (Math.random() * 100D) < myItem.getJewel())
					myRD += oppRDp;
				if (myRD <= 0)
					if (maxBdef == 150)
						myRD = 0;
					else
						myRD = 1;
				myHlt = Math.max(0, myHlt - oppRD);
				oppHlt = Math.max(0, oppHlt - myRD);
				oppRDp = oppRD;
				if (i < 1) {

					fight += "Your " + myCrit.getName() + " did " + myRD
							+ " damage -> opponent's " + oppCrit.getName()
							+ " has " + oppHlt + " health left.\n";
					fight += "Opponent's " + oppCrit.getName() + " did "
							+ oppRD + " damage -> your " + myCrit.getName()
							+ " has " + myHlt + " health left.\n";
				}
			}

			if (myHlt > 0 && oppHlt == 0) {
				if (i < 1) {
					fight += "Your " + myCrit.getName()
							+ " killed your opponent's " + oppCrit.getName()
							+ ".\n";
				}
				win++;
				continue;
			}
			if (oppHlt > 0 && myHlt == 0) {
				if (i < 1) {
					fight += "Attacking Creature got killed by the defending Creature. (defender wins)\n";
				}
				lost++;
				continue;
			}
			if (myHlt == 0 && oppHlt == 0) {
				if (i < 1) {
					fight += "Both Creatures died (Dip)\n";
				}
				dip++;
				continue;
			}
			if (myHlt > oppHlt) {
				if (i < 1) {
					fight += "Your " + myCrit.getName()
							+ " killed your opponent's exhausted "
							+ oppCrit.getName() + ".\n";
				}
				win++;
				continue;
			}
			if (oppHlt > myHlt) {
				if (i < 1) {
					fight += "Exhausted attacking Creature got killed by the defending Creature. (defender wins)\n";
				}
				lost++;
			} else {
				if (i < 1) {
					fight += "Well well well, 3 holes in the ground and someone is exhausted\n";
				}
			}

		}

		win = Math
				.floor((win / (double) Math.max(1, turns / 100)) * 100D + 0.5D) / 100D;
		lost = Math
				.floor((lost / (double) Math.max(1, turns / 100)) * 100D + 0.5D) / 100D;
		dip = Math
				.floor((dip / (double) Math.max(1, turns / 100)) * 100D + 0.5D) / 100D;
		return win;
	}

	private int calcGolem(int rd, int gollem, int turns) {
		double procent = (double) gollem / 100D;
		double trd = rd;
		for (int i = 0; i < turns; i++)
			trd += trd * procent;

		rd = (int) Math.floor(trd + 0.5D);
		return rd;
	}

	public String getFight() {
		return fight;
	}

	public double getWin() {
		return win;
	}

	public double getLost() {
		return lost;
	}

	public double getDip() {
		return dip;
	}

	public Creature getMyCrit() {
		return myCrit;
	}

	public Creature getOppCrit() {
		return oppCrit;
	}

	public Creature myCrit;

	public Creature oppCrit;

	public Item myItem;

	public Item oppItem;

	public double win;

	public double lost;

	public double dip;

	public String fight;
}