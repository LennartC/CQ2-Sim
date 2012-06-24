package be.lacerta.cq2.sim.hbn;

public class RevealNote extends HbnObject implements java.io.Serializable {
	private Reveal reveal;
	private Note note;

	public RevealNote() {
	}

	public RevealNote(Reveal reveal, Note note) {
		this.reveal = reveal;
		this.note = note;
	}

	public Reveal getReveal() {
		return this.reveal;
	}

	public void setReveal(Reveal reveal) {
		this.reveal = reveal;
	}

	public Note getNote() {
		return this.note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

}
