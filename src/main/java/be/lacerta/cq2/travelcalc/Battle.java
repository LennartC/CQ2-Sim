package be.lacerta.cq2.travelcalc;

import java.io.IOException;
import java.util.Vector;

public class Battle {

	Vector<String[]> eigen;
	Vector<String[]> encounter;
	
	
	/**
	 * @param parser
	 * @param gui
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Battle(String eigenString, String encounterString) {
		this.eigen = CritlistParser.parseEigen(eigenString);
		this.encounter = CritlistParser.parseEncounter(encounterString);
	}
	
	public String calc() {
//		long timeBefore = System.currentTimeMillis();
		boolean solutionFound = false;
		String battleResult;
		boolean defChecked= false;
		String BattleType="Unknown";
		
		//not enough creatures
		if (eigen == null || eigen.size()==0 || encounter == null || encounter.size() == 0) {
			return "Nothing to parse";
		} else if (eigen.size() < encounter.size()) {
			return "Not enough creatures, " + eigen.size() + " vs " + encounter.size();
		} else {
			
			//auto-select
			int defend[] = new int[encounter.size()];
			for(int i=0;i<defend.length;i++) defend[i]=i+1;
			if(Battlecheck(eigen, encounter, defend)) {
				solutionFound=true;
				defChecked=true;
				BattleType = "Auto Select Method";
			}
			
			//probeer simpel
			if(!solutionFound) {
				defend = BattleNormaal(eigen, encounter);
				solutionFound = true;
				BattleType = "Simple Method";
				for (int k = 0; k < defend.length; k++) {
					if (defend[k] == -1) solutionFound = false;
				}
			}

			//probeer iths
			if (!solutionFound) {
				int defendiths[] = BattleMetIths(eigen, encounter);
				for (int t = 0; t < defendiths.length; t++) {
					defend[t] = defendiths[t];
				}
				BattleType = "Itherian Method";
				solutionFound = true;
				for (int k = 0; k < defendiths.length; k++)
					if (defendiths[k] == -1)
						solutionFound = false;
			}
			
			//checken
			//System.out.println("can kill this way: " + this.Battlecheck(eigen, encounter, defend));
			if (solutionFound && !defChecked) {
				if (!(Battlecheck(eigen, encounter, defend))) {	
					solutionFound = false;
					defChecked = true;
					////System.out.println("not all masscrits are used");
				}
			}
			
			//return String bepalen
			if (solutionFound) {
				//check if different order
				boolean difOrder = false;
				for (int k = 0; k < defend.length; k++) {
					if (defend[k] != k+1) {
						difOrder = true;
						break;
					}
				}
				
				//defenders string maken
				battleResult = "" + defend[0];
				for (int k = 1; k < defend.length; k++)	battleResult = (battleResult + "," + defend[k]);

				if (difOrder) {
					////System.out.println("dif order: " + battleResult + " (" + BattleType + ")");
					return "Different order: " + battleResult + " (" + BattleType + ")";
				} else {
					////System.out.println("killed all: " + battleResult + " (" + BattleType + ")");
					return "Killed all: " + battleResult + " (" + BattleType + ")";
				}

			} else return "Couldn't find a solution (" + encounter.size()+ " crits)";
			

		}

	}
	
	private int[] BattleNormaal(Vector<String[]> eigen, Vector<String[]> encounter) {
		
		int ownDef = 0;
		int oppDef = 0;

		int clerics = 0;
		int diabs = 0;
		int aantal = 0;
		int aantalander = 0;
		int defend[] = new int[encounter.size()];

		for (int i = 0; i < defend.length; i++)	defend[i] = -1;
		
		//calc aantal mass crits
		for (int i = 0; i < eigen.size(); i++) {
			String crit[] = (String[]) eigen.elementAt(i);
			if (crit[2].equals("Cleric"))
				clerics++;
			if (crit[2].equals("Diabolic Horde"))
				diabs++;
		}
		
		int check[] = new int[eigen.size()];
		for (int i = 0; i < check.length; i++)
			check[i] = -1;

		String massType;
		if (clerics > diabs) {
			aantal = clerics;
			massType = "Cleric";
		} else {
			aantal = diabs;
			massType = "Diabolic Horde";
		}

		if (aantal > encounter.size())
			aantal = encounter.size();
		aantalander = encounter.size() - aantal;
		for (int i = 0; i < encounter.size(); i++) {
			String encounterCrit[] = (String[]) encounter.elementAt(i);
			String oppClass = encounterCrit[3];
			int oppDmg = Integer.parseInt(encounterCrit[4]);
			int oppHlth = Integer.parseInt(encounterCrit[5]);
			String oppRace = encounterCrit[2];
			String oppName = encounterCrit[0] + "." + encounterCrit[1];
			boolean kill = false;
			for (int j = 0; j < eigen.size(); j++) {
				String eigenCrit[] = (String[]) eigen.elementAt(j);
				String ownClass;
				int ownDmg;
				int ownHlth;
				String ownRace;
				String ownName;
				int id;
				String item;
				String curse;
				Creature own;
				Creature opp;
				Fight battle;
				if (!kill && check[j] < 0) {
					ownClass = eigenCrit[3];
					ownDmg = Integer.parseInt(eigenCrit[4]);
					ownHlth = Integer.parseInt(eigenCrit[5]);
					ownRace = eigenCrit[2];
					ownName = eigenCrit[0] + "." + eigenCrit[1];
					id = Integer.parseInt(eigenCrit[0]);
					if (ownClass.equals("forest"))
						oppDef = Integer.parseInt(encounterCrit[6]);
					if (ownClass.equals("death"))
						oppDef = Integer.parseInt(encounterCrit[7]);
					if (ownClass.equals("air"))
						oppDef = Integer.parseInt(encounterCrit[8]);
					if (ownClass.equals("earth"))
						oppDef = Integer.parseInt(encounterCrit[9]);
					if (oppClass.equals("forest"))
						ownDef = Integer.parseInt(eigenCrit[6]);
					if (oppClass.equals("death"))
						ownDef = Integer.parseInt(eigenCrit[7]);
					if (oppClass.equals("air"))
						ownDef = Integer.parseInt(eigenCrit[8]);
					if (oppClass.equals("earth"))
						ownDef = Integer.parseInt(eigenCrit[9]);
					item = eigenCrit[10];
					
					if (item.contains("Health")) {
						ownHlth = ownHlth
								+ Integer.parseInt(item.substring(item
										.indexOf("+") + 1, item
										.indexOf(" Health")));
					}
					if (item.contains("Damage")) {
						ownDmg = ownDmg + Integer.parseInt(item.substring(item.indexOf("+") + 1,
									item.indexOf(" Damage")));
						}
					if (item.contains("Defenses")) {
						   ownDef = ownDef + Integer.parseInt(item.substring(item.indexOf("+") + 1,
									item.indexOf("% Defenses")));
						    ownDef=Math.min(140, ownDef);
						}
					curse = eigenCrit[11];
					if (curse.contains("Meta")) {
						int dmg = ownDmg;
						int hlt = ownHlth;
						double m = Math.floor(1) / 100;
						dmg *= m;
						ownDmg = dmg;
						hlt *= m;
						ownHlth = hlt;
					}

					if (eigenCrit[2].equals(massType)) {
						own = new Creature(ownName, ownDmg, ownHlth, ownDef,
								item, aantal, aantalander, oppRace);
					} else {
						own = new Creature(ownName, ownDmg, ownHlth, ownDef,
								item, oppClass, oppRace);
					}

					opp = new Creature(oppName, oppDmg, oppHlth, oppDef,
							"niks", ownClass, ownRace);
					battle = new Fight(own, opp);
					if (battle.win() == 20D && !(battle.getFight().contains("exhausted")))  {
						kill = true;
						check[j] = i;
						defend[i] = id;
					}

				}
			}

		}

		return defend;
	}

	//
	// Check battle
	//
	private boolean Battlecheck(Vector<String[]> eigen, Vector<String[]> encounter, int[] defend) {

		int ownDef = 0;
		int oppDef = 0;

		boolean defendcheck = true;

		int clerics = 0;
		int diabs = 0;
		for (Integer i : defend) {
			//System.out.println(i);
			String crit[] = (String[]) eigen.elementAt(i-1);
			//System.out.println(i + " " + crit[2]);
			if (crit[2].equals("Cleric"))
				clerics++;
			if (crit[2].equals("Diabolic Horde"))
				diabs++;
		}
		
		//System.out.println(clerics);

		int ithHlth = 0;
		int ithDef = 0;
		int ithDmg = 0;
		
		//zoek alle iths en add ith health, damage en def in variabelen
		for (Integer i : defend) {
			String eigenCrit[] = (String[]) eigen.elementAt(i-1);
			String item = eigenCrit[10];
			if (item.contains("Health")) {
				ithHlth+= Integer.parseInt(item.substring(item.indexOf("+") + 1, item.indexOf(" Health")));
			}
			if (item.contains("Damage")) {
				ithDmg+= Integer.parseInt(item.substring(item.indexOf("+") + 1,item.indexOf(" Damage")));
			}
			if (item.contains("Defenses")) {
				ithDef+= Integer.parseInt(item.substring(
						item.indexOf("+") + 1, item.indexOf("% Defenses")));
			}
		}

		for (int i = 0; i < encounter.size(); i++) {
			String encounterCrit[] = (String[]) encounter.elementAt(i);
			String oppClass = encounterCrit[3];
			int oppDmg = Integer.parseInt(encounterCrit[4]);
			int oppHlth = Integer.parseInt(encounterCrit[5]);
			String oppRace = encounterCrit[2];
			String oppName = encounterCrit[0] + "."+ encounterCrit[1];

			int defender = defend[i];
			String eigenCrit[] = (String[]) eigen.elementAt(defender-1);
			String ownClass;
			int ownDmg;
			int ownHlth;
			String item;
			String ownRace;
			String ownName;
			Creature own;
			Creature opp;
			Fight battle;

			ownClass = eigenCrit[3];
			ownDmg = Integer.parseInt(eigenCrit[4]);
			ownHlth = Integer.parseInt(eigenCrit[5]);
			ownRace = eigenCrit[2];
			ownName = eigenCrit[0] + "." + eigenCrit[1];
			
			if (ownClass.equals("forest")) oppDef = Integer.parseInt(encounterCrit[6]);
			if (ownClass.equals("death")) oppDef = Integer.parseInt(encounterCrit[7]);
			if (ownClass.equals("air"))	oppDef = Integer.parseInt(encounterCrit[8]);
			if (ownClass.equals("earth")) oppDef = Integer.parseInt(encounterCrit[9]);
			
			if (oppClass.equals("forest")) ownDef = Integer.parseInt(eigenCrit[6]);
			if (oppClass.equals("death")) ownDef = Integer.parseInt(eigenCrit[7]);
			if (oppClass.equals("air"))	ownDef = Integer.parseInt(eigenCrit[8]);
			if (oppClass.equals("earth")) ownDef = Integer.parseInt(eigenCrit[9]);
			
			item = eigenCrit[10];
			
			if (eigenCrit[2].matches("Diabolic Horde|Cleric")) {
				
				int aantalMass=clerics;
				if(eigenCrit[2].matches("Diabolic Horde")) aantalMass=diabs;
				
				int aantalAnder = encounter.size()-aantalMass;
				//System.out.println(aantalMass + " " + aantalAnder);
				own = new Creature(ownName, ownDmg+ithDmg, ownHlth+ithHlth, ownDef+ithDef, item, aantalMass,
						aantalAnder, oppRace);
				//System.out.println(own);
				//System.out.println(ownName + " " + (ownDmg+ithDmg) + " " + (ownHlth+ithHlth) + " " + (ownDef+ithDef) + " " + item + " " + aantalMass + " " +aantalAnder + " " + oppRace);
			} else {
				own = new Creature(ownName, ownDmg+ithDmg, ownHlth+ithHlth, ownDef+ithDef, item, oppClass, oppRace);
			}

			opp = new Creature(oppName, oppDmg, oppHlth, oppDef, "niks",0,0, ownRace);
			battle = new Fight(own, opp);
			if (battle.win() == 20D && !(battle.getFight().contains("exhausted")))  {
			} else {
				defendcheck = false;
			}
		}
		return defendcheck;
	}


	/**
	 * @param eigen
	 * @param encounter
	 * @return
	 */
	private int[] BattleMetIths(Vector<String[]> eigen, Vector<String[]> encounter) {
		int ownDef = 0;
		int oppDef = 0;

		int clerics = 0;
		int diabs = 0;
		int aantal = 0;
		int aantalander = 0;

		int defend[] = new int[encounter.size()];

		for (int i = 0; i < defend.length; i++)
			defend[i] = -1;

		for (int i = 0; i < eigen.size(); i++) {
			String crit[] = (String[]) eigen.elementAt(i);
			if (crit[2].equals("Cleric"))
				clerics++;
			if (crit[2].equals("Diabolic Horde"))
				diabs++;

		}

		int check[] = new int[eigen.size()];
		for (int i = 0; i < check.length; i++)
			check[i] = -1;

		String massType;
		if (clerics > diabs) {
			aantal = clerics;
			massType = "Cleric";
		} else {
			aantal = diabs;
			massType = "Diabolic Horde";
		}
		if (aantal > encounter.size())
			aantal = encounter.size();
		aantalander = encounter.size() - aantal;

		int ithHlth = 0;
		int ithDef = 0;
		int ithDmg = 0;
		
		//zoek alle iths en add ith health, damage en def in variabelen
		for (int i = 0; i < eigen.size()  ; i++) {
			String eigenCrit[] = (String[]) eigen.elementAt(i);
			String item = eigenCrit[10];
			if (item.contains("Health")) {
				ithHlth+= Integer.parseInt(item.substring(item.indexOf("+") + 1, item.indexOf(" Health")));
			}
			if (item.contains("Damage")) {
				ithDmg+= Integer.parseInt(item.substring(item.indexOf("+") + 1,item.indexOf(" Damage")));
			}
			if (item.contains("Defenses")) {
				ithDef+= Integer.parseInt(item.substring(
						item.indexOf("+") + 1, item.indexOf("% Defenses")));
			}
		}

		//iths vastzetten in defend array
		for (int i = 0; i < eigen.size()  ; i++) {

			
			String eigenCrit[] = (String[]) eigen.elementAt(i);
			String ownClass = eigenCrit[3];
			String ownDmg = eigenCrit[4];
			String ownHlth = eigenCrit[5];
			String ownRace = eigenCrit[2];
			String ownName = eigenCrit[0] + "." + eigenCrit[1];

			String item = eigenCrit[10];
			
			int id = Integer.parseInt(eigenCrit[0]);
			
			//if itherian
			if (item.contains("Health") || item.contains("Damage") || item.contains("Defenses")) {

				boolean kill = false;
				for (int j = 0; j < encounter.size(); j++) {
					String encounterCrit[] = (String[]) encounter.elementAt(j);
					String oppClass;
					int oppDmg;
					int oppHlth;
					String oppRace;
					String oppName;
					String curse;
					Creature own;
					Creature opp;
					Fight battle;
					if (!kill && defend[j] < 0) {

						oppClass = encounterCrit[3];
						oppDmg = Integer.parseInt(encounterCrit[4]);
						oppHlth = Integer.parseInt(encounterCrit[5]);
						oppRace = encounterCrit[2];
						oppName = encounterCrit[0] + "." + encounterCrit[1];
						if (ownClass.equals("forest"))
							oppDef = Integer.parseInt(encounterCrit[6]);
						if (ownClass.equals("death"))
							oppDef = Integer.parseInt(encounterCrit[7]);
						if (ownClass.equals("air"))
							oppDef = Integer.parseInt(encounterCrit[8]);
						if (ownClass.equals("earth"))
							oppDef = Integer.parseInt(encounterCrit[9]);
						if (oppClass.equals("forest"))
							ownDef = Integer.parseInt(eigenCrit[6]);
						if (oppClass.equals("death"))
							ownDef = Integer.parseInt(eigenCrit[7]);
						if (oppClass.equals("air"))
							ownDef = Integer.parseInt(eigenCrit[8]);
						if (oppClass.equals("earth"))
							ownDef = Integer.parseInt(eigenCrit[9]);
						ownHlth = eigenCrit[5];
						ownDmg = eigenCrit[4];

						curse = eigenCrit[11];
						if (curse.contains("Meta")) {
							int dmg = Integer.parseInt(ownDmg);
							int hlt = Integer.parseInt(ownHlth);
							double m = Math.floor(1) / 100;
							dmg *= m;
							ownDmg = String.valueOf(dmg);
							hlt *= m;
							ownHlth = String.valueOf(hlt);
						}
						
						int newhlt = Integer.parseInt(ownHlth) + ithHlth;
						int totaalHlth = newhlt;

						int newdam = Integer.parseInt(ownDmg) + ithDmg;
						int totaalDmg = newdam;

						int newdef = Math.min(140, ownDef + ithDef);
						
						int totaalDef = newdef;
						
						if (eigenCrit[2].equals(massType)) {
							own = new Creature(ownName, totaalDmg, totaalHlth,
									totaalDef, item, aantal, aantalander, oppRace);
						} else {
							own = new Creature(ownName, totaalDmg, totaalHlth,
									totaalDef, item, oppClass, oppRace);
						}
						if (oppDef < 151) {
							opp = new Creature(oppName, oppDmg, oppHlth,
									oppDef, "niks", ownClass, ownRace);
							battle = new Fight(own, opp);

							if (battle.win() == 20D && !(battle.getFight().contains("exhausted")))  {
								kill = true;
								check[i] = j;
								defend[j] = id;
								if (eigenCrit[2].equals(massType)) {
									defend[encounter.size()] = defend[encounter
											.size()] + 1;
								}

							}
						}
					}
				}

			}
		}

		//rest vastzetten in defend array
		for (int i = 0; i < encounter.size(); i++) {
			if ( defend[i] < 0 ){
			String encounterCrit[] = (String[]) encounter.elementAt(i);
			String oppClass = encounterCrit[3];
			int oppDmg = Integer.parseInt(encounterCrit[4]);
			int oppHlth = Integer.parseInt(encounterCrit[5]);
			String oppRace = encounterCrit[2];
			String oppName = encounterCrit[0] + "."+ encounterCrit[1];
			boolean kill = false;

			for (int j = 0; j < eigen.size(); j++) {
				String eigenCrit[] = (String[]) eigen.elementAt(j);
				String ownClass;
				int ownDmg;
				int ownHlth;
				String ownRace;
				String ownName;
				int id;
				String item;
				String curse;
				Creature own;
				Creature opp;
				Fight battle;
				if (!kill && check[j] < 0) {
					ownClass = eigenCrit[3];
					ownDmg = Integer.parseInt(eigenCrit[4]);
					ownHlth = Integer.parseInt(eigenCrit[5]);
					ownRace = eigenCrit[2];
					ownName = eigenCrit[0] + "." + eigenCrit[1];
					id = Integer.parseInt(eigenCrit[0]);
					if (ownClass.equals("forest"))
						oppDef = Integer.parseInt(encounterCrit[6]);
					if (ownClass.equals("death"))
						oppDef = Integer.parseInt(encounterCrit[7]);
					if (ownClass.equals("air"))
						oppDef = Integer.parseInt(encounterCrit[8]);
					if (ownClass.equals("earth"))
						oppDef = Integer.parseInt(encounterCrit[9]);
					if (oppClass.equals("forest"))
						ownDef = Integer.parseInt(eigenCrit[6]);
					if (oppClass.equals("death"))
						ownDef = Integer.parseInt(eigenCrit[7]);
					if (oppClass.equals("air"))
						ownDef = Integer.parseInt(eigenCrit[8]);
					if (oppClass.equals("earth"))
						ownDef = Integer.parseInt(eigenCrit[9]);
					item = eigenCrit[10];
					curse = eigenCrit[11];
					if (curse.contains("Meta")) {
						int dmg = ownDmg;
						int hlt = ownHlth;
						double m = Math.floor(1)/100;
						dmg *= m;
						ownDmg = dmg;
						hlt *= m;
						ownHlth = hlt;
					}

					ownHlth = ownHlth + ithHlth;

					ownDmg = ownDmg + ithDmg;

					int newdef= Math.min(140, ownDef + ithDef);
					ownDef= newdef;


					if (eigenCrit[2].equals(massType)) {
						own = new Creature(ownName, ownDmg, ownHlth, ownDef, item,
								aantal, aantalander, oppRace);
					} else {
						own = new Creature(ownName, ownDmg, ownHlth, ownDef, item, oppClass, oppRace);
					}
					if (oppDef < 151) {
						opp = new Creature(oppName, oppDmg, oppHlth, oppDef, "niks", ownClass, ownRace);
						battle = new Fight(own, opp);

						if (battle.win() == 20D && !(battle.getFight().contains("exhausted")))  {
							kill = true;
							check[j] = i;
							defend[i] = id;

						}
					}
				}
			}
		}
		}



		return defend;
	}

}