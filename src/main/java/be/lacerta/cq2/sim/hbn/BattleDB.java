package be.lacerta.cq2.sim.hbn;

public class BattleDB extends HbnObject implements java.io.Serializable {
	int id;
	BattleDBSide attacker;
	BattleDBSide defender;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BattleDBSide getAttacker() {
		return attacker;
	}
	public void setAttacker(BattleDBSide attacker) {
		this.attacker = attacker;
	}
	public BattleDBSide getDefender() {
		return defender;
	}
	public void setDefender(BattleDBSide defender) {
		this.defender = defender;
	}
}
