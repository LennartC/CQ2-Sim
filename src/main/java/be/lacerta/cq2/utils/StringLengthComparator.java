package be.lacerta.cq2.utils;

import java.util.Comparator;

public class StringLengthComparator implements Comparator<String> {
	public int compare(String crit1, String crit2) {
		int l1 = crit1==null?0:crit1.length();
		int l2 = crit2==null?0:crit2.length();
		return l2-l1;
	}
}
