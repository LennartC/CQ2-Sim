package be.lacerta.cq2.sim;

import be.lacerta.cq2.battlecalc.Battle;
import be.lacerta.cq2.objects.BattleResult;
import be.lacerta.cq2.objects.Enchant;
import be.lacerta.cq2.objects.Itherian;
import be.lacerta.cq2.objects.Item;
import be.lacerta.cq2.objects.Creature;
import be.lacerta.cq2.sim.hbn.BattleDB;
import be.lacerta.cq2.sim.hbn.BattleDBSide;
import be.lacerta.cq2.utils.StringUtils;

public class BattleCalcExtension extends AbstractSimExtension {

	Creature attCrit, defCrit;
	Itherian attIth, defIth;
	Enchant attEnchant, defEnchant;

	String[] fieldNames = {
			"item","itemenchant",
			"damage","health","def","name","level","same","diff",
			"ithdamage","ithhealth","ithdef","curse",
			"waveenchanttype","waveenchantlevel","class"
	};


	public void run(String page) {

		for (int i=0; i<fieldNames.length; i++) {
			request.setAttribute("defender_"+fieldNames[i], request.getParameter("defender."+fieldNames[i])==null?"":request.getParameter("defender."+fieldNames[i]));
			request.setAttribute("attacker_"+fieldNames[i], request.getParameter("attacker."+fieldNames[i])==null?"":request.getParameter("attacker."+fieldNames[i]));
		}
		
		int fight = 0;
		
		if (post) {
			fight = getCreatures();
		} else if (getInt("battleid") > 0) {
			fight = getCreaturesFromDB(getInt("battleid"));
		}
		
		if (fight>0) {
			BattleResult br;
			if (request.getParameter("calcpercentage") != null) {
				int turns=1000;
				br = Battle.fight(
						attCrit, attIth, attEnchant, getInt("attacker.same"), getInt("attacker.diff"),
						defCrit, defIth, defEnchant, getInt("defender.same"), getInt("defender.diff"),
						turns);
				request.setAttribute("battlecalc_result", StringUtils.convertBBCode("Based on "+turns+" calculations:\n"+
						"\nAttacker wins: "+(Math.floor(br.getAttackerWon() / Math.max(1,(turns/100))*100+.5)/100)+ "%"+
						"\nDefender wins: "+(Math.floor(br.getDefenderWon() / Math.max(1,(turns/100))*100+.5)/100)+ "%"+
						"\nBoth creatures die: "+(Math.floor(br.getDip() / Math.max(1,(turns/100))*100+.5)/100)+ "%"));
			} else {
				br = Battle.fight(
						attCrit, attIth, attEnchant, getInt("attacker.same"), getInt("attacker.diff"),
						defCrit, defIth, defEnchant, getInt("defender.same"), getInt("defender.diff"),
						1);
				request.setAttribute("battlecalc_result", StringUtils.convertBBCode(br.getReport()));
			}
			request.setAttribute("battlecalc_fight", fight);
		}
		
		
		request.setAttribute("creatures",be.lacerta.cq2.sim.hbn.Creature.getCreatures("name"));
		setPath("/battlecalc.jsp");
	}

	private int getCreatures() {

		
		BattleDBSide attacker = new BattleDBSide();
		getProperties(attacker,"attacker");
		attacker.save();
		
		BattleDBSide defender = new BattleDBSide();
		getProperties(defender,"defender");
		defender.save();
		
		
		BattleDB battle = new BattleDB();
		battle.setAttacker(attacker);
		//System.out.println("attacker id: "+battle.getAttacker().getId());
		battle.setDefender(defender);
		battle.save();
		//System.out.println("attacker id: "+battle.getAttacker().getId());
		
		attCrit = new Creature();
		attIth = new Itherian();
		setProperties(battle.getAttacker(),"attacker",attCrit,attIth);

		defCrit = new Creature();
		defIth = new Itherian();
		setProperties(battle.getDefender(),"defender",defCrit,defIth);
		
		return battle.getId();
	}

	
	private Creature setProperties(BattleDBSide side, String prefix, Creature crit, Itherian ith) {
		
		Item item = new Item(side.getItem(),side.getItemenchant());
		crit.setName(side.getName());
		crit.setCreatureClass(side.getCritClass());
		crit.setDamage(side.getDamage());
		crit.setHealth(side.getHealth());
		crit.setAirDef(side.getDef());
		crit.setForestDef(side.getDef());
		crit.setEarthDef(side.getDef());
		crit.setDeathDef(side.getDef());
		crit.setCurse(side.getCurse());
		crit.setItem(item);

		crit.setName(side.getName().equals("")?"creature":side.getName());
		
		ith.setIthDamage(side.getIthdamage());
		ith.setIthHealth(side.getIthhealth());
		ith.setIthDefence(side.getIthdef());

		if (("attacker").equals(prefix)) {
			attEnchant = new Enchant(side.getWaveenchanttype(),side.getWaveenchantlevel());
		} else {
			defEnchant = new Enchant(side.getWaveenchanttype(),side.getWaveenchantlevel());
		}
		
		
		request.setAttribute(prefix+"_item", side.getItem());
		request.setAttribute(prefix+"_itemenchant", side.getItemenchant());
		request.setAttribute(prefix+"_same", side.getSame());
		request.setAttribute(prefix+"_diff", side.getDiff());
		request.setAttribute(prefix+"_damage", side.getDamage());
		request.setAttribute(prefix+"_health", side.getHealth());
		request.setAttribute(prefix+"_def", side.getDef());
		request.setAttribute(prefix+"_curse", side.getCurse());
		request.setAttribute(prefix+"_name", side.getName());
		request.setAttribute(prefix+"_class", side.getCritClass());
		request.setAttribute(prefix+"_ithdamage", side.getIthdamage());
		request.setAttribute(prefix+"_ithhealth", side.getIthhealth());
		request.setAttribute(prefix+"_ithdef", side.getIthdef());
		request.setAttribute(prefix+"_waveenchanttype", side.getWaveenchanttype());
		request.setAttribute(prefix+"_waveenchantlevel", side.getWaveenchantlevel());
		
		return crit;
	}
	
	private void getProperties(BattleDBSide side, String prefix) {
		side.setItem(getString(prefix+".item"));
		side.setItemenchant(getString(prefix+".itemenchant"));
		side.setSame(getInt(prefix+".same"));
		side.setDiff(getInt(prefix+".diff"));
		side.setDamage(getInt(prefix+".damage"));
		side.setHealth(getInt(prefix+".health"));
		side.setDef(getInt(prefix+".def"));
		side.setCurse(getString(prefix+".curse"));
		side.setName(getString(prefix+".name"));
		side.setCritClass(getString(prefix+".class"));
		side.setIthdamage(getInt(prefix+".ithdamage"));
		side.setIthhealth(getInt(prefix+".ithhealth"));
		side.setIthdef(getInt(prefix+".ithdef"));
		side.setWaveenchanttype(getInt(prefix+".waveenchanttype"));
		side.setWaveenchantlevel(getInt(prefix+".waveenchantlevel"));
	}
	
	private int getCreaturesFromDB(int id) {

		BattleDB battle = (BattleDB)BattleDB.get(BattleDB.class, id);
		if (battle != null) {
			attCrit = new Creature();
			attIth = new Itherian();
			setProperties(battle.getAttacker(),"attacker",attCrit,attIth);
			
			defCrit = new Creature();
			defIth = new Itherian();
			setProperties(battle.getDefender(),"defender",defCrit,defIth);			
			return id;
		} else return 0;
	}

}
