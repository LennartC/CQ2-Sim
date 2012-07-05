/*Author Imbalancing*/
package be.lacerta.cq2.sim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import be.lacerta.cq2.sim.hbn.RevealCrit;
import be.lacerta.cq2.utils.PageParser;
import be.lacerta.cq2.utils.Quadruplet;

public class DefenseParserExtension extends AbstractSimExtension {

	public void run(String page) {
		if (post) {
			int totaal=0;
			List<RevealCrit> crits = PageParser.parseReveal(getString("inputPage"));
			Map<Quadruplet<String,String,String,String>,Integer> creatureMap = new HashMap<Quadruplet<String,String,String,String>,Integer>();
			
			for (RevealCrit crit: crits) {
				Quadruplet<String,String,String,String> key = new Quadruplet<String,String,String,String>();
				key.setFirst(crit.getName());
				key.setSecond(crit.getItem());
				key.setThird(crit.getEnchant());
				key.setFourth(crit.getCurse());
				
				int typeTotal = 1;
				totaal++;
				if (creatureMap.containsKey(key)) {
					typeTotal += creatureMap.get(key);
				}
				creatureMap.put(key, typeTotal);
				crit.delete();
			}
			
			
			List<Quadruplet<String,String,String,String>> keys = new ArrayList<Quadruplet<String,String,String,String>>();
			keys.addAll(creatureMap.keySet());
			Collections.sort(keys, new ValueComparator(creatureMap));
			
			Map<Quadruplet<String,String,String,String>,Integer> linkedMap = new LinkedHashMap<Quadruplet<String,String,String,String>,Integer>();
			for (Quadruplet<String,String,String,String> key : keys) {
				linkedMap.put(key, creatureMap.get(key));
			}
			
			request.setAttribute("totaal",totaal);
			request.setAttribute("defparser_result", linkedMap);
		}
		setPath("/kingdomdefenseparser.jsp");
	}
	
	class ValueComparator implements Comparator<Quadruplet<String,String,String,String>> {
		  Map<Quadruplet<String,String,String,String>,Integer> base;
		  public ValueComparator(Map<Quadruplet<String,String,String,String>,Integer> base) {
		      this.base = base;
		  }

		  public int compare(Quadruplet<String,String,String,String> a, Quadruplet<String,String,String,String> b) {
			  return base.get(b)-base.get(a);
		  }
	}
}