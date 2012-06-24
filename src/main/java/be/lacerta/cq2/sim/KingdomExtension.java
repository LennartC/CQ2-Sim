package be.lacerta.cq2.sim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import be.lacerta.cq2.sim.hbn.Kingdom;
import be.lacerta.cq2.sim.hbn.Mage;
import be.lacerta.cq2.sim.hbn.Reveal;
import be.lacerta.cq2.sim.hbn.RevealCrit;
import be.lacerta.cq2.utils.PageParser;
import be.lacerta.cq2.utils.RevealCritStrengthComparator;
import be.lacerta.cq2.utils.RevealItherianComparator;

public class KingdomExtension extends AbstractSimExtension {

	public void run(String page) {
		String kdName = getString("kingdom");
		if (post) {
			String action = getString("action");
			if (action.equals("parse")) {
				kdName = PageParser.parseKingdom(getString("kdpage"));
			}
		}
		
		if (kdName != null && !kdName.equals("")) {
			Kingdom kingdom = Kingdom.loadByName(kdName);
			if (kingdom != null && kingdom.getInhabitants()!=null && kingdom.getInhabitants().size()>0) {

				Hashtable<String,Integer> creatureList = new Hashtable<String,Integer>();
				List<RevealCrit> forestItherianList = new ArrayList<RevealCrit>();
				List<RevealCrit> deathItherianList = new ArrayList<RevealCrit>();
				List<RevealCrit> airItherianList = new ArrayList<RevealCrit>();
				List<RevealCrit> earthItherianList = new ArrayList<RevealCrit>();
				List<RevealCrit> netherList = new ArrayList<RevealCrit>();
				
				for (Mage mage : kingdom.getInhabitants()) {
					Reveal r = Reveal.getRevealByMage(mage.getName());
					if (r!=null) for (RevealCrit crit : r.getCreatures()) {
						int amount = 0;
						if (creatureList.get(crit.getName())!=null)
							amount = creatureList.get(crit.getName());
						creatureList.put(crit.getName(),++amount);
						
						if (RevealCrit.TYPE_ITHERIAN.equals(crit.getType())) {
						    if (("forest").equalsIgnoreCase(crit.getCreatureClass())) {
						    	forestItherianList.add(crit);
							} else if (("death").equalsIgnoreCase(crit.getCreatureClass())) {
								deathItherianList.add(crit);
							} else if (("air").equalsIgnoreCase(crit.getCreatureClass())) {
								airItherianList.add(crit);
							} else if (("earth").equalsIgnoreCase(crit.getCreatureClass())) {
								earthItherianList.add(crit);
							}
						}
						else if (RevealCrit.TYPE_NETHER.equals(crit.getType())) netherList.add(crit);
					}
				}

				Collections.sort(forestItherianList, new RevealItherianComparator());
				Collections.sort(deathItherianList, new RevealItherianComparator());
				Collections.sort(airItherianList, new RevealItherianComparator());
				Collections.sort(earthItherianList, new RevealItherianComparator());
				Collections.sort(netherList, new RevealCritStrengthComparator());
				kingdom.setModified();
				
				request.setAttribute("kingdom", kingdom);
				request.setAttribute("kingdom_creatures", creatureList);
				request.setAttribute("kingdom_nethers", netherList);
				request.setAttribute("kingdom_forestitherians", forestItherianList);
				request.setAttribute("kingdom_deathitherians", deathItherianList);
				request.setAttribute("kingdom_airitherians", airItherianList);
				request.setAttribute("kingdom_earthitherians", earthItherianList);
				
			} else {
				request.setAttribute("message", "Kingdom not found");
			}
		}
		
		setPath("/kingdom.jsp");
	}
}
