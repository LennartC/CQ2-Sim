package be.lacerta.cq2.orbparser;

import be.lacerta.cq2.objects.Gem;
import be.lacerta.cq2.objects.Orb;

public class HeuristicGemPower implements OrbHeuristicValue {
	private Integer value = 0;

	public Number getValue() {
		return value;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}

	public void setOrb(Orb orb) {
		int singleNodeValue = 0;
		for (Gem gem : orb.getGems()) {
			if (gem!=null) singleNodeValue += gem.getPower();
		}
		value += singleNodeValue;
	}

	public OrbHeuristicValue createChild() {
		HeuristicGemPower created = new HeuristicGemPower();
		created.setValue(value.intValue());
		return created;
	}

	public Number getValue(Orb orb) {
		int power = 0;
		for (Gem gem : orb.getGems()) {
			if (gem!=null) power += gem.getPower();
		}
		return power;
	}
}
