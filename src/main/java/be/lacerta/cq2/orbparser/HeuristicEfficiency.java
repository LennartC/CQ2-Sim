package be.lacerta.cq2.orbparser;

import be.lacerta.cq2.objects.Orb;
import be.lacerta.cq2.utils.CQ2Functions;

public class HeuristicEfficiency implements OrbHeuristicValue {
	double value;
	int baseLevel;
	
	public HeuristicEfficiency(int baseLevel) {
		this.baseLevel = baseLevel;
	}
	
	public OrbHeuristicValue createChild() {
		return new HeuristicEfficiency(baseLevel);
	}

	public Number getValue() {
		return value;
	}

	public Double getValue(Orb orb) {
		return CQ2Functions.calcRealNetherEfficiency(baseLevel, orb);
	}

	public void setOrb(Orb orb) {
		value = getValue(orb);
	}

}
