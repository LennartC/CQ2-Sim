package be.lacerta.cq2.utils;

import java.util.Comparator;
import be.lacerta.cq2.objects.Creature;

public class CritLengthComparator implements Comparator<Creature> {

	public int compare(Creature crit1, Creature crit2) {
		int l1 = crit1.getName()==null?0:crit1.getName().length();
		int l2 = crit2.getName()==null?0:crit2.getName().length();
		return l2-l1;
	}

}
