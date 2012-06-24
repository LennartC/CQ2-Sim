package be.lacerta.cq2.travelcalc;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public abstract class CritlistParser {


	public static Vector<String[]> parseEigen(String reader) {
		
		Vector<String[]> eigenList = new Vector<String[]>();
		
		String[] lines = reader.split("\n");
		int index = 0;
		int defenders = 0;

		for (int i = 0; i < lines.length; i++) {
			String regel = lines[i];

			if (regel.indexOf("passive") > 0) {
				defenders++;
			}

		}

		String[][] crits = new String[defenders][4];
		int[][] critStats = new int[defenders][6];
		String[] ids = new String[defenders];
		String[] items = new String[defenders];
		String[] curses = new String[defenders];
		String[] critRegel = null;
		
		for (int i = 0; i < lines.length; i++) {
			String regel = lines[i];
			if (regel.indexOf("passive") > 0) {
				regel = regel.replaceAll("\t", "  ").trim();
				
				critRegel = regel.split("\\s{2,}+");
				ids[index] = String.valueOf(index + 1);
				String critname = critRegel[0];
				crits[index][0] = critname
						.substring(critname.indexOf(". ") + 2);
				String soort = critRegel[1];
				//check for nethers
				if (soort.indexOf("(N)") > 0) {
					crits[index][1] = soort
							.substring(0, soort.indexOf("/"));
					crits[index][2] = soort.substring(
							soort.indexOf("/") + 1, soort.indexOf(" (N)"));
				} else {
					//check for iths
					if (soort.indexOf("(I)") > 0) {
						crits[index][1] = soort.substring(0, soort
								.indexOf("/"));
						crits[index][2] = soort.substring(soort
								.indexOf("/") + 1, soort.indexOf(" (I)"));
					} else { 
						//normal crits
						
						crits[index][1] = soort.substring(0, soort
								.indexOf("/"));
						crits[index][2] = soort.substring(soort
								.indexOf("/") + 1, soort.indexOf(","));
					}
				}
				if (critRegel.length > 5)
					items[index] = critRegel[5];
				String[] damhlth = critRegel[3].split("/");
				String dam = damhlth[0];
				dam = dam.trim();
				String hlth = damhlth[1];
				hlth = hlth.trim();

				String stats = critRegel[4];
				String forest = stats.substring(stats.indexOf("F") + 1,
						stats.indexOf("/"));
				String death = stats.substring(stats.indexOf("/D") + 2,
						stats.indexOf("/A"));
				String air = stats.substring(stats.indexOf("/A") + 2, stats
						.indexOf("/E"));
				String earth = stats.substring(stats.indexOf("/E") + 2,
						stats.length());
				earth = earth.trim();
				critStats[index][0] = Integer.parseInt(dam);
				critStats[index][1] = Integer.parseInt(hlth);
				critStats[index][2] = Integer.parseInt(forest);
				critStats[index][3] = Integer.parseInt(death);
				critStats[index][4] = Integer.parseInt(air);
				critStats[index][5] = Integer.parseInt(earth);
				if (i < (lines.length - 1)) {
					String Curse = lines[i + 1];
					curses[index] = "none";
					if (Curse.contains("Doppelganger")) {
						curses[index] = "Doppelganger";
					} else if (Curse.contains("Metamorphosis")) {
						curses[index] = "Metamorphosis";
					}
				}
				index++;
			}
		}
		for (int i = 0; i < crits.length; i++) {
			if (items[i] == null)
				items[i] = "none";
			if (curses[i] == null)
				curses[i] = "none";
			String crit[] = { "" + ids[i], crits[i][0], crits[i][1],
					crits[i][2], "" + critStats[i][0],
					"" + critStats[i][1], "" + critStats[i][2],
					"" + critStats[i][3], "" + critStats[i][4],
					"" + critStats[i][5], items[i], curses[i] };
			eigenList.add(crit);
		}
		
		return eigenList;
	}

	public static Vector<String[]> parseEncounter(String reader) {
		Vector<String[]> encounterList = new Vector<String[]>();
		String[] lines = reader.split("\n");
		int index = 0;

		List<String[]> encountercrits = new ArrayList<String[]>();
		List<Integer[]> encountercritStats = new ArrayList<Integer[]>();
		List<String> encounterids = new ArrayList<String>();
		List<String> encounteritems = new ArrayList<String>();
		List<String> encountercurses = new ArrayList<String>();
		String[] critregelEnc = null;
		String[] damhlth = null;
		String soort = null;
		String defenses = null;
		int temp = 0;
		for (int i = 0; i < lines.length; i++) {

			String regel = lines[i];
			
			regel = regel.replaceAll("\t", "  ").trim();
			if (regel.indexOf("Please copy the following number into the field next to it") >= 0) {
				break;
			} else 	if (regel.indexOf("passive") > 0) {
				
				encountercrits.add(null);
				encountercritStats.add(null);
				encounterids.add(null);
				encounteritems.add(null);
				encountercurses.add(null);
				
				try {
					temp++;
					critregelEnc = regel.split("\\s{2,}+");
					String[] encounterCrit = new String[4];
					encounterCrit[0] = critregelEnc[0];
					soort = critregelEnc[1];
					damhlth = critregelEnc[3].split("/");
					defenses = critregelEnc[4];
	
					encounterids.set(index,String.valueOf(index + 1));
	
					if (soort.indexOf("(N)") > 0) {
						encounterCrit[1] = soort.substring(0, soort
								.indexOf("/"));
						encounterCrit[2] = soort.substring(soort
								.indexOf("/") + 1, soort.indexOf(" (N)"));
					} else {
						if (soort.indexOf("(I)") > 0) {
							encounterCrit[1] = soort.substring(0, soort
									.indexOf("/"));
							encounterCrit[2] = soort.substring(soort
									.indexOf("/") + 1, soort.indexOf(" (I)"));
						} else {
							encounterCrit[1] = soort.substring(0, soort
									.indexOf("/"));
							encounterCrit[2] = soort.substring(soort
									.indexOf("/") + 1, soort.indexOf(","));
						}
					}
	
					encountercrits.set(index,encounterCrit);
					encounteritems.set(index,"none");
					encountercurses.set(index,"none");
	
					String dam = damhlth[0];
					String hlth = damhlth[1];
	
					String forest = defenses.substring(defenses.indexOf("F") + 1,
							defenses.indexOf("/"));
					String death = defenses.substring(defenses.indexOf("/D") + 2,
							defenses.indexOf("/A"));
					String air = defenses.substring(defenses.indexOf("/A") + 2, defenses
							.indexOf("/E"));
					String earth = defenses.substring(defenses.indexOf("/E") + 2,
							defenses.length());
					Integer[] encountercritStat = new Integer[6];
					encountercritStat[0] = Integer.parseInt(dam.trim());
					encountercritStat[1] = Integer.parseInt(hlth.trim());
					encountercritStat[2] = Integer.parseInt(forest.trim());
					encountercritStat[3] = Integer.parseInt(death.trim());
					encountercritStat[4] = Integer.parseInt(air.trim());
					earth = earth.replaceAll(" ", "");
					encountercritStat[5] = Integer.parseInt(earth.trim());
					encountercritStats.set(index,encountercritStat);
					index++;
				} catch (ArrayIndexOutOfBoundsException aioobe) {}
			}
		}
		for (int i = 0; i < encountercrits.size(); i++) {
			if (encounteritems.get(i) == null)
				encounteritems.set(i,"none");
			String encountercrit[] = { "" + encounterids.get(i),
					encountercrits.get(i)[0], encountercrits.get(i)[1],
					encountercrits.get(i)[2], "" + encountercritStats.get(i)[0],
					"" + encountercritStats.get(i)[1],
					"" + encountercritStats.get(i)[2],
					"" + encountercritStats.get(i)[3],
					"" + encountercritStats.get(i)[4],
					"" + encountercritStats.get(i)[5], encounteritems.get(i),
					encountercurses.get(i) };
			encounterList.add(encountercrit);
		}
		return encounterList;
	
	}

	public static void outputOwnList(Vector<String[]> eigenList) {
		for (int i = 0; i < eigenList.size(); i++) {
			String eentje[] = (String[]) eigenList.elementAt(i);
			for (int j = 0; j < eentje.length; j++) System.out.print(eentje[j] + " | ");
			System.out.println();
		}
			
	}

	public static void outputEncounterList(Vector<String[]> encounterList) {
		for (int i = 0; i < encounterList.size(); i++) {
			String eentje[] = (String[]) encounterList.elementAt(i);
			for (int j = 0; j < eentje.length; j++) System.out.print(eentje[j] + " | ");
			System.out.println();
		}
		

	}



}