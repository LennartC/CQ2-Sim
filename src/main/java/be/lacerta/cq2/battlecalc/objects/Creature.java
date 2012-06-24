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


package be.lacerta.cq2.battlecalc.objects;

import java.io.Serializable;

import be.lacerta.cq2.battlecalc.util.*;

public class Creature implements Serializable {

  private static final long serialVersionUID = 565227904629850610L;
  public static String SUFFOCATION = "Suffocation";
  public static String METAMORPHOSIS = "Metamorphosis";
  public static String DOPPELGANGER = "Doppelganger";
  public static String JINX = "Jinx";

  private String name, creatureClass = "", race = "";
  private int level;
  private int damage = 0;
  private int forestDef = 0, deathDef = 0, airDef = 0, earthDef = 0;
  private int health = 0, skill = 0;
  private Item theItem;
  private Itherian ith;
  private boolean metad = false, suffocated = false, doppeled = false, jinxed = false;

  public Creature() {

  }

  @Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + airDef;
	result = prime * result
			+ ((creatureClass == null) ? 0 : creatureClass.hashCode());
	result = prime * result + damage;
	result = prime * result + deathDef;
	result = prime * result + (doppeled ? 1231 : 1237);
	result = prime * result + earthDef;
	result = prime * result + forestDef;
	result = prime * result + health;
	result = prime * result + ((ith == null) ? 0 : ith.hashCode());
	result = prime * result + level;
	result = prime * result + (metad ? 1231 : 1237);
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((race == null) ? 0 : race.hashCode());
	result = prime * result + skill;
	result = prime * result + (suffocated ? 1231 : 1237);
	result = prime * result + (jinxed ? 1231 : 1237);
	result = prime * result + ((theItem == null) ? 0 : theItem.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	final Creature other = (Creature) obj;
	if (airDef != other.airDef)
		return false;
	if (creatureClass == null) {
		if (other.creatureClass != null)
			return false;
	} else if (!creatureClass.equals(other.creatureClass))
		return false;
	if (damage != other.damage)
		return false;
	if (deathDef != other.deathDef)
		return false;
	if (doppeled != other.doppeled)
		return false;
	if (earthDef != other.earthDef)
		return false;
	if (forestDef != other.forestDef)
		return false;
	if (health != other.health)
		return false;
	if (ith == null) {
		if (other.ith != null)
			return false;
	} else if (!ith.equals(other.ith))
		return false;
	if (level != other.level)
		return false;
	if (metad != other.metad)
		return false;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (race == null) {
		if (other.race != null)
			return false;
	} else if (!race.equals(other.race))
		return false;
	if (skill != other.skill)
		return false;
	if (suffocated != other.suffocated)
		return false;
	if (jinxed != other.jinxed)
		return false;
	if (theItem == null) {
		if (other.theItem != null)
			return false;
	} else if (!theItem.equals(other.theItem))
		return false;
	return true;
}

public Creature(String name, String crtClass, String race,
                  int damage, int health, int skill,
                  int forestDef, int deathDef, int airDef, int earthDef,
                  Item item, String curse) {
    this.name = name;
    this.creatureClass = crtClass.toLowerCase();
    this.race = race.toLowerCase();
    this.damage = damage;
    this.health = health;
    this.skill = skill;
    this.forestDef = forestDef;
    this.deathDef = deathDef;
    this.airDef = airDef;
    this.earthDef = earthDef;
    this.theItem = item;
    if (curse.equals("Suffocation")) this.suffocated = true;
    if (curse.equals("Jinx")) this.jinxed = true;
    else if (curse.equals("Metamorphosis")) this.metad = true;
    else if (curse.equals("Doppelganger")) this.doppeled = true;
  }

  public Creature(int dmg, int hlt, int def, Item item, String curse) {
      this("","","",dmg,hlt,0,def,def,def,def,item,curse);
  }

  public void setName(String name) {
    this.name = name;
  }
  public String getName() {
    return StringUtils.capitalize(name);
  }

  public void setRace(String race) {
      this.race = race;
  }
  public String getRace() {
      return race;
  }
  public void setCreatureClass(String crtClass) {
      this.creatureClass = crtClass;
  }
  public String getCreatureClass() {
      return creatureClass;
  }
  /** @return the damage of the creature */
  public double getDamage() { return damage; }
  /** @return the health of the creature */
  public int getHealth() { return health; }
  /** @return the health of the creature */
  public int getSkill() { return skill; }
  /** @return the defence of the creature */
  public int getForestDef() { return forestDef; }
  /** @return the defence of the creature */
  public int getDeathDef() { return deathDef; }
  /** @return the defence of the creature */
  public int getAirDef() { return airDef; }
  /** @return the defence of the creature */
  public int getEarthDef() { return earthDef; }
  /** @return the defence of the creature */
  public int getDef(String crtClass) {
	  //System.out.println("finding def for "+crtClass+" and def is : F"+getForestDef()+"/D"+getDeathDef()+"/A"+getAirDef()+"/E"+getEarthDef());
      if (crtClass.toLowerCase().equalsIgnoreCase("forest")) {
          return getForestDef();
      } else if (crtClass.toLowerCase().equalsIgnoreCase("death")) {
          return getDeathDef();
      } else if (crtClass.toLowerCase().equalsIgnoreCase("air")) {
          return getAirDef();
      } else if (crtClass.toLowerCase().equalsIgnoreCase("earth")) {
          return getEarthDef();
      }
      return getForestDef();
  }
  public void setItem(Item item) {
	  this.theItem = item;
  }
  /** @return the item of the creature */
  public Item getItem() { return theItem; }
  /** @return <code>true</code> if the creature is suffocated */
  public boolean isSuffocated() { return suffocated; }
  /** @return <code>true</code> if the creature is suffocated */
  public boolean isJinxed() { return jinxed; }
  /** @return <code>true</code> if the creature is cursed with meta */
  public boolean isMetad() { return metad; }
  /** @return <code>true</code> if the creature is cursed with doppel */
  public boolean isDoppeled() { return doppeled; }

  public Creature clone() {
      String curse = "";
      if (isSuffocated()) curse = Creature.SUFFOCATION;
      if (isJinxed()) curse = Creature.JINX;
      else if (isMetad()) curse = Creature.METAMORPHOSIS;
      else if (isDoppeled()) curse = Creature.DOPPELGANGER;
      Creature clone = new Creature(name, creatureClass, race,
                  damage,health, skill, forestDef, deathDef, airDef, earthDef,
                  theItem, curse);
      return clone;
  }

  
    public void setDamage(String s, int level, int desiredLevel) {
    	setDamage(Integer.parseInt(s), level, desiredLevel);
    }
	
    public void setHealth(String s, int level, int desiredLevel) {
    	setHealth(Integer.parseInt(s), level, desiredLevel);
    }
    
    public void setDamage(int damage, int level, int desiredLevel) {
    	if (level == 0) level = this.level;
    	this.damage = Creature.convertStrength(damage, level, desiredLevel);
        this.level = desiredLevel;
    }
	
    public void setHealth(int health, int level, int desiredLevel) {
    	if (level == 0) level = this.level;
    	this.health = Creature.convertStrength(health, level, desiredLevel);
    	this.level = desiredLevel;
    }
    
    public void setSkill(String s) {
        this.skill = Integer.parseInt(s);
    }
    public void setForestDef(String s) {
        this.forestDef = Integer.parseInt(s);
    }
    public void setDeathDef(String s) {
        this.deathDef = Integer.parseInt(s);
    }
    public void setAirDef(String s) {
        this.airDef = Integer.parseInt(s);
    }
    public void setEarthDef(String s) {
        this.earthDef = Integer.parseInt(s);
    }
    
	public static int convertStrength(int strength, int level, int desiredLevel) {
		return Integer.parseInt(""+Math.round((strength*(0.8 + 0.2*desiredLevel))/(0.8+0.2*level)));
	}

	public Itherian getIth() {
		return ith;
	}

	public void setIth(Itherian ith) {
		this.ith = ith;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setMetad(boolean meta) {
		this.metad = meta;
	}

	public void setSuffocated(boolean suffo) {
		this.suffocated = suffo;
	}
	public void setJinxed(boolean jinx) {
		this.jinxed = jinx;
	}

	public void setDoppeled(boolean doppel) {
		this.doppeled = doppel;
	}

}
