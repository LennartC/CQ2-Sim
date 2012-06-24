package be.lacerta.cq2.utils;

import java.util.Comparator;

import be.lacerta.cq2.sim.hbn.RevealCrit;

public class RevealCritStrengthComparator implements Comparator<RevealCrit>  {

	public int compare(RevealCrit c1, RevealCrit c2) {
		int s1 = c1==null?0:c1.getDamage()*2+c1.getHealth();
		int s2 = c2==null?0:c2.getDamage()*2+c2.getHealth();
		return s2-s1;
	}

}
