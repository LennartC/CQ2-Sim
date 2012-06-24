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

public class Item {
/*
for line in `echo "select id, name from cq2_critdb_races order by name" | mysql -s coopmansl -u coopmansl -p`
do
	id=`echo $line | awk -F\\\t '{ print $1 }'`; 
	race=`echo $line | awk -F\\\t '{ print $2 }'`; 
	echo "\"-----$race-----\","; 
	for iline in `echo "select name, descr from cq2_critdb_items where raceid = $id order by workshop, brim, ess" | mysql -s coopmansl -u coopmansl --password=<pass>`
	do 
		name=`echo $iline | awk -F\\\t '{ print $1 }'`
		desc=`echo $iline | awk -F\\\t '{ print $2 }'`
		echo "\"$name\", // $desc "
	done
done
*/

public static final String[] ITEMS = {
	"",
	//"-----Air Remnant-----",
	//"Overmind Essence", // Negates all other items
	"-----Angel-----",
	"Angelic Blade", // Increases damage by 50 points.
	"Devil Slasher", // Adds 60% damage during daytime and 15% damage at night.
	"Angelic Claymore", // Adds 100% damage during daytime and 25% damage at night.
	"Devil's Nightmare", // Adds 120% damage during daytime and 30% damage at night.
	"",
	//"-----Apocalypse-----",
	//"-----Balrog-----",
	"-----Banshee-----",
	"Anguish Cry", // Decreases opponent's defenses by 7% every turn.
	"Impaler Cry", // Decreases opponent's defenses by 10% every turn.
	"Styx Cry", // Decreases opponent's defenses by 13% every turn.
	"Destruction Leech", // Decreases opponent's defenses by 8% and adds 4% to creature every turn.
	"Abyss Leech", // Decreases opponent's defenses by 10% and adds 5% to creature every turn.
	"",
	"-----Bat-----",
	"Flexible Wings", // Increases the creature's damage by 10 points.
	"Extended Wings", //  Increases the creature's damage by 32 points.
	"Sharp Wings", //  Increases the creature's damage by 60 points.
	"Honed Wings", //  Increases the creature's damage by 88 points.
	"Mutation Wings", //  Increases the creature's damage by 141 points.
	"Sharp Mutation Wings", //  Increases the creature's damage by 199 points.
	"Honed Mutation Wings", //  Increases the creature's damage by 234 points.
	"Vampire Wings", //  Increases the creature's damage by 375 points.
	"",
	"-----Carnage-----",
	"Fallen Touch", //  Decreases opponent's damage by 32 points every turn.
	"Rotten Touch", //  Decreases opponent's damage by 58 points every turn.
	"Shadow Touch", //  Decreases opponent's damage by 75 points every turn.
	"Tormenter Touch", //  Decreases opponent's damage by 92 points every turn.
	"",
	"-----Cleric-----",
	"Spider Rune", // Adds 45 damage points for every Cleric fighting on your side, removes 3 damage points for every other creature fighting on your side.
	"Viper Rune", //  Adds 60 damage points for every Cleric fighting on your side, removes 6 damage points for every other creature fighting on your side.
	"Raptor Rune", //  Adds 70 damage points for every Cleric fighting on your side, removes 9 damage points for every other creature fighting on your side.
	"Disciple Rune", //  Adds 78 damage points for every Cleric fighting on your side, removes 19 damage points for every other creature fighting on your side.
	"Zealot Rune", //  Adds 86 damage points for every Cleric fighting on your side, removes 24 damage points for every other creature fighting on your side.
	"Mirage Rune", //  Adds 97 damage points for every Cleric fighting on your side, removes 29 damage points for every other creature fighting on your side.
	"Delusion Rune", //  Adds 108 damage points for every Cleric fighting on your side, removes 34 damage points for every other creature fighting on your side.
	//"-----Death Remnant-----",
	"",
	"-----Devil-----",
	"Devil Axe", // Increases the creature's damage by 50 points.
	"Angel Breaker", // Adds 30% health at night and 8% health during daytime.
	"Devil Maul", // Adds 70% health at night and 18% health during daytime.
	"Angel's Horror", // Adds 110% health at night and 28% health during daytime.
	"",
	"-----Diabolic Horde-----",
	"Diabolic Pendant", //  Creature gains 30 damage points for every Diabolic Horde fighting on your side.
	"Diabolic Flag", //  Creature gains 35 damage points for every Diabolic Horde fighting on your side.
	"Diabolic Banner", //  Creature gains 41 damage points for every Diabolic Horde fighting on your side.
	"Diabolic Standard", //  Creature gains 46 damage points for every Diabolic Horde fighting on your side.
	"Diabolic Carrier", //  Creature gains 52 damage points for every Diabolic Horde fighting on your side.
	"Diabolic Flame", //  Creature gains 58 damage points for every Diabolic Horde fighting on your side.
	"Diabolic Inferno", //  Creature gains 64 damage points for every Diabolic Horde fighting on your side.
	"Diabolic Abyss", //  Creature gains 76 damage points for every Diabolic Horde fighting on your side.
	"",
	"-----Doomguard-----",
	"Chaos Armor", // Removes 20% from death defense and adds 20% health points.
	"Infernal Armor", // Removes 30% from death defense and adds 30% health points.
	"Pit Armor", // Removes 55% from death defense and adds 55% health points.
	"",
	"-----Dragon-----",
	"Earth Dragon Scales", //  Removes 160 health points and adds 40% to earth defense.
	"Death Dragon Scales", //  Removes 160 health points and adds 40% to death defense.
	"Air Dragon Scales", //  Removes 80 health points and adds 20% to air defense.
	"Forest Dragon Scales", //  Removes 160 health points and adds 40% to forest defense.
	"Dragon Breath", // Increases the creature's damage by 330 points.
	"Corrosive Dragon Breath", // Increases the creature's damage by 360 points.
	"Earth Dragon Armor", //  Removes 200 health points and adds 50% to earth defense.
	"Death Dragon Armor", //  Removes 200 health points and adds 50% to death defense.
	"Air Dragon Armor", // Removes 160 health points and adds 40% to air defense.
	"Forest Dragon Armor", //  Removes 240 health points and adds 60% to forest defense.
	//"-----Earth Remnant-----",
	"",
	"-----Ent------",
	"Ebony Artifact", // Removes 8 health points and adds 8% to every defense.
	"Ashwood Artifact", // Removes 18 health points and adds 18% to every defense.
	"Hemlock Artifact", // Removes 25 health points and adds 25% to every defense.
	"Elder Artifact", // Removes 30 health points and adds 30% to every defense.
	"Ancient Artifact", // Removes 40 health points and adds 40% to every defense.
	"",
	"-----Fiend-----",
	"Ferocious Infusion", //  Adds 250 damage and health, removes 60 damage and health for every other creature of the exact same type fighting on your side.
	"Berserker Infusion", //  Adds 300 damage and health, removes 75 damage and health for every other creature of the exact same type fighting on your side.
	"Thrasher Infusion", //  Adds 350 damage and health, removes 85 damage and health for every other creature of the exact same type fighting on your side.
	"Marauder Infusion", //  Adds 400 damage and health, removes 100 damage and health for every other creature of the exact same type fighting on your side.
	"Mauler Infusion", //  Adds 500 damage and health, removes 125 damage and health for every other creature of the exact same type fighting on your side.
	"Destroyer Infusion", //  Adds 600 damage and health, removes 150 damage and health for every other creature of the exact same type fighting on your side.
	//"-----Forest Remnant-----",
	"",
	"-----Gargoyle-----",
	"Goretongue Teeth", // Increases the amount of resources stolen by 40% for every Gargoyle fighting on your side.
	"Nightwing Teeth", // Increases the amount of resources stolen by 60% for every Gargoyle fighting on your side.
	"Bloodclaw Teeth", // Increases the amount of resources stolen by 80% for every Gargoyle fighting on your side.
	"",
	"-----Giant-----",
	"Heavy Stone", // Increases the creature's health by 10%.
	"Hot Rock", // Increases the creature's health by 20%.
	"Sizzling Stone", // Increases the creature's health by 30%.
	"Searing Giant Rock", // Increases the creature's health by 35%.
	"Scorching Giant Rock", // Increases the creature's health by 40%.
	"",
	"-----Golem-----",
	"Granite Axe", // Adds 8% damage points every turn.
	"Magma Blade", // Adds 10% damage points every turn.
	"Pit Razor", // Adds 14% damage points every turn.
	"Pit Scythe", // Adds 17% damage points every turn.
	"Fear Mace", // Adds 20% damage points every turn.
	"Nightmare Cleaver", // Adds 25% damage points every turn.
	"",
	"-----Hound-----",
	"Hound Collar", //  Creature has 18% chance of evading an enemy attack and receiving no damage.
	"Enchanted Collar", //  Creature has 24% chance of evading an enemy attack and receiving no damage.
	"Collar of Agility", //  Creature has 28% chance of evading an enemy attack and receiving no damage.
	"Collar of Dexterity", //  Creature has 32% chance of evading an enemy attack and receiving no damage.
	"Collar of Excellence", //  Creature has 36% chance of evading an enemy attack and receiving no damage.
	"",
	"-----Hunter-----",
	"Book of Light", // Creature gains 15% damage points for every Light Hunter fighting on your side.
	"Servant's Guide", // Creature gains 20% damage points for every Servant Hunter fighting on your side.
	"Director's Manual", // Creature gains 25% damage points for every Hunter Director fighting on your side.
	"",
	"-----Imler-----",
	"Bump Jewel", //  Creature has 14% chance of doing the damage of the opponent in addition to its own damage.
	"Crushing Jewel", //  Creature has 20% chance of doing the damage of the opponent in addition to its own damage.
	"Granite Jewel", //  Creature has 28% chance of doing the damage of the opponent in addition to its own damage.
	"Boulder Jewel", //  Creature has 34% chance of doing the damage of the opponent in addition to its own damage.
	"Silver Jewel", //  Creature has 40% chance of doing the damage of the opponent in addition to its own damage.
	"",
	"-----Imling-----",
	"Tiny Charm", //  Drains 50 health from the opponent every turn and gives it to the creature.
	"Small Charm", //  Drains 60 health from the opponent every turn and gives it to the creature.
	"Putrid Charm", //  Drains 70 health from the opponent every turn and gives it to the creature.
	"Rancid Charm", //  Drains 90 health from the opponent every turn and gives it to the creature.
	"Diamond Charm", //  Drains 120 health from the opponent every turn and gives it to the creature.
	"",
	"-----Imp-----",
	"Imp Sword", //  Increases the creature's health and damage by 7 points.
	"Imp Axe", //  Increases the creature's health and damage by 21 points.
	"Imp Mace", //  Increases the creature's health and damage by 40 points.
	"Imp Staff", //  Increases the creature's health and damage by 58 points.
	"Necromancer Wand", //  Increases the creature's health and damage by 93 points.
	"Necrolord Wand", //  Increases the creature's health and damage by 156 points.
	"Distortion Wand", //  Increases the creature's health and damage by 210 points.
	"Corruption Wand", //  Increases the creature's health and damage by 242 points.
	"",
	"-----Lich-----",
	"Sacrifical Knife", // Reduces the opponent's defenses by 10% at the start of the battle.
	"Gore Knife", // Reduces the opponent's defenses by 14% at the start of the battle.
	"Blood Knife", // Reduces the opponent's defenses by 18% at the start of the battle.
	"Shadow Knife", // Reduces the opponent's defenses by 22% at the start of the battle.
	"Hell Knife", // Reduces the opponent's defenses by 26% at the start of the battle.
	"Overseer Blade", // Reduces the opponent's defenses by 30% at the start of the battle.
	//"-----Monk-----",
	//" Annihilation Staff", // Negates all other items.
	"",
	"-----Pit Worm-----",
	"Toothpick", // Increases the creature's damage by 1 points.
	"Pit Worm Toxin", // Creature has 25% chance of doing double damage.
	"Pit Worm Poison", // Creature has 35% chance of doing double damage.
	"Pit Worm Venom", // Creature has 55% chance of doing double damage.
	"Pit Worm Acid", // Creature has 80% chance of doing double damage.
	"",
	"-----Pit Wraith-----",
	"Pit Wraith Book", // Increases the creature's xp by 40%.
	"War Manuscript", // Increases the creature's damage by 30%.
	"Pit Wraith Tome", // Increases the creature's xp by 70%.
	"War Tome", // Increases the creature's damage by 50%.
	"",
	"-----Priest-----",
	"Security Rod", //  Increases the creature's forest, death, air and earth defense by 12%.
	"Shield Rod", //  Increases the creature's forest, death, air and earth defense by 14%.
	"Protection Wand", //  Increases the creature's forest, death, air and earth defense by 16%.
	"Guard Rod", // Increases the creature's forest, death, air and earth defense by 18%.
	"Guardian Wand", // Increases the creature's forest, death, air and earth defense by 21%.
	"Aegis Wand", //  Increases the creature's forest, death, air and earth defense by 24%.
	"Staff of Warden", //  Increases the creature's forest, death, air and earth defense by 27%.
	"Staff of Safeguard", //  Increases the creature's forest, death, air and earth defense by 30%.
	"",
	"-----Rat-----",
	"Weak Rat Poison", //  Increases the creature's health by 20 points.
	"Rat Poison", //  Increases the creature's health by 62 points.
	"Swamp Rat Poison", //  Increases the creature's health by 132 points.
	"Plague Rat Poison", //  Increases the creature's health by 188 points.
	//"-----Reaper-----",
	//"-----Rift Dancer-----",
	//"-----Seraph-----",
	"",
	"-----Smith-----",
	"Crystal Hammer", //  Increases the creature's health by 340 points.
	"Brim Hammer", //  Increases the creature's damage by 220 points.
	"Black Crystal Hammer", //  Increases the creature's health by 750 when fighting earth creatures and by 500 when fighting other creatures.
	"Black Crystal Maul", //  Increases the creature's health by 750 when fighting death creatures and by 500 when fighting other creatures.
	"Dark Brim Hammer", //  Increases the creature's damage by 450 when fighting air creatures and by 300 when fighting other creatures.
	"Dark Brim Maul", //  Increases the creature's damage by 450 when fighting forest creatures and by 300 when fighting other creatures.
	"Depravity Hammer", //  Increases the creature's health by 700 points.
	"Disease Maul", //  Increases the creature's damage by 600 when fighting death creatures and by 400 when fighting other creatures.
	"Disease Hammer", //  Increases the creature's damage by 600 when fighting earth creatures and by 400 when fighting other creatures.
	"Sickness Maul", //  Increases the creature's health by 1200 when fighting forest creatures and by 800 when fighting other creatures.
	"Sickness Hammer", //  Increases the creature's health by 1200 when fighting air creatures and by 800 when fighting other creatures.
	"Death Hammer", //  Increases the creature's damage by 600 points.
	"",
	"-----Spirit-----",
	"Willow Heart", //  Creature has 80% chance of healing 170 health points every turn.
	"Frozen Heart", //  Creature has 80% chance of healing 210 health points every turn.
	"Succubus Heart", //  Creature has 80% chance of healing 280 health points every turn.
	"Ancient Heart", //  Creature has 80% chance of healing 370 health points every turn.
	"",
	"-----Storm-----",
	"Earth Storm Enhancer", //  Removes 20% earth defense and adds 20% to all other defenses.
	"Death Storm Enhancer", //  Removes 25% death defense and adds 25% to all other defenses.
	"Air Storm Enhancer", //  Removes 25% air defense and adds 25% to all other defenses.
	"Earth Storm Augmenter", //  Removes 35% earth defense and adds 35% to all other defenses.
	"Death Storm Augmenter", //  Removes 35% death defense and adds 35% to all other defenses.
	"Air Storm Augmenter", //  Removes 40% air defense and adds 40% to all other defenses.
	//"-----Tempest-----",
	//"-----Tremor-----",
	"",
	"-----Undead-----",
	"Undead Club", //  Increases the creature's damage by 10 points.
	"Rot Club", //  Increases the creature's damage by 30 points.
	"Stench Club", //  Increases the creature's damage by 65 points.
	"Zombie Club", //  Increases the creature's damage by 86 points.
	"Doom Club", //  Increases the creature's damage by 142 points.
	"",
	"-----Wolf-----",
	"Dark Claws", // Increases the creature's damage and health by 8%.
	"Mystic Claws", // Increases the creature's damage and health by 18%.
	"Illusion Claws", // Increases the creature's damage and health by 34%.
	""
  };

 public static final String[] ENCHANTS = {"    ", "ImOf", "MiBe", "MaBe", "MaIm"};

  private String name;
  private int dmgDecrPerTurn = 0, defDecrPerTurn = 0, defIncrPerTurn = 0;
  private int dmgIncr = 0, healthIncr = 0, defIncr = 0;
  private int dmgVsDeathIncr = 0, dmgVsForestIncr = 0, dmgVsAirIncr = 0, dmgVsEarthIncr = 0;
  private int healthVsDeathIncr = 0, healthVsForestIncr = 0, healthVsAirIncr = 0, healthVsEarthIncr = 0;
  private int defVsDeathIncr = 0, defVsForestIncr = 0, defVsAirIncr = 0, defVsEarthIncr = 0;
  private double dmgIncrPerc = 0, healthIncrPerc = 0;
  private int heart = 0, knife = 0, pit = 0, collar = 0, jewel = 0, charm = 0, golem = 0;
  private int massIncr = 0, massDecr = 0, fiendDecr;
  private double hunter = 0;
  private int idx;
  private boolean majorBerserk = false, minorBerserk = false, improvedOffence = false, majorImmunity = false;
  private String enchantVs = "";
  private int sameCrits = 0, diffCrits = 0;


  public Item(String item, String enchant) {
    this(item,enchant,0,0,true);
  }
  
  public Item(String item, String enchant, int sameCrits, int diffCrits) {
	    this(item, enchant, sameCrits, diffCrits, true);
  }
  /**
   * construct item
   *
   * @param item String representation
   * @param enchant <code>true</code> if major berserk
   */
  public Item(String item, String enchant, int sameCrits, int diffCrits, boolean day) {
    name = item;

    		if (item.equals("Angelic Blade")) {
    			dmgIncr = 50;
	    } else if (item.equals("Devil Slasher")) {
	    	if (day) dmgIncrPerc = 0.60; else dmgIncrPerc = 0.15;
	    } else if (item.equals("Angelic Claymore")) {
	    	if (day) dmgIncrPerc = 1.00; else dmgIncrPerc = 0.25;
	    } else if (item.equals("Devil's Nightmare")) {
	    	if (day) dmgIncrPerc = 1.20; else dmgIncrPerc = 0.30;
	    } else if (item.equals("Anguish Cry")) {
	    	defDecrPerTurn = 7;
	    } else if (item.equals("Impaler Cry")) {
	    	defDecrPerTurn = 10;
	    } else if (item.equals("Styx Cry")) {
	    	defDecrPerTurn = 13;
	    } else if (item.equals("Destruction Leech")) {
	    	defDecrPerTurn = 8;  defIncrPerTurn = 4;
	    } else if (item.equals("Abyss Leech")) {
	    	defDecrPerTurn = 10; defIncrPerTurn = 5;
	    } else if (item.equals("Flexible Wings")) {
	    	dmgIncr = 10;
	    } else if (item.equals("Extended Wings")) {
	    	dmgIncr = 32;
	    } else if (item.equals("Sharp Wings")) {
	    	dmgIncr = 60;
	    } else if (item.equals("Honed Wings")) {
	    	dmgIncr = 88;
	    } else if (item.equals("Mutation Wings")) {
	    	dmgIncr = 141;
	    } else if (item.equals("Sharp Mutation Wings")) {
	    	dmgIncr = 199;
	    } else if (item.equals("Honed Mutation Wings")) {
	    	dmgIncr = 234;
	    } else if (item.equals("Vampire Wings")) {
	    	dmgIncr = 375;
	    } else if (item.equals("Fallen Touch")) {
	    	dmgDecrPerTurn = 32;
	    } else if (item.equals("Rotten Touch")) {
	    	dmgDecrPerTurn = 58;
	    } else if (item.equals("Shadow Touch")) {
	    	dmgDecrPerTurn = 75;
	    } else if (item.equals("Tormenter Touch")) {
	    	dmgDecrPerTurn = 92;
	    } else if (item.equals("Spider Rune")) {
	    	massIncr = 45; massDecr = 3;
	    } else if (item.equals("Viper Rune")) {
	    	massIncr = 60; massDecr = 6;
	    } else if (item.equals("Raptor Rune")) {
	    	massIncr = 70; massDecr = 9;
	    } else if (item.equals("Disciple Rune")) {
	    	massIncr = 78; massDecr = 19;
	    } else if (item.equals("Zealot Rune")) {
	    	massIncr = 86; massDecr = 24;
	    } else if (item.equals("Mirage Rune")) {
	    	massIncr = 97; massDecr = 29;
	    } else if (item.equals("Delusion Rune")) {
	    	massIncr = 108; massDecr = 34;
	    } else if (item.equals("Devil Axe")) {
	    	dmgIncr = 50;
	    } else if (item.equals("Angel Breaker")) {
	    	if (day) dmgIncrPerc = 0.08; else dmgIncrPerc = 0.30;
	    } else if (item.equals("Devil Maul")) {
	    	if (day) dmgIncrPerc = 0.18; else dmgIncrPerc = 0.70;
	    } else if (item.equals("Angel's Horror")) {
	    	if (day) dmgIncrPerc = 0.28; else dmgIncrPerc = 1.10;
	    } else if (item.equals("Diabolic Pendant")) {
	    	massIncr = 30;
	    } else if (item.equals("Diabolic Flag")) {
	    	massIncr = 35;
	    } else if (item.equals("Diabolic Banner")) {
	    	massIncr = 41;
	    } else if (item.equals("Diabolic Standard")) {
	    	massIncr = 46;
	    } else if (item.equals("Diabolic Carrier")) {
	    	massIncr = 52;
	    } else if (item.equals("Diabolic Flame")) {
	    	massIncr = 58;
	    } else if (item.equals("Diabolic Inferno")) {
	    	massIncr = 64;
	    } else if (item.equals("Diabolic Abyss")) {
	    	massIncr = 76;
	    } else if (item.equals("Chaos Armor")) {
	    	healthIncrPerc = 0.20;
	    } else if (item.equals("Infernal Armor")) {
	    	healthIncrPerc = 0.30;
	    } else if (item.equals("Pit Armor")) {
	    	healthIncrPerc = 0.55;
	    } else if (item.equals("Earth Dragon Scales")) {
	    	healthIncr = -160; defVsEarthIncr = 40;
	    } else if (item.equals("Death Dragon Scales")) {
	    	healthIncr = -160; defVsDeathIncr = 40;
	    } else if (item.equals("Air Dragon Scales")) {
	    	healthIncr = -80; defVsAirIncr = 20;
	    } else if (item.equals("Forest Dragon Scales")) {
	    	healthIncr = -160; defVsForestIncr = 40;
	    } else if (item.equals("Dragon Breath")) {
	    	dmgIncr = 330;
	    } else if (item.equals("Corrosive Dragon Breath")) {
	    	dmgIncr = 360;
	    } else if (item.equals("Earth Dragon Armor")) {
	    	healthIncr = -200; defVsEarthIncr = 50;
	    } else if (item.equals("Death Dragon Armor")) {
	    	healthIncr = -200; defVsDeathIncr = 50;
	    } else if (item.equals("Air Dragon Armor")) {
	    	healthIncr = -160; defVsAirIncr = 40;
	    } else if (item.equals("Forest Dragon Armor")) {
	    	healthIncr = -240; defVsForestIncr = 50;
	    } else if (item.equals("Ebony Artifact")) {
	    	healthIncr = -8;  defIncr = 8;
	    } else if (item.equals("Ashwood Artifact")) {
	    	healthIncr = -18; defIncr = 18;
	    } else if (item.equals("Hemlock Artifact")) {
	    	healthIncr = -25; defIncr = 25;
	    } else if (item.equals("Elder Artifact")) {
	    	healthIncr = -30; defIncr = 30;
	    } else if (item.equals("Ancient Artifact")) {
	    	healthIncr = -40; defIncr = 40;
	    } else if (item.equals("Ferocious Infusion")) {
	    	dmgIncr = 250; healthIncr = 250; fiendDecr = 60;
	    } else if (item.equals("Berserker Infusion")) {
	    	dmgIncr = 300; healthIncr = 300; fiendDecr = 75;
	    } else if (item.equals("Thrasher Infusion")) {
	    	dmgIncr = 350; healthIncr = 350; fiendDecr = 85;
	    } else if (item.equals("Marauder Infusion")) {
	    	dmgIncr = 400; healthIncr = 400; fiendDecr = 100;
	    } else if (item.equals("Mauler Infusion")) {
	    	dmgIncr = 500; healthIncr = 500; fiendDecr = 125;
	    } else if (item.equals("Destroyer Infusion")) {
	    	dmgIncr = 600; healthIncr = 600; fiendDecr = 150;
	    } else if (item.equals("Goretongue Teeth")) {
	    } else if (item.equals("Nightwing Teeth")) {
	    } else if (item.equals("Bloodclaw Teeth")) {
	    } else if (item.equals("Heavy Stone")) {
	    	healthIncrPerc = 0.10;
	    } else if (item.equals("Hot Rock")) {
	    	healthIncrPerc = 0.20;
	    } else if (item.equals("Sizzling Stone")) {
	    	healthIncrPerc = 0.30;
	    } else if (item.equals("Searing Giant Rock")) {
	    	healthIncrPerc = 0.35;
	    } else if (item.equals("Scorching Giant Rock")) {
	    	healthIncrPerc = 0.40;
	    } else if (item.equals("Granite Axe")) {
	    	golem = 8;
	    } else if (item.equals("Magma Blade")) {
	    	golem = 10;
	    } else if (item.equals("Pit Razor")) {
	    	golem = 14;
	    } else if (item.equals("Pit Scythe")) {
	    	golem = 17;
	    } else if (item.equals("Fear Mace")) {
	    	golem = 20;
	    } else if (item.equals("Nightmare Cleaver")) {
	    	golem = 25;
	    } else if (item.equals("Hound Collar")) {
	    	collar = 18;
	    } else if (item.equals("Enchanted Collar")) {
	    	collar = 24;
	    } else if (item.equals("Collar of Agility")) {
	    	collar = 28;
	    } else if (item.equals("Collar of Dexterity")) {
	    	collar = 32;
	    } else if (item.equals("Collar of Excellence")) {
	    	collar = 36;
	    } else if (item.equals("Book of Light")) {
	    	hunter = 0.15;
	    } else if (item.equals("Servant's Guide")) {
	    	hunter = 0.20;
	    } else if (item.equals("Director's Manual")) {
	    	hunter = 0.25;
	    } else if (item.equals("Bump Jewel")) {
	    	jewel = 14;
	    } else if (item.equals("Crushing Jewel")) {
	    	jewel = 20;
	    } else if (item.equals("Granite Jewel")) {
	    	jewel = 28;
	    } else if (item.equals("Boulder Jewel")) {
	    	jewel = 34;
	    } else if (item.equals("Silver Jewel")) {
	    	jewel = 40;
	    } else if (item.equals("Tiny Charm")) {
	    	charm = 50;
	    } else if (item.equals("Small Charm")) {
	    	charm = 60;
	    } else if (item.equals("Putrid Charm")) {
	    	charm = 70;
	    } else if (item.equals("Rancid Charm")) {
	    	charm = 90;
	    } else if (item.equals("Diamond Charm")) {
	    	charm = 120;
	    } else if (item.equals("Imp Sword")) {
	    	dmgIncr = 7;   healthIncr = 7;
	    } else if (item.equals("Imp Axe")) {
	    	dmgIncr = 21;  healthIncr = 21;
	    } else if (item.equals("Imp Mace")) {
	    	dmgIncr = 40;  healthIncr = 40;
	    } else if (item.equals("Imp Staff")) {
	    	dmgIncr = 58;  healthIncr = 58;
	    } else if (item.equals("Necromancer Wand")) {
	    	dmgIncr = 93;  healthIncr = 93;
	    } else if (item.equals("Necrolord Wand")) {
	    	dmgIncr = 156; healthIncr = 156;
	    } else if (item.equals("Distortion Wand")) {
	    	dmgIncr = 210; healthIncr = 210;
	    } else if (item.equals("Corruption Wand")) {
	    	dmgIncr = 242; healthIncr = 242;
	    } else if (item.equals("Sacrifical Knife")) {
	    	knife =10;
	    } else if (item.equals("Gore Knife")) {
	    	knife =14;
	    } else if (item.equals("Blood Knife")) {
	    	knife =18;
	    } else if (item.equals("Shadow Knife")) {
	    	knife =22;
	    } else if (item.equals("Hell Knife")) {
	    	knife =26;
	    } else if (item.equals("Overseer Blade")) {
	    	knife =30;
	    } else if (item.equals("Toothpick")) {
	    	dmgIncr = 1;
	    } else if (item.equals("Pit Worm Toxin")) {
	    	pit = 25;
	    } else if (item.equals("Pit Worm Poison")) {
	    	pit = 35;
	    } else if (item.equals("Pit Worm Venom")) {
	    	pit = 55;
	    } else if (item.equals("Pit Worm Acid")) {
	    	pit = 80;
	    } else if (item.equals("Pit Wraith Book")) {
	    } else if (item.equals("War Manuscript")) {
	    	dmgIncrPerc = 0.30;
	    } else if (item.equals("Pit Wraith Tome")) {
	    } else if (item.equals("War Tome")) {
	    	dmgIncrPerc = 0.50;
	    } else if (item.equals("Security Rod")) {
	    	defIncr = 12;
	    } else if (item.equals("Shield Rod")) {
	    	defIncr = 14;
	    } else if (item.equals("Protection Wand")) {
	    	defIncr = 16;
	    } else if (item.equals("Guard Rod")) {
	    	defIncr = 18;
	    } else if (item.equals("Guardian Wand")) {
	    	defIncr = 21;
	    } else if (item.equals("Aegis Wand")) {
	    	defIncr = 24;
	    } else if (item.equals("Staff of Warden")) {
	    	defIncr = 27;
	    } else if (item.equals("Staff of Safeguard")) {
	    	defIncr = 30;
	    } else if (item.equals("Weak Rat Poison")) {
	    	healthIncr = 20;
	    } else if (item.equals("Rat Poison")) {
	    	healthIncr = 62;
	    } else if (item.equals("Swamp Rat Poison")) {
	    	healthIncr = 132;
	    } else if (item.equals("Plague Rat Poison")) {
	    	healthIncr = 188;
	    } else if (item.equals("Crystal Hammer")) {
	    	healthIncr = 340;
	    } else if (item.equals("Brim Hammer")) {
	    	dmgIncr = 220;
	    } else if (item.equals("Black Crystal Hammer")) {
	    	healthVsDeathIncr = 500; healthVsForestIncr = 500; healthVsAirIncr = 500; healthVsEarthIncr = 750;
	    } else if (item.equals("Black Crystal Maul")) {
	    	healthVsDeathIncr = 750; healthVsForestIncr = 500; healthVsAirIncr = 500; healthVsEarthIncr = 500;
	    } else if (item.equals("Dark Brim Hammer")) {
	    	dmgVsDeathIncr = 300; dmgVsForestIncr = 300; dmgVsAirIncr = 450; dmgVsEarthIncr = 300;
	    } else if (item.equals("Dark Brim Maul")) {
	    	dmgVsDeathIncr = 300; dmgVsForestIncr = 450; dmgVsAirIncr = 300; dmgVsEarthIncr = 300;
	    } else if (item.equals("Depravity Hammer")) {
	    	healthIncr = 700;
	    } else if (item.equals("Disease Maul")) {
	    	dmgVsDeathIncr = 600; dmgVsForestIncr = 400; dmgVsAirIncr = 400; dmgVsEarthIncr = 400;
	    } else if (item.equals("Disease Hammer")) {
	    	dmgVsDeathIncr = 400; dmgVsForestIncr = 400; dmgVsAirIncr = 400; dmgVsEarthIncr = 600;
	    } else if (item.equals("Sickness Maul")) {
	    	healthVsDeathIncr = 800; healthVsForestIncr = 1200; healthVsAirIncr = 800; healthVsEarthIncr = 800;
	    } else if (item.equals("Sickness Hammer")) {
	    	healthVsDeathIncr = 800; healthVsForestIncr = 800; healthVsAirIncr = 1200; healthVsEarthIncr = 800;
	    } else if (item.equals("Death Hammer")) {
	    	dmgIncr = 600;
	    } else if (item.equals("Willow Heart")) {
	    	heart = 170;
	    } else if (item.equals("Frozen Heart")) {
	    	heart = 210;
	    } else if (item.equals("Succubus Heart")) {
	    	heart = 280;
	    } else if (item.equals("Ancient Heart")) {
	    	heart = 370;
	    } else if (item.equals("Earth Storm Enhancer")) {
	    	 defVsAirIncr = 20; defVsDeathIncr = 20; defVsForestIncr = 20; defVsEarthIncr = -20;
	    } else if (item.equals("Death Storm Enhancer")) {
	    	 defVsAirIncr = 25; defVsDeathIncr = -25; defVsForestIncr = 25; defVsEarthIncr = 25;
	    } else if (item.equals("Air Storm Enhancer")) {
	    	 defVsAirIncr = -25; defVsDeathIncr = 25; defVsForestIncr = 25; defVsEarthIncr = 25;
	    } else if (item.equals("Earth Storm Augmenter")) {
	    	 defVsAirIncr = 35; defVsDeathIncr = 35; defVsForestIncr = 35; defVsEarthIncr = -35;
	    } else if (item.equals("Death Storm Augmenter")) {
	    	 defVsAirIncr = 35; defVsDeathIncr = -35; defVsForestIncr = 35; defVsEarthIncr = 35;
	    } else if (item.equals("Air Storm Augmenter")) {
	    	 defVsAirIncr = -40; defVsDeathIncr = 40; defVsForestIncr = 40; defVsEarthIncr = 40;
	    } else if (item.equals("Undead Club")) {
	    	dmgIncr = 10;
	    } else if (item.equals("Rot Club")) {
	    	dmgIncr = 30;
	    } else if (item.equals("Stench Club")) {
	    	dmgIncr = 65;
	    } else if (item.equals("Zombie Club")) {
	    	dmgIncr = 86;
	    } else if (item.equals("Doom Club")) {
	    	dmgIncr = 142;
	    } else if (item.equals("Dark Claws")) {
	    	dmgIncrPerc = 0.08; healthIncrPerc = 0.08;
	    } else if (item.equals("Mystic Claws")) {
	    	dmgIncrPerc = 0.18; healthIncrPerc = 0.18;
	    } else if (item.equals("Illusion Claws")) {
	    	dmgIncrPerc = 0.34; healthIncrPerc = 0.34;
	    }

    setEnchant(enchant);
    
    this.sameCrits = sameCrits;
    this.diffCrits = diffCrits;
  }
  
  public void setEnchant(String enchant) {
		setEnchant(enchant,"");
  }
  
  public void setEnchant(String enchant, String versus) {
	  this.enchantVs = versus.trim();
	  if (enchant.equals("ImOf"))
		  improvedOffence = true;
	  else if (enchant.equals("MiBe"))
		  minorBerserk = true;
	  else if (enchant.equals("MaBe"))
		  majorBerserk = true;
	  else if (enchant.equals("MaIm"))
		  majorImmunity = true;
  }
  
  public String getName() { return name; }
  /** @return the damage decrease done each turn */
  public int getDmgDecrPerTurn() { return dmgDecrPerTurn; }
  /** @return the defence decrease done each turn */
  public int getDefDecrPerTurn() { return defDecrPerTurn; }
  /** @return the defence increase done each turn */
  public int getDefIncrPerTurn() { return defIncrPerTurn; }
  /** @return the health recovered by the heart each turn */
  public int getHeart() { return heart; }
  /** @return the chance the knife will work */
  public int getKnife() { return knife; }
  /** @return the chance the pit will work */
  public int getPit() { return pit; }
  /** @return the chance the collar will work */
  public int getCollar() { return collar; }
  /** @return the chance the jewel will work */
  public int getJewel() { return jewel; }
  /** @return the health removed/added by the charm */
  public int getCharm() { return charm; }
  /** @return the damage added by the gollem item every turn */
  public int getGolem() { return golem; }
  /** @return the damage added by mass items */
  public int getMassIncr() { return (massIncr*sameCrits); }
  /** @return the damage percentage added by hunters */
  public double getHunter() { return (hunter*sameCrits); }
  /** @return the damage removed by mass items (runes) */
  public int getMassDecr() { return (massDecr*diffCrits); }
  /** @return <code>true</code> if improved offence */
  public boolean isImOf() { return improvedOffence; }
  /** @return <code>true</code> if minor berserk */
  public boolean isMiBe() { return minorBerserk; }
  /** @return <code>true</code> if major berserk */
  public boolean isMaBe() { return majorBerserk; }
  /** @return <code>true</code> if major berserk */
  public boolean getMajorBerserk() { return majorBerserk; }
  /** @return <code>true</code> if major berserk */
  public boolean isMajorBerserk() { return majorBerserk; }
  /** @return the place of the item in the item array */
  public int getIndex() { return idx; }
  
  public boolean needNumberOfSame() { return (massIncr > 0 || hunter>0 || fiendDecr>0); }
  public boolean needNumberOfDiff() { return (massDecr > 0); }
public int getDmgIncr() {
	return dmgIncr;
}
public int getHealthIncr() {
	return healthIncr;
}
public double getDmgIncrPerc() {
	return dmgIncrPerc;
}
public double getHealthIncrPerc() {
	return healthIncrPerc;
}

public int getDmgVsDeathIncr() {
	return dmgVsDeathIncr;
}

public int getDmgVsForestIncr() {
	return dmgVsForestIncr;
}

public int getDmgVsAirIncr() {
	return dmgVsAirIncr;
}

public int getDmgVsEarthIncr() {
	return dmgVsEarthIncr;
}

public int getHealthVsDeathIncr() {
	return healthVsDeathIncr;
}

public int getHealthVsForestIncr() {
	return healthVsForestIncr;
}

public int getHealthVsAirIncr() {
	return healthVsAirIncr;
}

public int getHealthVsEarthIncr() {
	return healthVsEarthIncr;
}

public int getSameCrits() {
	return sameCrits;
}

public void setSameCrits(int sameCrits) {
	this.sameCrits = sameCrits;
}

public int getDiffCrits() {
	return diffCrits;
}

public void setDiffCrits(int diffCrits) {
	this.diffCrits = diffCrits;
}

public String getEnchantVs() {
	return enchantVs;
}

public void setEnchantVs(String enchantVs) {
	this.enchantVs = enchantVs;
}


/** @return <code>true</code> if major immunity */
public boolean getMajorImmunity() { return majorImmunity; }

public boolean isMajorImmunity() {
	return majorImmunity;
}

public void setMajorImmunity(boolean majorImmunity) {
	this.majorImmunity = majorImmunity;
}

public int getDefVsDeathIncr() {
	return defVsDeathIncr;
}

public void setDefVsDeathIncr(int defVsDeathIncr) {
	this.defVsDeathIncr = defVsDeathIncr;
}

public int getDefVsForestIncr() {
	return defVsForestIncr;
}

public void setDefVsForestIncr(int defVsForestIncr) {
	this.defVsForestIncr = defVsForestIncr;
}

public int getDefVsAirIncr() {
	return defVsAirIncr;
}

public void setDefVsAirIncr(int defVsAirIncr) {
	this.defVsAirIncr = defVsAirIncr;
}

public int getDefVsEarthIncr() {
	return defVsEarthIncr;
}

public void setDefVsEarthIncr(int defVsEarthIncr) {
	this.defVsEarthIncr = defVsEarthIncr;
}

public int getDefIncr() {
	return defIncr;
}

public void setDefIncr(int defIncr) {
	this.defIncr = defIncr;
}

public int getFiendDecr() {
	return (fiendDecr*(sameCrits-1));
}

public void setFiendDecr(int fiendDecr) {
	this.fiendDecr = fiendDecr;
}
}
