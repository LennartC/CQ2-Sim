package be.lacerta.cq2.objects;

public class BattleResult {
	String report;
	int attackerWon;
	int defenderWon;
	int dip;
	int exhaust;
	Creature attacker;
	Creature defender;

	public BattleResult(Creature attacker, Creature defender) {
		this("",0,0,0,0,attacker,defender);
	}

	public BattleResult(String report, int attackerWon, int defenderWon,
			int dip, int exhaust, Creature attacker, Creature defender) {
		super();
		this.report = report;
		this.attackerWon = attackerWon;
		this.defenderWon = defenderWon;
		this.dip = dip;
		this.exhaust = exhaust;
		this.attacker = attacker;
		this.defender = defender;
	}
	
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public int getAttackerWon() {
		return attackerWon;
	}
	public void setAttackerWon(int attackerWon) {
		this.attackerWon = attackerWon;
	}
	public int getDefenderWon() {
		return defenderWon;
	}
	public void setDefenderWon(int defenderWon) {
		this.defenderWon = defenderWon;
	}
	public int getDip() {
		return dip;
	}
	public void setDip(int dip) {
		this.dip = dip;
	}

	public int getExhaust() {
		return exhaust;
	}

	public void setExhaust(int exhaust) {
		this.exhaust = exhaust;
	}

	public Creature getAttacker() {
		return attacker;
	}

	public void setAttacker(Creature attacker) {
		this.attacker = attacker;
	}

	public Creature getDefender() {
		return defender;
	}

	public void setDefender(Creature defender) {
		this.defender = defender;
	}
	public int getCalculations() {
		return attackerWon+defenderWon+dip+exhaust;
	}
	
	public void append(String s) {
		report += s;
	}
	public int incrAttackerWon() {
		return ++attackerWon;
	}
	public int incrDefenderWon() {
		return ++defenderWon;
	}
	public int incrDip() {
		return ++dip;
	}
	public int incrExhaust() {
		return ++exhaust;
	}
}
