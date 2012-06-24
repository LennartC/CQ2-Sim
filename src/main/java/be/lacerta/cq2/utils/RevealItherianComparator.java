package be.lacerta.cq2.utils;

import java.util.Comparator;

import be.lacerta.cq2.sim.hbn.RevealCrit;

public class RevealItherianComparator implements Comparator<RevealCrit> {

	public int compare(RevealCrit o1, RevealCrit o2) {
		int ip1 = o1==null?0:o1.getIth();
		int ip2 = o2==null?0:o2.getIth();
		return ip2-ip1;
	}

}
