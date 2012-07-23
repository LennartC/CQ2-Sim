package be.lacerta.cq2.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import be.lacerta.cq2.sim.hbn.Amulet;
import be.lacerta.cq2.sim.hbn.Creature;
import be.lacerta.cq2.sim.hbn.Item;
import be.lacerta.cq2.sim.hbn.MageSkill;
import be.lacerta.cq2.sim.hbn.Skill;
import be.lacerta.cq2.utils.CQ2Functions;
import be.lacerta.cq2.utils.CreatureFinder;
import be.lacerta.cq2.utils.StringUtils;

public class CritDBExtension extends AbstractSimExtension {

	public void run(String page) {
		String action = request.getParameter("action");
		request.setAttribute("action", action);
		request.setAttribute("result", " ");

		if (action == null || action.equals("")) {
			if (StringUtils.isInteger(request.getParameter("race"))) {
				request.setAttribute("critdb_creatures",
						Creature.getCreatures(request));
				setPath("/critdb.jsp");
			} else {
				setPath("/main.jsp");
			}
		} else if (action.equals("search"))
			doSearch();
		else if (action.equals("critcalc"))
			doCritCalc();
		else if (action.equals("critlist"))
			doCritList();
		else if (action.equals("itemlist"))
			doItemList();
		else if (action.equals("costcalc"))
			doCostCalc();
		else if (action.equals("levelcalc"))
			doMaxLevelCalc();
		else if (action.equals("summoncalc"))
			doSummonChanceCalc();
		else
			setPath("/main.jsp");

	}

	private void doCritCalc() {
		// $DY = ($_GET['DX'] * (0.8 + 0.2*$_GET['LY']))/(0.8+0.2*$_GET['LX']);
		// $HY = ($_GET['HX'] * (0.8 + 0.2*$_GET['LY']))/(0.8+0.2*$_GET['LX']);

		if (post) {
			try {
				request.setAttribute("critdb_LX", request.getParameter("LX"));
				request.setAttribute("critdb_DX", request.getParameter("DX"));
				request.setAttribute("critdb_HX", request.getParameter("HX"));
				request.setAttribute("critdb_LY", request.getParameter("LY"));

				request.setAttribute("critdb_DY",
						be.lacerta.cq2.battlecalc.objects.Creature
								.convertStrength(Integer.parseInt(request
										.getParameter("DX")), Integer
										.parseInt(request.getParameter("LX")),
										Integer.parseInt(request
												.getParameter("LY"))));

				request.setAttribute("critdb_HY",
						be.lacerta.cq2.battlecalc.objects.Creature
								.convertStrength(Integer.parseInt(request
										.getParameter("HX")), Integer
										.parseInt(request.getParameter("LX")),
										Integer.parseInt(request
												.getParameter("LY"))));
			} catch (NumberFormatException nfe) {
				request.setAttribute("critdb_DY", null);
			}
		} else {
			request.setAttribute("critdb_DX", "");
			request.setAttribute("critdb_HX", "");
			request.setAttribute("critdb_LX", "");
			request.setAttribute("critdb_LY", "");
		}
		setPath("/critcalc.jsp");
	}

	private void doCritList() {
		String[] classes = { "Air", "Death", "Earth", "Forest" };

		for (int i = 0; i < classes.length; i++) {
			List<Creature> crits = Creature.getCreatures(classes[i],
					("show").equals(request.getParameter("all")));
			request.setAttribute("critdb_" + classes[i] + "Crits", crits);
		}

		setPath("/critlist.jsp");
	}

	private void doItemList() {
		String[] classes = { "Air", "Death", "Earth", "Forest" };

		for (int i = 0; i < classes.length; i++) {
			List<Item> items = Item.getItems(classes[i],
					("show").equals(request.getParameter("all")));
			request.setAttribute("critdb_" + classes[i] + "Items", items);
		}

		setPath("/itemlist.jsp");
	}

	private void doSearch() {
		if (post) {
			request.setAttribute("critdb_creatures",
					Creature.getCreatures(request));
		}
		setPath("/critdb.jsp");
	}

	private void doCostCalc() {
		if (post) {
			// amulets
			HashMap<Amulet, Integer> amuPrices = new HashMap<Amulet, Integer>();
			int i = 0;
			while (i < 6 && !getString("ammy" + i).equals("")) {
				int amount = Math.max(1, getInt("ammyamount" + i));
				Amulet amu = CreatureFinder.findAmulet(request.getParameter(
						"ammy" + i).trim());
				if (amu != null)
					amuPrices.put(amu, amount);
				i++;
			}

			// enchants
			List<int[]> enchantPrices = new ArrayList<int[]>();
			i = 0;
			while (i < 6 && !getString("level" + i).equals("")) {
				int amount = Math.max(1, getInt("enchamount" + i));
				int level = getInt("level" + i);

				if (level > 0) {
					int[] enchant = new int[7];
					enchant[0] = getInt("enchant" + i);
					enchant[1] = level;
					enchant[2] = amount;
					enchant[3] = level * getInt("brim" + i);
					enchant[4] = level * getInt("crys" + i);
					enchant[5] = level * getInt("ess" + i);
					enchant[6] = level * getInt("gran" + i);
					enchantPrices.add(enchant);
				}
				i++;
			}

			if (amuPrices.size() > 0)
				request.setAttribute("costcalc_amuprices", amuPrices);
			if (enchantPrices.size() > 0)
				request.setAttribute("costcalc_enchantprices", enchantPrices);
		}
		request.setAttribute("costcalc_MIenchanters",
				MageSkill.loadBySkill(Skill.loadByText("Major Immunity")));
		request.setAttribute("costcalc_MBenchanters",
				MageSkill.loadBySkill(Skill.loadByText("Major Berserk")));
		request.setAttribute("costcalc_items", Item.getItems("name"));
		setPath("/costcalc.jsp");
	}

	private void doSummonChanceCalc() {
		request.setAttribute("critdb_mageSkill", "");
		request.setAttribute("critdb_critSkill", "");
		if (post) {
			int mageSkill = getInt("mage");
			int critSkill = getInt("crit");
			request.setAttribute("critdb_mageSkill", mageSkill);
			request.setAttribute("critdb_critSkill", critSkill);
			request.setAttribute("critdb_chance",
					CQ2Functions.calcSummonChance(mageSkill, critSkill));
		}
		setPath("/critcalc.jsp");
	}

	public void doMaxLevelCalc() {
		double classMultiplier;
		int userLevel = 1;
		double compareValue = userLevel * 5.5 - 60;

		Amulet a = CreatureFinder.findAmulet(request.getParameter("ammy").trim());
		
		if (a != null && a.getEss() == 0) {
			
			if (request.getParameter("class").equals("Main class")) {
				classMultiplier = 1;
			} else
				classMultiplier = 1.5;

			Creature crit = Creature.getCreature(CreatureFinder.findAmulet(
					request.getParameter("ammy").trim()).getName());

			while (crit.getSkill() * classMultiplier > compareValue) {
				userLevel++;
				compareValue = userLevel * 5.5 - 60;
			}

			request.setAttribute("result",
					"The maximum magelevel to use a(n) " + crit.getName()
							+ ", in your "
							+ request.getParameter("class").toLowerCase()
							+ ", is " + (userLevel - 1) + ".");
		} else {
			request.setAttribute("result", "Enter a correct critname please.");
		}

		setPath("/critcalc.jsp");
	}
}
