/*
 * Copyright (c) 2005 Coopmans Lennart
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package be.lacerta.cq2.battlecalc;

import be.lacerta.cq2.battlecalc.objects.Creature;
import be.lacerta.cq2.battlecalc.objects.Enchant;
import be.lacerta.cq2.battlecalc.objects.Item;
import be.lacerta.cq2.battlecalc.objects.Itherian;

public class Fight {

  public Creature attackingCrit, defendingCrit;
  public Enchant attackingEnchant, defendingEnchant;

  public double win, lost, dip;

  public String fight;
  
  private boolean shortReport = false;

  /**
   * construct new fight
   *
   * @param crit1 your creature
   * @param ith1 your itherian
   * @param gem1 gem
   * @param crit2 opponent's creature
   * @param ith2 opponent's itherian
   * @param gem2 gem
   */
  public Fight(Creature crit1, Enchant ench1, Creature crit2, Enchant ench2) {
    attackingCrit = crit1;
    attackingEnchant = ench1;
    defendingCrit = crit2;
    defendingEnchant = ench2;
    fight = "";
    shortReport = false;
  }

  /**
   * does the math of the fight
   * @param turns how many turns he has to calculate this (useful for the % calc)
   */
  public void calc(int turns) {

    double attackDmg, attackDef, defendDmg, defendDef;
    int attackHlt, defendHlt;
    int attackRD, defendRD;
    int attackRDp, defendRDp;
    
    Item attackingItem = attackingCrit.getItem();
    Item defendingItem = defendingCrit.getItem();
    Itherian attackingIth = attackingCrit.getIth();
    Itherian defendingIth = defendingCrit.getIth();
    
    for (int i = 0; i < turns; i++) {

      fight = "";
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
      } else {
    	  //System.out.println("no mb! enchant only vs: "+attackingItem.getEnchantVs()+" and race is: "+defendingCrit.getRace());
      }
      

      
      if (attackingEnchant != null && (attackingEnchant.getCreature().equals("") || attackingEnchant.getCreature().equals(attackingCrit.getName()))) {
        attackDmg = attackingEnchant.getDamage(attackDmg);
        attackDef = attackingEnchant.getDefense(attackDef);
      }

      attackDmg += attackingIth.getDamage();
      attackDef += attackingIth.getDefence();
      attackHlt += attackingIth.getHealth();
      attackDmg += attackingItem.getMassIncr();
      attackDmg -= attackingItem.getMassDecr();
      attackDmg -= attackingItem.getFiendDecr();
      attackHlt -= attackingItem.getFiendDecr();
      attackDmg += attackingItem.getDmgIncr();
      attackHlt += attackingItem.getHealthIncr();
      attackDmg += attackDmg*attackingItem.getHunter(); 
      attackDmg += attackDmg*attackingItem.getDmgIncrPerc();
      attackHlt += attackHlt*attackingItem.getHealthIncrPerc();
      attackDef += attackingItem.getDefIncr();
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
      
      if (defendingEnchant != null && (defendingEnchant.getCreature().equals("") || defendingEnchant.getCreature().equals(defendingCrit.getName()))) {
        defendDmg = defendingEnchant.getDamage(defendDmg);
        defendDef = defendingEnchant.getDefense(defendDef);
      }
      defendDmg += defendingIth.getDamage();
      defendDef += defendingIth.getDefence();
      defendHlt += defendingIth.getHealth();
      defendDmg += defendingItem.getMassIncr();
      defendDmg -= defendingItem.getMassDecr();
      defendDmg -= defendingItem.getFiendDecr();
      defendHlt -= defendingItem.getFiendDecr();
      defendDmg += defendingItem.getDmgIncr();
      defendDef += defendingItem.getDefIncr();
      defendHlt += defendingItem.getHealthIncr();
      defendDmg += defendDmg*defendingItem.getHunter();  
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

        int tempDefendDef =  fixMinMaxDef(maxDefendDef, defendDef-j*attackingItem.getDefDecrPerTurn());
        tempDefendDef = fixMinMaxDef(maxDefendDef, tempDefendDef+j*defendingItem.getDefIncrPerTurn());
        
        int tempAttackDef =  fixMinMaxDef(maxAttackDef, attackDef+j*attackingItem.getDefIncrPerTurn());
        tempAttackDef = fixMinMaxDef(maxAttackDef, tempAttackDef+j*defendingItem.getDefDecrPerTurn());
        
		attackRD = (int) Math.floor(attackDmg * ((150 - tempDefendDef) / 100) + .5);
        defendRD = (int) Math.floor(defendDmg * ((150 - tempAttackDef) / 100) + .5);
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
        if (turns == 1 && !shortReport) {
          fight += "Attacking "+attackingCrit.getName()+" did "+attackRD+" damage -> defending "+defendingCrit.getName()+" has "+defendHlt+" health left. \n";
          fight += "Defending "+defendingCrit.getName()+" did "+defendRD+" damage -> attacking "+attackingCrit.getName()+" has "+attackHlt+" health left. \n";
        }
      }

      if (attackHlt > 0 && defendHlt == 0) {
       fight += "\nAttacking "+attackingCrit.getName()+" killed the defending "+defendingCrit.getName()+". (attacker wins)\n"; win++;
      } else if (defendHlt > 0 && attackHlt == 0) {
       fight += "\nAttacking "+attackingCrit.getName()+" got killed by the defending "+defendingCrit.getName()+". (defender wins)\n"; lost++;
      } else if (attackHlt == 0 && defendHlt == 0) {
       fight += "\nBoth Creatures died (Dip)\n"; dip++;
      } else {
        if(attackHlt > defendHlt) {
          fight += "\nAttacking "+attackingCrit.getName()+" killed the exhausted defending "+defendingCrit.getName()+". (attacker wins)\n"; win++;
        } else if (defendHlt > attackHlt) {
          fight += "\nExhausted attacking "+attackingCrit.getName()+" got killed by the defending "+defendingCrit.getName()+". (defender wins)\n"; lost++;
        } else
          fight += "\nWell well well, 3 holes in the ground and someone is exhausted\n";
      }
    }

    win = Math.floor(win / Math.max(1,(turns/100))*100+.5)/100;
    lost = Math.floor(lost / Math.max(1,(turns/100))*100+.5)/100;
    dip = Math.floor(dip / Math.max(1,(turns/100))*100+.5)/100;

  }

  /**
   * 
   * @param maxDef
   * @param def
   * @return
   */
  private int fixMinMaxDef(int maxDef, double def) {
	  return (int)Math.min(maxDef,Math.max(0,(def)));
  }
  
  /**
   * calcGolem
   *
   * @param rd int
   * @param gollem int
   * @param turns int
   * @return int
   */
  private int calcGolem(int rd, int gollem, int turns) {
    double procent = (double)gollem/100;
    double trd = rd;
    for (int i = 0; i < turns; i++) {
      trd += trd*procent;
    }
    rd = (int) Math.floor(trd+.5);
    return rd;
  }

  /** @return a string representation of the fight */
  public String getFight() { return fight; }
  /** @return the times your creature won */
  public double getWin() { return win; }
  /** @return the times your creature lost */
  public double getLost() { return lost; }
  /** @return the times your creature died in process */
  public double getDip() { return dip; }
  /** @return your creature */
  public Creature getMyCrit() { return attackingCrit; }
  /** @return opponent's creature */
  public Creature getOppCrit() { return defendingCrit; }

public boolean isShortReport() {
	return shortReport;
}

public void setShortReport(boolean shortReport) {
	this.shortReport = shortReport;
}

public Enchant getAttackingEnchant() {
	return attackingEnchant;
}

public void setAttackingEnchant(Enchant attackingEnchant) {
	this.attackingEnchant = attackingEnchant;
}

public Enchant getDefendingEnchant() {
	return defendingEnchant;
}

public void setDefendingEnchant(Enchant defendingEnchant) {
	this.defendingEnchant = defendingEnchant;
}

}
