package be.lacerta.cq2.travelcalc;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String encounter = "";
		encounter += "There are 1 creatures attacking you.\n";
		encounter += "Enter the number of one of your creatures in each field and press the defend button at the bottom of the page.\n";
		encounter += "  Mog Rat 	Rat/earth, passive 	L2 	4/10 	F50/D0/A50/E100 		\n";
		encounter += "\n";
		encounter += "Please copy the following number into the field next to it: ?\n";
		encounter += "\n";
		encounter += "				\n";
		encounter += "\n";
		encounter += "\n";
		encounter += "  \n";
		encounter += "There are 1 creatures attacking you.\n";
		encounter += "Enter the number of one of your creatures in each field and press the defend button at the bottom of the page.\n";
		encounter += "  Mog Rat 	Rat/earth, passive 	L2 	4/10 	F50/D0/A50/E100 		\n";
		encounter += "\n";
		encounter += "Please copy the following number into the field next to it: ?\n";
		encounter += "\n";
		
		Battle battle = new Battle(own, encounter);
		System.out.println(battle.calc());

	}

	private static String own = "1. Spider Sorceress 	Cleric/forest, passive 	L20 	403/811 	F100/D20/A70/E0 	Spider Rune 	\n2. Spider Sorceress 	Cleric/forest, passive 	L20 	403/811 	F100/D20/A70/E0 	Spider Rune 	\n3. Spider Sorceress 	Cleric/forest, passive 	L20 	403/811 	F100/D20/A70/E0 	Spider Rune 	\n4. Spider Sorceress 	Cleric/forest, passive 	L16 	336/676 	F100/D20/A70/E0 	Spider Rune 	\n5. Spider Sorceress 	Cleric/forest, passive 	L16 	336/676 	F100/D20/A70/E0 	Spider Rune 	\n6. Spider Sorceress 	Cleric/forest, passive 	L15 	319/642 	F100/D20/A70/E0 	Spider Rune 	\n7. Spider Sorceress 	Cleric/forest, passive 	L15 	319/642 	F100/D20/A70/E0 	Spider Rune 	\n8. Spider Sorceress 	Cleric/forest, passive 	L16 	336/676 	F100/D20/A70/E0 	Spider Rune 	\n9. Red Storm 	Storm/air, passive 	L20 	302/605 	F25/D80/A75/E65 	Air Storm Enhancer 	\n10. Red Storm 	Storm/air, passive 	L20 	302/605 	F25/D80/A75/E65 	Air Storm Enhancer 	\n11. Red Storm 	Storm/air, passive 	L18 	277/554 	F0/D55/A100/E40 		";
	//private static String encounter = "      Mutated Pulse Bat   	 Bat/air, passive  	 L20   	 575/725  	 F0/D50/A100/E50		\n   Mutated Pulse Bat   	 Bat/air, passive  	 L20   	 575/725  	 F0/D50/A100/E50		\n   Mutated Pulse Bat   	 Bat/air, passive  	 L20   	 575/725  	 F0/D50/A100/E50		\n   Mutated Pulse Bat   	 Bat/air, passive  	 L20   	 575/725  	 F0/D50/A100/E50		\n   Mutated Pulse Bat   	 Bat/air, passive  	 L20   	 575/725  	 F0/D50/A100/E50		\n   Mutated Pulse Bat   	 Bat/air, passive  	 L20   	 575/725  	 F0/D50/A100/E50		\n   Mutated Pulse Bat   	 Bat/air, passive  	 L20   	 575/725  	 F0/D50/A100/E50		\n   Mutated Pulse Bat   	 Bat/air, passive  	 L20   	 575/725  	 F0/D50/A100/E50		\n   Mutated Pulse Bat   	 Bat/air, passive  	 L20   	 575/725  	 F0/D50/A100/E50		\n   Mutated Pulse Bat   	 Bat/air, passive  	 L20   	 575/725  	 F0/D50/A100/E50		\n   Mutated Pulse Bat   	 Bat/air, passive  	 L20   	 575/725  	 F0/D50/A100/E50		\n   Mutated Pulse Bat   	 Bat/air, passive  	 L20   	 575/725  	 F0/D50/A100/E50		\n   Mutated Pulse Bat   	 Bat/air, passive  	 L20   	 575/725  	 F0/D50/A100/E50		\n   Mutated Pulse Bat   	 Bat/air, passive  	 L20   	 575/725  	 F0/D50/A100/E50		\n ";
	//private static String encounter = " Mountain Priest 	Priest/forest, passive 	L20 	427/936 	F100/D45/A50/E0 		\n Imp Necrolord 	Imp/forest, passive 	L20 	446/792 	F100/D50/A50/E0 		\n Imp Necrolord 	Imp/forest, passive 	L20 	446/792 	F100/D50/A50/E0 		\n Imp Necrolord 	Imp/forest, passive 	L20 	446/792 	F100/D50/A50/E0 		\n Life Reaper 	Reaper/death, passive 	L18 	145/255 	F40/D150/A40/E40 		\n Life Reaper 	Reaper/death, passive 	L18 	145/255 	F40/D150/A40/E40 		\n Life Reaper 	Reaper/death, passive 	L18 	145/255 	F40/D150/A40/E40 		\n Life Reaper 	Reaper/death, passive 	L18 	145/255 	F40/D150/A40/E40 	";
}
