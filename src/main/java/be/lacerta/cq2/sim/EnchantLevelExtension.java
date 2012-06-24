package be.lacerta.cq2.sim;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnchantLevelExtension extends AbstractSimExtension {

	public void run(String page) {
		if (post) {
			Hashtable<String,int[]> critTable = new Hashtable<String,int[]>();
			int total = 0;
			String creatures = getString("creatures");
			Pattern p = Pattern.compile("^\\s*(\\D+):\\s*(\\d+)\\s*$");
			Matcher m = p.matcher(creatures);
			while (m.find()) {
				String mage = m.group(1);
				int amount = Integer.parseInt(m.group(2));
				int[] i = {amount, 0};
				total+=amount;
				critTable.put(mage, i);
			}
		}
		
	}

}
