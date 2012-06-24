package be.lacerta.cq2.objects;

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
	  private int dmgIncr = 0, healthIncr = 0;
	  private int dmgVsDeathIncr = 0, dmgVsForestIncr = 0, dmgVsAirIncr = 0, dmgVsEarthIncr = 0;
	  private int healthVsDeathIncr = 0, healthVsForestIncr = 0, healthVsAirIncr = 0, healthVsEarthIncr = 0;
	  private int defVsDeathIncr = 0, defVsForestIncr = 0, defVsAirIncr = 0, defVsEarthIncr = 0;
	  private double dmgIncrPerc = 0, healthIncrPerc = 0;
	  private int heart = 0, knife = 0, pit = 0, collar = 0, jewel = 0, charm = 0, golem = 0;
	  private int massIncr = 0, massDecr = 0, fiendDecr;
	  private double bookOfLight = 0, servantGuide = 0, directorManual;
	  private int idx;
	  private boolean majorBerserk = false, minorBerserk = false, improvedOffence = false, majorImmunity = false;
	  private String enchantVs = "";
	  private boolean unknownEnchant = false;

	  public boolean isUnknownEnchant() {
		return unknownEnchant;
	}

	public void setUnknownEnchant(boolean unknownEnchant) {
		this.unknownEnchant = unknownEnchant;
	}

	public Item(String item, String enchant) {
	    this(item,enchant,true);
	  }
	  
	public Item() {
		
	}
	  /**
	   * construct item
	   *
	   * @param name String representation
	   * @param enchant <code>true</code> if major berserk
	   */
	  public Item(String name, String enchant, boolean day) {
	    setName(name,day);
	    setEnchant(enchant);
	  }
	  
	  public void setName(String name, boolean day) {
		  this.name = name;
		  		if (name.equals("Angelic Blade")) {
					dmgIncr = 50;
		    } else if (name.equals("Devil Slasher")) {
		    	if (day) dmgIncrPerc = 0.60; else dmgIncrPerc = 0.15;
		    } else if (name.equals("Angelic Claymore")) {
		    	if (day) dmgIncrPerc = 1.00; else dmgIncrPerc = 0.25;
		    } else if (name.equals("Devil's Nightmare")) {
		    	if (day) dmgIncrPerc = 1.20; else dmgIncrPerc = 0.30;
		    } else if (name.equals("Anguish Cry")) {
		    	defDecrPerTurn = 7;
		    } else if (name.equals("Impaler Cry")) {
		    	defDecrPerTurn = 10;
		    } else if (name.equals("Styx Cry")) {
		    	defDecrPerTurn = 13;
		    } else if (name.equals("Destruction Leech")) {
		    	defDecrPerTurn = 8;  defIncrPerTurn = 4;
		    } else if (name.equals("Abyss Leech")) {
		    	defDecrPerTurn = 10; defIncrPerTurn = 5;
		    } else if (name.equals("Flexible Wings")) {
		    	dmgIncr = 10;
		    } else if (name.equals("Extended Wings")) {
		    	dmgIncr = 32;
		    } else if (name.equals("Sharp Wings")) {
		    	dmgIncr = 60;
		    } else if (name.equals("Honed Wings")) {
		    	dmgIncr = 88;
		    } else if (name.equals("Mutation Wings")) {
		    	dmgIncr = 141;
		    } else if (name.equals("Sharp Mutation Wings")) {
		    	dmgIncr = 199;
		    } else if (name.equals("Honed Mutation Wings")) {
		    	dmgIncr = 234;
		    } else if (name.equals("Vampire Wings")) {
		    	dmgIncr = 375;
		    } else if (name.equals("Fallen Touch")) {
		    	dmgDecrPerTurn = 32;
		    } else if (name.equals("Rotten Touch")) {
		    	dmgDecrPerTurn = 58;
		    } else if (name.equals("Shadow Touch")) {
		    	dmgDecrPerTurn = 75;
		    } else if (name.equals("Tormenter Touch")) {
		    	dmgDecrPerTurn = 92;
		    } else if (name.equals("Spider Rune")) {
		    	massIncr = 45; massDecr = 3;
		    } else if (name.equals("Viper Rune")) {
		    	massIncr = 60; massDecr = 6;
		    } else if (name.equals("Raptor Rune")) {
		    	massIncr = 70; massDecr = 9;
		    } else if (name.equals("Disciple Rune")) {
		    	massIncr = 78; massDecr = 19;
		    } else if (name.equals("Zealot Rune")) {
		    	massIncr = 86; massDecr = 24;
		    } else if (name.equals("Mirage Rune")) {
		    	massIncr = 97; massDecr = 29;
		    } else if (name.equals("Delusion Rune")) {
		    	massIncr = 108; massDecr = 34;
		    } else if (name.equals("Devil Axe")) {
		    	dmgIncr = 50;
		    } else if (name.equals("Angel Breaker")) {
		    	if (day) dmgIncrPerc = 0.08; else dmgIncrPerc = 0.30;
		    } else if (name.equals("Devil Maul")) {
		    	if (day) dmgIncrPerc = 0.18; else dmgIncrPerc = 0.70;
		    } else if (name.equals("Angel's Horror")) {
		    	if (day) dmgIncrPerc = 0.28; else dmgIncrPerc = 1.10;
		    } else if (name.equals("Diabolic Pendant")) {
		    	massIncr = 30;
		    } else if (name.equals("Diabolic Flag")) {
		    	massIncr = 35;
		    } else if (name.equals("Diabolic Banner")) {
		    	massIncr = 41;
		    } else if (name.equals("Diabolic Standard")) {
		    	massIncr = 46;
		    } else if (name.equals("Diabolic Carrier")) {
		    	massIncr = 52;
		    } else if (name.equals("Diabolic Flame")) {
		    	massIncr = 58;
		    } else if (name.equals("Diabolic Inferno")) {
		    	massIncr = 64;
		    } else if (name.equals("Diabolic Abyss")) {
		    	massIncr = 76;
		    } else if (name.equals("Chaos Armor")) {
		    	healthIncrPerc = 0.20;
		    } else if (name.equals("Infernal Armor")) {
		    	healthIncrPerc = 0.30;
		    } else if (name.equals("Pit Armor")) {
		    	healthIncrPerc = 0.55;
		    } else if (name.equals("Earth Dragon Scales")) {
		    	healthIncr = -160; defVsEarthIncr = 40;
		    } else if (name.equals("Death Dragon Scales")) {
		    	healthIncr = -160; defVsDeathIncr = 40;
		    } else if (name.equals("Air Dragon Scales")) {
		    	healthIncr = -80; defVsAirIncr = 20;
		    } else if (name.equals("Forest Dragon Scales")) {
		    	healthIncr = -160; defVsForestIncr = 40;
		    } else if (name.equals("Dragon Breath")) {
		    	dmgIncr = 330;
		    } else if (name.equals("Corrosive Dragon Breath")) {
		    	dmgIncr = 360;
		    } else if (name.equals("Earth Dragon Armor")) {
		    	healthIncr = -200; defVsEarthIncr = 50;
		    } else if (name.equals("Death Dragon Armor")) {
		    	healthIncr = -200; defVsDeathIncr = 50;
		    } else if (name.equals("Air Dragon Armor")) {
		    	healthIncr = -160; defVsAirIncr = 40;
		    } else if (name.equals("Forest Dragon Armor")) {
		    	healthIncr = -240; defVsForestIncr = 50;
		    } else if (name.equals("Ebony Artifact")) {
		    	healthIncr = -8;  setDefIncr(8);
		    } else if (name.equals("Ashwood Artifact")) {
		    	healthIncr = -18; setDefIncr(18);
		    } else if (name.equals("Hemlock Artifact")) {
		    	healthIncr = -25; setDefIncr(25);
		    } else if (name.equals("Elder Artifact")) {
		    	healthIncr = -30; setDefIncr(30);
		    } else if (name.equals("Ancient Artifact")) {
		    	healthIncr = -40; setDefIncr(40);
		    } else if (name.equals("Ferocious Infusion")) {
		    	dmgIncr = 250; healthIncr = 250; fiendDecr = 60;
		    } else if (name.equals("Berserker Infusion")) {
		    	dmgIncr = 300; healthIncr = 300; fiendDecr = 75;
		    } else if (name.equals("Thrasher Infusion")) {
		    	dmgIncr = 350; healthIncr = 350; fiendDecr = 85;
		    } else if (name.equals("Marauder Infusion")) {
		    	dmgIncr = 400; healthIncr = 400; fiendDecr = 100;
		    } else if (name.equals("Mauler Infusion")) {
		    	dmgIncr = 500; healthIncr = 500; fiendDecr = 125;
		    } else if (name.equals("Destroyer Infusion")) {
		    	dmgIncr = 600; healthIncr = 600; fiendDecr = 150;
		    } else if (name.equals("Goretongue Teeth")) {
		    } else if (name.equals("Nightwing Teeth")) {
		    } else if (name.equals("Bloodclaw Teeth")) {
		    } else if (name.equals("Heavy Stone")) {
		    	healthIncrPerc = 0.10;
		    } else if (name.equals("Hot Rock")) {
		    	healthIncrPerc = 0.20;
		    } else if (name.equals("Sizzling Stone")) {
		    	healthIncrPerc = 0.30;
		    } else if (name.equals("Searing Giant Rock")) {
		    	healthIncrPerc = 0.35;
		    } else if (name.equals("Scorching Giant Rock")) {
		    	healthIncrPerc = 0.40;
		    } else if (name.equals("Granite Axe")) {
		    	golem = 8;
		    } else if (name.equals("Magma Blade")) {
		    	golem = 10;
		    } else if (name.equals("Pit Razor")) {
		    	golem = 14;
		    } else if (name.equals("Pit Scythe")) {
		    	golem = 17;
		    } else if (name.equals("Fear Mace")) {
		    	golem = 20;
		    } else if (name.equals("Nightmare Cleaver")) {
		    	golem = 25;
		    } else if (name.equals("Hound Collar")) {
		    	collar = 18;
		    } else if (name.equals("Enchanted Collar")) {
		    	collar = 24;
		    } else if (name.equals("Collar of Agility")) {
		    	collar = 28;
		    } else if (name.equals("Collar of Dexterity")) {
		    	collar = 32;
		    } else if (name.equals("Collar of Excellence")) {
		    	collar = 36;
		    } else if (name.equals("Book of Light")) {
		    	bookOfLight = 0.15;
		    } else if (name.equals("Servant's Guide")) {
		    	servantGuide = 0.20;
		    } else if (name.equals("Director's Manual")) {
		    	directorManual = 0.25;
		    } else if (name.equals("Bump Jewel")) {
		    	jewel = 14;
		    } else if (name.equals("Crushing Jewel")) {
		    	jewel = 20;
		    } else if (name.equals("Granite Jewel")) {
		    	jewel = 28;
		    } else if (name.equals("Boulder Jewel")) {
		    	jewel = 34;
		    } else if (name.equals("Silver Jewel")) {
		    	jewel = 40;
		    } else if (name.equals("Tiny Charm")) {
		    	charm = 50;
		    } else if (name.equals("Small Charm")) {
		    	charm = 60;
		    } else if (name.equals("Putrid Charm")) {
		    	charm = 70;
		    } else if (name.equals("Rancid Charm")) {
		    	charm = 90;
		    } else if (name.equals("Diamond Charm")) {
		    	charm = 120;
		    } else if (name.equals("Imp Sword")) {
		    	dmgIncr = 7;   healthIncr = 7;
		    } else if (name.equals("Imp Axe")) {
		    	dmgIncr = 21;  healthIncr = 21;
		    } else if (name.equals("Imp Mace")) {
		    	dmgIncr = 40;  healthIncr = 40;
		    } else if (name.equals("Imp Staff")) {
		    	dmgIncr = 58;  healthIncr = 58;
		    } else if (name.equals("Necromancer Wand")) {
		    	dmgIncr = 93;  healthIncr = 93;
		    } else if (name.equals("Necrolord Wand")) {
		    	dmgIncr = 156; healthIncr = 156;
		    } else if (name.equals("Distortion Wand")) {
		    	dmgIncr = 210; healthIncr = 210;
		    } else if (name.equals("Corruption Wand")) {
		    	dmgIncr = 242; healthIncr = 242;
		    } else if (name.equals("Sacrifical Knife")) {
		    	knife =10;
		    } else if (name.equals("Gore Knife")) {
		    	knife =14;
		    } else if (name.equals("Blood Knife")) {
		    	knife =18;
		    } else if (name.equals("Shadow Knife")) {
		    	knife =22;
		    } else if (name.equals("Hell Knife")) {
		    	knife =26;
		    } else if (name.equals("Overseer Blade")) {
		    	knife =30;
		    } else if (name.equals("Toothpick")) {
		    	dmgIncr = 1;
		    } else if (name.equals("Pit Worm Toxin")) {
		    	pit = 25;
		    } else if (name.equals("Pit Worm Poison")) {
		    	pit = 35;
		    } else if (name.equals("Pit Worm Venom")) {
		    	pit = 55;
		    } else if (name.equals("Pit Worm Acid")) {
		    	pit = 80;
		    } else if (name.equals("Pit Wraith Book")) {
		    } else if (name.equals("War Manuscript")) {
		    	dmgIncrPerc = 0.30;
		    } else if (name.equals("Pit Wraith Tome")) {
		    } else if (name.equals("War Tome")) {
		    	dmgIncrPerc = 0.50;
		    } else if (name.equals("Security Rod")) {
		    	setDefIncr(12);
		    } else if (name.equals("Shield Rod")) {
		    	setDefIncr(14);
		    } else if (name.equals("Protection Wand")) {
		    	setDefIncr(16);
		    } else if (name.equals("Guard Rod")) {
		    	setDefIncr(18);
		    } else if (name.equals("Guardian Wand")) {
		    	setDefIncr(21);
		    } else if (name.equals("Aegis Wand")) {
		    	setDefIncr(24);
		    } else if (name.equals("Staff of Warden")) {
		    	setDefIncr(27);
		    } else if (name.equals("Staff of Safeguard")) {
		    	setDefIncr(30);
		    } else if (name.equals("Weak Rat Poison")) {
		    	healthIncr = 20;
		    } else if (name.equals("Rat Poison")) {
		    	healthIncr = 62;
		    } else if (name.equals("Swamp Rat Poison")) {
		    	healthIncr = 132;
		    } else if (name.equals("Plague Rat Poison")) {
		    	healthIncr = 188;
		    } else if (name.equals("Crystal Hammer")) {
		    	healthIncr = 340;
		    } else if (name.equals("Brim Hammer")) {
		    	dmgIncr = 220;
		    } else if (name.equals("Black Crystal Hammer")) {
		    	healthVsDeathIncr = 500; healthVsForestIncr = 500; healthVsAirIncr = 500; healthVsEarthIncr = 750;
		    } else if (name.equals("Black Crystal Maul")) {
		    	healthVsDeathIncr = 750; healthVsForestIncr = 500; healthVsAirIncr = 500; healthVsEarthIncr = 500;
		    } else if (name.equals("Dark Brim Hammer")) {
		    	dmgVsDeathIncr = 300; dmgVsForestIncr = 300; dmgVsAirIncr = 450; dmgVsEarthIncr = 300;
		    } else if (name.equals("Dark Brim Maul")) {
		    	dmgVsDeathIncr = 300; dmgVsForestIncr = 450; dmgVsAirIncr = 300; dmgVsEarthIncr = 300;
		    } else if (name.equals("Depravity Hammer")) {
		    	healthIncr = 700;
		    } else if (name.equals("Disease Maul")) {
		    	dmgVsDeathIncr = 600; dmgVsForestIncr = 400; dmgVsAirIncr = 400; dmgVsEarthIncr = 400;
		    } else if (name.equals("Disease Hammer")) {
		    	dmgVsDeathIncr = 400; dmgVsForestIncr = 400; dmgVsAirIncr = 400; dmgVsEarthIncr = 600;
		    } else if (name.equals("Sickness Maul")) {
		    	healthVsDeathIncr = 800; healthVsForestIncr = 1200; healthVsAirIncr = 800; healthVsEarthIncr = 800;
		    } else if (name.equals("Sickness Hammer")) {
		    	healthVsDeathIncr = 800; healthVsForestIncr = 800; healthVsAirIncr = 1200; healthVsEarthIncr = 800;
		    } else if (name.equals("Death Hammer")) {
		    	dmgIncr = 600;
		    } else if (name.equals("Willow Heart")) {
		    	heart = 170;
		    } else if (name.equals("Frozen Heart")) {
		    	heart = 210;
		    } else if (name.equals("Succubus Heart")) {
		    	heart = 280;
		    } else if (name.equals("Ancient Heart")) {
		    	heart = 370;
		    } else if (name.equals("Earth Storm Enhancer")) {
		    	 defVsAirIncr = 20; defVsDeathIncr = 20; defVsForestIncr = 20; defVsEarthIncr = -20;
		    } else if (name.equals("Death Storm Enhancer")) {
		    	 defVsAirIncr = 25; defVsDeathIncr = -25; defVsForestIncr = 25; defVsEarthIncr = 25;
		    } else if (name.equals("Air Storm Enhancer")) {
		    	 defVsAirIncr = -25; defVsDeathIncr = 25; defVsForestIncr = 25; defVsEarthIncr = 25;
		    } else if (name.equals("Earth Storm Augmenter")) {
		    	 defVsAirIncr = 35; defVsDeathIncr = 35; defVsForestIncr = 35; defVsEarthIncr = -35;
		    } else if (name.equals("Death Storm Augmenter")) {
		    	 defVsAirIncr = 35; defVsDeathIncr = -35; defVsForestIncr = 35; defVsEarthIncr = 35;
		    } else if (name.equals("Air Storm Augmenter")) {
		    	 defVsAirIncr = -40; defVsDeathIncr = 40; defVsForestIncr = 40; defVsEarthIncr = 40;
		    } else if (name.equals("Undead Club")) {
		    	dmgIncr = 10;
		    } else if (name.equals("Rot Club")) {
		    	dmgIncr = 30;
		    } else if (name.equals("Stench Club")) {
		    	dmgIncr = 65;
		    } else if (name.equals("Zombie Club")) {
		    	dmgIncr = 86;
		    } else if (name.equals("Doom Club")) {
		    	dmgIncr = 142;
		    } else if (name.equals("Dark Claws")) {
		    	dmgIncrPerc = 0.08; healthIncrPerc = 0.08;
		    } else if (name.equals("Mystic Claws")) {
		    	dmgIncrPerc = 0.18; healthIncrPerc = 0.18;
		    } else if (name.equals("Illusion Claws")) {
		    	dmgIncrPerc = 0.34; healthIncrPerc = 0.34;
		    }
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
		  else
			  unknownEnchant = true;
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
	  public int getMassIncr() { return massIncr; }

	  public double getBookOfLight() {
		return bookOfLight;
	}

	public void setBookOfLight(double bookOfLight) {
		this.bookOfLight = bookOfLight;
	}

	public double getServantGuide() {
		return servantGuide;
	}

	public void setServantGuide(double servantGuide) {
		this.servantGuide = servantGuide;
	}

	public double getDirectorManual() {
		return directorManual;
	}

	public void setDirectorManual(double directorManual) {
		this.directorManual = directorManual;
	}

	/** @return the damage removed by mass items (runes) */
	  public int getMassDecr() { return massDecr; }
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
	  
	  public boolean needNumberOfSame() { return (massIncr > 0 || bookOfLight>0 || servantGuide>0 || directorManual>0 || fiendDecr>0); }
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

	public int getFiendDecr() {
		return fiendDecr;
	}

	public void setFiendDecr(int fiendDecr) {
		this.fiendDecr = fiendDecr;
	}
	
	public void setDefIncr(int def) {
		this.defVsAirIncr = def;
		this.defVsDeathIncr = def;
		this.defVsEarthIncr = def;
		this.defVsForestIncr = def;
	}
	
	public int getDefIncr() {
		int def = defVsAirIncr;
		def = Math.max(def, defVsDeathIncr);
		def = Math.max(def, defVsEarthIncr);
		def = Math.max(def, defVsForestIncr);
		return def;
	}
	
}
