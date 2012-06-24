package be.lacerta.cq2.orbparser;

import be.lacerta.cq2.objects.Orb;

public interface OrbHeuristicValue {
	public OrbHeuristicValue createChild();
	
	public Number getValue();
	
	public void setOrb(Orb orb);
	
	public Number getValue(Orb orb);
}
