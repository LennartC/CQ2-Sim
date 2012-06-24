package be.lacerta.cq2.sim;

import be.lacerta.cq2.travelcalc.Battle;

public class TravelCalcExtension extends AbstractSimExtension {

	public void run(String page) {
		request.setAttribute("travel_eigen", "");
		if (post) {
			Battle battle = new Battle(getString("critlist"),getString("encounter"));
			String result = battle.calc();
			request.setAttribute("travel_eigen", getString("critlist"));
			request.setAttribute("travel_solution", result);
		}
		
		setPath("/travelcalc.jsp");

	}

}
