package be.lacerta.cq2.utils;

import java.util.Comparator;

import be.lacerta.cq2.sim.hbn.User;

public class UserComparator implements Comparator<User>{

		public int compare(User o1, User o2) {
			return o1.getUsername().compareToIgnoreCase(o2.getUsername());
		}

}
