package be.lacerta.cq2.sim.hbn;

// Generated 11-jun-2009 13:52:24 by Hibernate Tools 3.2.4.GA

/**
 * RaidResultNote generated by hbm2java
 */
public class RaidResultNote extends HbnObject implements java.io.Serializable {

	private RaidResult raidresult;
	private Note note;

	public RaidResultNote() {
	}

	public RaidResultNote(RaidResult raidresult, Note note) {
		this.raidresult = raidresult;
		this.note = note;
	}

	public RaidResult getRaidresult() {
		return this.raidresult;
	}

	public void setRaidresult(RaidResult raidresult) {
		this.raidresult = raidresult;
	}

	public Note getNote() {
		return this.note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

}
