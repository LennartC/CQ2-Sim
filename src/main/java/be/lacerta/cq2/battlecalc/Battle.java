package be.lacerta.cq2.battlecalc;

import java.util.List;

import be.lacerta.cq2.objects.*;

public class Battle {
	List<Creature> attackers;
	List<Creature> defenders;
	Enchant attackEnchant;
	Enchant defendEnchant;
	public Battle(List<Creature> attackers, List<Creature> defenders,
			Enchant attackEnchant, Enchant defendEnchant) {
		super();
		this.attackers = attackers;
		this.defenders = defenders;
		this.attackEnchant = attackEnchant;
		this.defendEnchant = defendEnchant;
	}
	public List<Creature> getAttackers() {
		return attackers;
	}
	public void setAttackers(List<Creature> attackers) {
		this.attackers = attackers;
	}
	public List<Creature> getDefenders() {
		return defenders;
	}
	public void setDefenders(List<Creature> defenders) {
		this.defenders = defenders;
	}
	public Enchant getAttackEnchant() {
		return attackEnchant;
	}
	public void setAttackEnchant(Enchant attackEnchant) {
		this.attackEnchant = attackEnchant;
	}
	public Enchant getDefendEnchant() {
		return defendEnchant;
	}
	public void setDefendEnchant(Enchant defendEnchant) {
		this.defendEnchant = defendEnchant;
	}
	
	public static BattleResult fight(Creature attackingCrit, Itherian attackingIth, Enchant attackEnchant, int attSame, int attDiff,
									 Creature defendingCrit, Itherian defendingIth, Enchant defendEnchant, int defSame, int defDiff,
									 int turns) {
		BattleResult br = new BattleResult(attackingCrit,defendingCrit);
		
	    double attackDmg, attackDef, defendDmg, defendDef;
	    int attackHlt, defendHlt;
	    int attackRD, defendRD;
	    int attackRDp, defendRDp;
	    
	    Item attackingItem = attackingCrit.getItem();
	    Item defendingItem = defendingCrit.getItem();
	    
	    for (int i = 0; i < turns; i++) {

	      //int maxDefendDef = 140;
	      //int maxAttackDef = 140;

	      /* attacking crit */
	      attackDmg = attackingCrit.getDamage();
	      attackDef = attackingCrit.getDef(defendingCrit.getCreatureClass());
	      attackHlt = attackingCrit.getHealth();
	      if (attackingCrit.isMetad()) {
	        double m = Math.floor(Math.random()*59+1.5)/100;
	        attackDmg *= m; attackHlt *= m;
	      } else if (attackingCrit.isDoppeled()) {
	    	  attackingCrit.setCreatureClass(defendingCrit.getCreatureClass());
	          attackDmg = defendingCrit.getDamage();
	          attackDef = defendingCrit.getDef(attackingCrit.getCreatureClass());
	          attackHlt = defendingCrit.getHealth();    	  
	      }
	      if (attackingItem.getEnchantVs().equals("") || attackingItem.getEnchantVs().equals(defendingCrit.getRace())) {
	          if (attackingItem.isMaBe()) attackDmg *= 2.1;
	          else if (attackingItem.isMiBe()) attackDmg *= 1.9;
	          else if (attackingItem.isImOf()) attackDmg *= 1.7;
	          else if (attackingItem.isMajorImmunity()) attackDef = 100;
	      }
	      
	      if (attackEnchant != null && (attackEnchant.getCreature().equals("") || attackEnchant.getCreature().equals(attackingCrit.getName()))) {
	        attackDmg = attackEnchant.getDamage(attackDmg);
	        attackDef = attackEnchant.getDefense(attackDef);
	      }

	      attackDmg += attackingIth.getIthDamage();
	      attackDef += attackingIth.getIthDefence();
	      attackHlt += attackingIth.getIthHealth();
	      attackDmg += attackingItem.getMassIncr()*attSame;
	      attackDmg -= attackingItem.getMassDecr()*attDiff;
	      attackDmg -= attackingItem.getFiendDecr()*attSame;
	      attackHlt -= attackingItem.getFiendDecr()*attSame;
	      attackDmg += attackingItem.getDmgIncr();
	      attackHlt += attackingItem.getHealthIncr();
	      attackDmg += attackDmg*attackingItem.getBookOfLight()*attSame;
	      attackDmg += attackDmg*attackingItem.getServantGuide()*attSame;
	      attackDmg += attackDmg*attackingItem.getDirectorManual()*attSame;
	      attackDmg += attackDmg*attackingItem.getDmgIncrPerc();
	      attackHlt += attackHlt*attackingItem.getHealthIncrPerc();
	      if (("air").equals(defendingCrit.getCreatureClass().toLowerCase())) {
	    	  attackDmg += attackingItem.getDmgVsAirIncr();
	    	  attackHlt += attackingItem.getHealthVsAirIncr();
	    	  attackDef += attackingItem.getDefVsAirIncr();
	      } else if (("death").equals(defendingCrit.getCreatureClass().toLowerCase())) {
	    	  attackDmg += attackingItem.getDmgVsDeathIncr();
	    	  attackHlt += attackingItem.getHealthVsDeathIncr();
	    	  attackDef += attackingItem.getDefVsDeathIncr();
	      } else if (("earth").equals(defendingCrit.getCreatureClass().toLowerCase())) {
	    	  attackDmg += attackingItem.getDmgVsEarthIncr();
	    	  attackHlt += attackingItem.getHealthVsEarthIncr();
	    	  attackDef += attackingItem.getDefVsEarthIncr();
	      } else if (("forest").equals(defendingCrit.getCreatureClass().toLowerCase())) {
	    	  attackDmg += attackingItem.getDmgVsForestIncr();
	    	  attackHlt += attackingItem.getHealthVsForestIncr();
	    	  attackDef += attackingItem.getDefVsForestIncr();
	      } else {
	    	  attackDef += attackingItem.getDefIncr();
	      }
	      attackDef -= defendingItem.getKnife();
	      attackDef = (int)Math.max(0, attackDef);
	      
	      /* defending crit */
	      defendDmg = defendingCrit.getDamage();
	      defendDef = defendingCrit.getDef(attackingCrit.getCreatureClass());
	      defendHlt = defendingCrit.getHealth();
	      if (defendingCrit.isMetad()) {
	        double m = Math.floor(Math.random()*59+1.5)/100;
	        defendDmg *= m; defendHlt *= m;
	      }
	      
	      if (attackingItem.getEnchantVs().equals("") || attackingItem.getEnchantVs().equals(defendingCrit.getRace())) {
		      if (defendingItem.isMaBe()) defendDmg *= 2.1;
		      else if (defendingItem.isMiBe()) defendDmg *= 1.9;
		      else if (defendingItem.isImOf()) defendDmg *= 1.7;
		      else if (defendingItem.isMajorImmunity()) defendDef = 100;
	      }
	      
	      if (defendEnchant != null && (defendEnchant.getCreature().equals("") || defendEnchant.getCreature().equals(defendingCrit.getName()))) {
	        defendDmg = defendEnchant.getDamage(defendDmg);
	        defendDef = defendEnchant.getDefense(defendDef);
	      }
	      defendDmg += defendingIth.getIthDamage();
	      defendDef += defendingIth.getIthDefence();
	      defendHlt += defendingIth.getIthHealth();
	      defendDmg += defendingItem.getMassIncr()*defSame;
	      defendDmg -= defendingItem.getMassDecr()*defDiff;
	      defendDmg -= defendingItem.getFiendDecr()*defSame;
	      defendHlt -= defendingItem.getFiendDecr()*defSame;
	      defendDmg += defendingItem.getDmgIncr();
	      defendHlt += defendingItem.getHealthIncr();
	      defendDmg += defendDmg*defendingItem.getBookOfLight()*defSame;
	      defendDmg += defendDmg*defendingItem.getServantGuide()*defSame;
	      defendDmg += defendDmg*defendingItem.getDirectorManual()*defSame;
	      defendDmg += defendDmg*defendingItem.getDmgIncrPerc();
	      defendHlt += defendHlt*defendingItem.getHealthIncrPerc();
	      if (("air").equals(attackingCrit.getCreatureClass().toLowerCase())) {
	    	  defendDmg += defendingItem.getDmgVsAirIncr();
	    	  defendHlt += defendingItem.getHealthVsAirIncr();
	    	  defendDef += defendingItem.getDefVsAirIncr();
	      } else if (("death").equals(attackingCrit.getCreatureClass().toLowerCase())) {
	    	  defendDmg += defendingItem.getDmgVsDeathIncr();
	    	  defendHlt += defendingItem.getHealthVsDeathIncr();
	    	  defendDef += defendingItem.getDefVsDeathIncr();
	      } else if (("earth").equals(attackingCrit.getCreatureClass().toLowerCase())) {
	    	  defendDmg += defendingItem.getDmgVsEarthIncr();
	    	  defendHlt += defendingItem.getHealthVsEarthIncr();
	    	  defendDef += defendingItem.getDefVsEarthIncr();
	      } else if (("forest").equals(attackingCrit.getCreatureClass().toLowerCase())) {
	    	  defendDmg += defendingItem.getDmgVsForestIncr();
	    	  defendHlt += defendingItem.getHealthVsForestIncr();
	    	  defendDef += defendingItem.getDefVsForestIncr();
	      } else {
		      defendDef += defendingItem.getDefIncr();
	      }
	      defendDef -= attackingItem.getKnife();
	      defendDef = (int)Math.max(0, defendDef);
	      
	      /* default values */
	      int maxDefendDef = (int) Math.max(140,defendingCrit.getDef(attackingCrit.getCreatureClass()));
	      int maxAttackDef = (int) Math.max(140,attackingCrit.getDef(defendingCrit.getCreatureClass()));
	      defendDef = (int)Math.min(maxDefendDef, defendDef);
	      attackDef = (int)Math.min(maxAttackDef, attackDef);

	      attackRDp = (int) Math.floor(attackDmg * ((150 - Math.min(maxDefendDef,Math.max(0,defendDef))) / 100) + .5);
	      defendRDp = (int) Math.floor(defendDmg * ((150 - Math.min(maxAttackDef,Math.max(0,attackDef))) / 100) + .5);

	      for (int j = 1; attackHlt > 0 && defendHlt > 0 && j <= 40; j++) {
	      	//if (defendingItem.getKnife() != 0 && ((int) (Math.random()*100)) < defendingItem.getKnife()) attackDef = attackDef - 50;
	      	//if (attackingItem.getKnife() != 0 && ((int) (Math.random()*100)) < attackingItem.getKnife()) defendDef = defendDef - 50;
	        /* suffocation */
	        if (attackingCrit.isSuffocated()) { attackHlt -= (int) Math.floor((attackHlt * 0.15) + .5); }
	        if (defendingCrit.isSuffocated()) { defendHlt -= (int) Math.floor((defendHlt * 0.15) + .5); }

	        double tempDefendDef =  fixMinMaxDef(maxDefendDef, defendDef-j*attackingItem.getDefDecrPerTurn());
	        tempDefendDef = fixMinMaxDef(maxDefendDef, tempDefendDef+j*defendingItem.getDefIncrPerTurn());
	        
	        double tempAttackDef =  fixMinMaxDef(maxAttackDef, attackDef+j*attackingItem.getDefIncrPerTurn());
	        tempAttackDef = fixMinMaxDef(maxAttackDef, tempAttackDef-j*defendingItem.getDefDecrPerTurn());
	        
			attackRD = (int) Math.floor(attackDmg * ((150.0 - tempDefendDef) / 100) + .5);
	        defendRD = (int) Math.floor(defendDmg * ((150.0 - tempAttackDef) / 100) + .5);
	// attacker
	        defendRD -= (j*attackingItem.getDmgDecrPerTurn());
	        if (defendRD <= 0) if (maxAttackDef == 150) defendRD = 0; else defendRD = 1;
	        attackHlt += Math.min(defendHlt,attackingItem.getCharm()); defendHlt -= attackingItem.getCharm();
	        if (attackingItem.getGolem() != 0) attackRD = calcGolem(attackRD,attackingItem.getGolem(),j);
	        else if (attackingItem.getHeart() != 0 && ((int) (Math.random()*100)) < 80) attackHlt += attackingItem.getHeart();
	        else if (attackingItem.getPit() != 0 && ((int)(Math.random()*100)) < attackingItem.getPit()) attackRD *= 2;
	        else if (attackingItem.getCollar() != 0  && ((int) (Math.random()*100)) < attackingItem.getCollar()) defendRD = 0;
	        //else if (attackingItem.getKnife() != 0 && ((int) (Math.random()*100)) < attackingItem.getKnife()) 
	        //	attackRD = (int) Math.floor(attackDmg * ((150 - Math.min(maxBdef,Math.max(0,(defendDef - j*(attackingItem.getDefDecr() - defendingItem.getDefIncr())) -50))) / 100) + .5);
	        else if (attackingItem.getJewel() != 0 && ((int) (Math.random()*100)) < attackingItem.getJewel()) attackRD += defendRDp;

	// defender
	        attackRD -= (j*defendingItem.getDmgDecrPerTurn());
	        if (attackRD <= 0) if (maxDefendDef == 150) attackRD = 0; else attackRD = 1;
	        defendHlt += Math.min(attackHlt,defendingItem.getCharm()); attackHlt -= defendingItem.getCharm();
	        if (defendingItem.getGolem() != 0) defendRD = calcGolem(defendRD,defendingItem.getGolem(),j);
	        else if (defendingItem.getHeart() != 0 && ((int) (Math.random()*100)) < 80) defendHlt += defendingItem.getHeart();
	        else if (defendingItem.getPit() != 0 && ((int)(Math.random()*100)) < defendingItem.getPit()) defendRD *= 2;
	        else if (defendingItem.getCollar() != 0  && ((int) (Math.random()*100)) < defendingItem.getCollar()) attackRD = 0;
	        //else if (defendingItem.getKnife() != 0 && ((int) (Math.random()*100)) < defendingItem.getKnife()) defendRD = (int) Math.floor(defendDmg * ((150 - Math.min(maxAdef,Math.max(0,(attackDef - j*(defendingItem.getDefDecr() - attackingItem.getDefIncr()))-50))) / 100) + .5);
	        else if (defendingItem.getJewel() != 0 && ((int) (Math.random()*100)) < defendingItem.getJewel()) defendRD += attackRD;

	        attackHlt = Math.max(0, attackHlt - defendRD);
	        defendHlt = Math.max(0, defendHlt - attackRD);

	        defendRDp = defendRD; attackRDp = attackRD;
	        if (turns == 1) {
	        	br.append("Attacking "+attackingCrit.getName()+" did "+attackRD+" damage -> defending "+defendingCrit.getName()+" has "+defendHlt+" health left. \n");
	        	br.append("Defending "+defendingCrit.getName()+" did "+defendRD+" damage -> attacking "+attackingCrit.getName()+" has "+attackHlt+" health left. \n");
	        }
	      }

	      if (attackHlt > 0 && defendHlt == 0) {
	    	  br.append("\nAttacking "+attackingCrit.getName()+" killed the defending "+defendingCrit.getName()+". (attacker wins)\n"); br.incrAttackerWon();
	      } else if (defendHlt > 0 && attackHlt == 0) {
	    	  br.append("\nAttacking "+attackingCrit.getName()+" got killed by the defending "+defendingCrit.getName()+". (defender wins)\n"); br.incrDefenderWon();
	      } else if (attackHlt == 0 && defendHlt == 0) {
	    	  br.append("\nBoth Creatures died (Dip)\n"); br.incrDip();
	      } else {
	        if(attackHlt > defendHlt) {
	        	br.append("\nAttacking "+attackingCrit.getName()+" killed the exhausted defending "+defendingCrit.getName()+". (attacker wins)\n"); br.incrAttackerWon();
	        } else if (defendHlt > attackHlt) {
	        	br.append("\nExhausted attacking "+attackingCrit.getName()+" got killed by the defending "+defendingCrit.getName()+". (defender wins)\n"); br.incrDefenderWon();
	        } else
	        	br.append("\nWell well well, 3 holes in the ground and someone is exhausted\n"); br.incrExhaust();
	      }
	    }
	    
		return br;
	}
	
	  /**
	   * 
	   * @param maxDef
	   * @param def
	   * @return
	   */
	  private static int fixMinMaxDef(int maxDef, double def) {
		  return (int)Math.min(maxDef,Math.max(0,(def)));
	  }
	
	public BattleResult fight(int index, int turns) {
		
		if (index>=attackers.size() || index>=defenders.size()) return null;
		
		Creature attackingCrit = attackers.get(index);
		Creature defendingCrit = defenders.get(index);
		
	    Itherian attackingIth = mergeIth(attackers);
	    Itherian defendingIth = mergeIth(defenders);
		
	    Item attackingItem = attackingCrit.getItem();
	    Item defendingItem = defendingCrit.getItem();
	    
	    int attSame=0, attDiff=0;

	    if (attackingItem.getMassIncr()>0) attSame=getNumberWithRace(attackers,attackingCrit.getRace());
	    if (attackingItem.getMassDecr()>0) attDiff=getNumberNotWithRace(attackers,attackingCrit.getRace());
	    if (attackingItem.getFiendDecr()>0) attSame=(getNumberWithName(attackers,attackingCrit.getName())-1);
	    if (attackingItem.getFiendDecr()>0) attSame=(getNumberWithName(attackers,attackingCrit.getName())-1);

	    if (attackingItem.getBookOfLight()>0) attSame=getNumberWithName(attackers,"Light Hunter");
	    if (attackingItem.getServantGuide()>0) attSame=getNumberWithName(attackers,"Servant Hunter");
	    if (attackingItem.getDirectorManual()>0) attSame=getNumberWithName(attackers,"Hunter Director");
		
	    int defSame=0, defDiff=0;

	    if (defendingItem.getMassIncr()>0) defSame=getNumberWithRace(defenders,defendingCrit.getRace());
	    if (defendingItem.getMassDecr()>0) defDiff=getNumberNotWithRace(defenders,defendingCrit.getRace());
	    if (defendingItem.getFiendDecr()>0) defSame=(getNumberWithName(defenders,defendingCrit.getName())-1);
	    if (defendingItem.getFiendDecr()>0) defSame=(getNumberWithName(defenders,defendingCrit.getName())-1);

	    if (defendingItem.getBookOfLight()>0) defSame=getNumberWithName(defenders,"Light Hunter");
	    if (defendingItem.getServantGuide()>0) defSame=getNumberWithName(defenders,"Servant Hunter");
	    if (defendingItem.getDirectorManual()>0) defSame=getNumberWithName(defenders,"Hunter Director");
	    
		return fight(attackingCrit,attackingIth,attackEnchant,attSame,attDiff,
					 defendingCrit,defendingIth,defendEnchant,defSame,defDiff,
					 turns);
	}
	
	private Itherian mergeIth(List<Creature> creatures) {
		Itherian ith = new Itherian();
		for (Creature crit : creatures) {
			if (crit instanceof Itherian) {
				ith.setIthDamage(ith.getIthDamage()+((Itherian)crit).getIthDamage());
				ith.setIthHealth(ith.getIthHealth()+((Itherian)crit).getIthHealth());
				ith.setIthDefence(ith.getIthDefence()+((Itherian)crit).getIthDefence());
			}
		}
		return ith;
	}
	
	private int getNumberWithRace(List<Creature> creatures, String race) {
		int total=1;
		for (Creature c : creatures) {
			if (c.getRace().equalsIgnoreCase(race)) total++;
		}
		return total;
	}
	
	private int getNumberNotWithRace(List<Creature> creatures, String race) {
		int total=1;
		for (Creature c : creatures) {
			if (!c.getRace().equalsIgnoreCase(race)) total++;
		}
		return total;
	}	
	private int getNumberWithName(List<Creature> creatures, String name) {
		int total=1;
		for (Creature c : creatures) {
			if (c.getName().equalsIgnoreCase(name)) total++;
		}
		return total;
	}
	  /**
	   * calcGolem
	   *
	   * @param rd int
	   * @param gollem int
	   * @param turns int
	   * @return int
	   */
	  private static int calcGolem(int rd, int gollem, int turns) {
	    double procent = (double)gollem/100;
	    double trd = rd;
	    for (int i = 0; i < turns; i++) {
	      trd += trd*procent;
	    }
	    rd = (int) Math.floor(trd+.5);
	    return rd;
	  }
}
