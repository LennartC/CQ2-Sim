package be.lacerta.cq2.orbparser;

import be.lacerta.cq2.objects.Enchant;
import be.lacerta.cq2.objects.Orb;
import be.lacerta.cq2.utils.CQ2Functions;

public class HeuristicNetherEffect implements OrbHeuristicValue {
	private Double value;
	private Double singleNodeValue;
	private int baseLevel;
	
	public HeuristicNetherEffect(int baseLevel) {
		this.baseLevel = baseLevel;
		value = 0.0;
	}
	
	public void setValue(Double value) {
		this.value = value;
		singleNodeValue = value;
	}

	public Number getValue() {
		return value;
	}

	public void setOrb(Orb orb) {
		singleNodeValue = CQ2Functions.calcNetherEffect(baseLevel, orb.getGems(), Enchant.EMERALD);
		value += singleNodeValue;
	}

	public OrbHeuristicValue createChild() {
		HeuristicNetherEffect created = new HeuristicNetherEffect(baseLevel);
		created.setValue(value.doubleValue());
		return created;
	}

	public Number getValue(Orb orb) {
		return CQ2Functions.calcNetherEffect(baseLevel, orb.getGems(), Enchant.EMERALD);
	}


}
