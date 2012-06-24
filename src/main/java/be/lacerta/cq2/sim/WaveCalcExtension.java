package be.lacerta.cq2.sim;

import java.util.ArrayList;
import java.util.List;

import be.lacerta.cq2.battlecalc.Fight;
import be.lacerta.cq2.battlecalc.objects.Creature;
import be.lacerta.cq2.battlecalc.objects.Enchant;
import be.lacerta.cq2.battlecalc.objects.Item;
import be.lacerta.cq2.battlecalc.objects.Itherian;
import be.lacerta.cq2.utils.StringUtils;

public class WaveCalcExtension extends AbstractSimExtension {

	Creature[] attCrit, defCrit;
	Enchant attEnchant, defEnchant;

	String[] fieldNames = {
			"item","itemenchant",
			"damage","health","Fdef","Ddef","Adef","Edef","name","level","same","diff",
			"ithdamage","ithhealth","ithdef","curse","class","enchantvs"
	};


	public void run(String page) {

		for (int i=0; i<fieldNames.length; i++) {
			for (int j=1; j<=15; j++) {
				request.setAttribute("defender-"+j+"_"+fieldNames[i], request.getParameter("defender-"+j+"."+fieldNames[i])==null?"":request.getParameter("defender-"+j+"."+fieldNames[i]));
				request.setAttribute("attacker-"+j+"_"+fieldNames[i], request.getParameter("attacker-"+j+"."+fieldNames[i])==null?"":request.getParameter("attacker-"+j+"."+fieldNames[i]));
			}
		}
		request.setAttribute("defender_waveenchanttype", request.getParameter("defender.waveenchanttype")==null?"":request.getParameter("defender.waveenchanttype"));
		request.setAttribute("attacker_waveenchanttype", request.getParameter("attacker.waveenchanttype")==null?"":request.getParameter("attacker.waveenchanttype"));
		request.setAttribute("defender_waveenchantlevel", request.getParameter("defender.waveenchantlevel")==null?"":request.getParameter("defender.waveenchantlevel"));
		request.setAttribute("attacker_waveenchantlevel", request.getParameter("attacker.waveenchantlevel")==null?"":request.getParameter("attacker.waveenchantlevel"));
		request.setAttribute("defender_waveenchantvs", request.getParameter("defender.waveenchantvs")==null?"":request.getParameter("defender.waveenchantvs"));
		request.setAttribute("attacker_waveenchantvs", request.getParameter("attacker.waveenchantvs")==null?"":request.getParameter("attacker.waveenchantvs"));

		if (post) {
			
			List<String> results = new ArrayList<String>();

			if (getCreatures()) {
				for (int i=0; i<attCrit.length; i++) {
					Fight myFight = new Fight(attCrit[i], attEnchant, defCrit[i], defEnchant);
					
					if (defCrit[i] == null || defCrit[i].getDamage() == 0) {
						results.add("Attacking "+attCrit[i].getName()+" was left undefended.");
					} else if (request.getParameter("calcpercentage") != null) {
						myFight.calc(1000);
						results.add(StringUtils.convertBBCode("Based on 10000 calculations:\n"+
							"\nAttacker wins: "+myFight.getWin()+ "%"+
							"\nDefender wins: "+myFight.getLost()+ "%"+
							"\nBoth creatures die: "+myFight.getDip()+ "%"));
					} else {
						myFight.calc(1);
						results.add(StringUtils.convertBBCode(myFight.getFight()));
					}
				}
			}
			
			request.setAttribute("wavecalc_results", results);
		}

		request.setAttribute("creatures",be.lacerta.cq2.sim.hbn.Creature.getCreatures("name"));
		setPath("/wavecalc.jsp");
	}

	private boolean getCreatures() {
		attCrit = new Creature[15];
		getCreatures(attCrit,"attacker");
		defCrit = new Creature[15];
		getCreatures(defCrit,"defender");
		
		attEnchant = new Enchant(getInt("attacker.waveenchanttype"),
				 				getInt("attacker.waveenchantlevel"),
				 				getString("attacker.waveenchantvs"));
		defEnchant = new Enchant(getInt("defender.waveenchanttype"),
				 				getInt("defender.waveenchantlevel"),
				 				getString("defender.waveenchantvs"));
		return true;
	}

	private boolean getCreatures(Creature[] crits, String side) {
		for (int i=1; i<=crits.length; i++) {
			Item item = new Item(getString(side+"-"+i+".item"),getString(side+"-"+i+".itemenchant"),0,0);
			item.setEnchantVs(getString(side+"-"+i+".enchantvs"));
			
			crits[i-1] = new Creature(getInt(side+"-"+i+".damage"),
					   getInt(side+"-"+i+".health"),
					   0,
					   item,
					   getString(side+"-"+i+".curse"));
			try { crits[i-1].setAirDef(getString(side+"-"+i+".Adef")); } catch (NumberFormatException nfe) {}
			try { crits[i-1].setForestDef(getString(side+"-"+i+".Fdef")); } catch (NumberFormatException nfe) {}
			try { crits[i-1].setDeathDef(getString(side+"-"+i+".Ddef")); } catch (NumberFormatException nfe) {}
			try { crits[i-1].setEarthDef(getString(side+"-"+i+".Edef")); } catch (NumberFormatException nfe) {}
			crits[i-1].setName(("").equals(getString(side+"-"+i+".name")) ?
					"creature" : getString(side+"-"+i+".name"));
			crits[i-1].setCreatureClass(getString(side+"-"+i+".class"));
			if (!crits[i-1].getName().equals("")) {
				be.lacerta.cq2.sim.hbn.Creature hbnCrit = be.lacerta.cq2.sim.hbn.Creature.getCreature(crits[i-1].getName());
				if (hbnCrit != null) crits[i-1].setRace(hbnCrit.getRace().getName());
			}
			Itherian ith  = new Itherian(getInt(side+"-"+i+".ithdamage"),
					  getInt(side+"-"+i+".ithhealth"),
					  getInt(side+"-"+i+".ithdef"));
			crits[i-1].setIth(ith);
		}
		fixIthsAndItems(crits);
		return true;
	}

	private void fixIthsAndItems(Creature[] crits) {
		Itherian ith = new Itherian();
		
		for (Creature crit : crits) {
			crit.getItem().setSameCrits(0);
			crit.getItem().setDiffCrits(0);
			
			if (crit.getItem().getName().toLowerCase().matches(".* Rune")) {
				for (Creature c2 : crits) {
					if (c2.getRace().equalsIgnoreCase("cleric")) {
						crit.getItem().setSameCrits(crit.getItem().getSameCrits()+1);
					} else {
						crit.getItem().setDiffCrits(crit.getItem().getDiffCrits()+1);
					}
				}
			} else if (crit.getItem().getName().toLowerCase().matches("Diabolic .*")) {
				for (Creature c2 : crits) {
					if (c2.getRace().equalsIgnoreCase("diabolic horde")) {
						crit.getItem().setSameCrits(crit.getItem().getSameCrits()+1);
					}
				}				
			} else if (crit.getItem().getName().toLowerCase().matches(".* Infusion")) {
				for (Creature c2 : crits) {
					if (crit.getName().equalsIgnoreCase(c2.getName())) {
						crit.getItem().setSameCrits(crit.getItem().getSameCrits()+1);
					}
					crit.getItem().setSameCrits(crit.getItem().getSameCrits()-1);
				}				
			} else if (crit.getItem().getName().toLowerCase().matches("Book of Light")) {
				for (Creature c2 : crits) {
					if (c2.getName().equalsIgnoreCase("Light Hunter")) {
						crit.getItem().setSameCrits(crit.getItem().getSameCrits()+1);
					}
				}				
			} else if (crit.getItem().getName().toLowerCase().matches("Servant.*Guide")) {
				for (Creature c2 : crits) {
					if (c2.getName().equalsIgnoreCase("Servant Hunter")) {
						crit.getItem().setSameCrits(crit.getItem().getSameCrits()+1);
					}
				}				
			} else if (crit.getItem().getName().toLowerCase().matches("Director.*Manual")) {
				for (Creature c2 : crits) {
					if (c2.getName().equalsIgnoreCase("Hunter Director")) {
						crit.getItem().setSameCrits(crit.getItem().getSameCrits()+1);
					}
				}				
			}

			ith.setDamage(ith.getDamage()+crit.getIth().getDamage());
			ith.setHealth(ith.getHealth()+crit.getIth().getHealth());
			ith.setDefence(ith.getDefence()+crit.getIth().getDefence());
			
			crit.setIth(ith);
		}
	}

}
