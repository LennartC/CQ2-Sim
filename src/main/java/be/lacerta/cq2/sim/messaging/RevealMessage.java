package be.lacerta.cq2.sim.messaging;

import java.util.Date;

import be.lacerta.cq2.sim.hbn.Reveal;

public class RevealMessage implements Message {

	private static final long serialVersionUID = 3260216505184837921L;
	
	private MageMessage mage;
	private String unparsed;
	private Date date;
	
	public MageMessage getMage() {
		return mage;
	}
	public void setMage(MageMessage mage) {
		this.mage = mage;
	}
	public String getUnparsed() {
		return unparsed;
	}
	public void setUnparsed(String unparsed) {
		this.unparsed = unparsed;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public static RevealMessage fromEntity(Reveal entity) {
        RevealMessage reveal = new RevealMessage();
        MageMessage mage = MageMessage.fromEntity(entity.getMage());
        mage.setForestSkill(entity.getForestSkill());
        mage.setDeathSkill(entity.getDeathSkill());
        mage.setAirSkill(entity.getAirSkill());
        mage.setEarthSkill(entity.getEarthSkill());
        reveal.setMage(mage);
        reveal.setDate(entity.getTime());
        reveal.setUnparsed(entity.getUnparsed());
        return reveal;
	}
}
