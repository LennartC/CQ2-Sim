package be.lacerta.cq2.sim;

import java.util.Date;

import be.lacerta.cq2.sim.hbn.Support;
import be.lacerta.cq2.sim.hbn.User;
import be.lacerta.cq2.utils.PageParser;
import be.lacerta.cq2.utils.SimConstants;

public class SupportExtension extends AbstractSimExtension {

	public void run(String page) {
		User from = user;
		
		if (getInt("user") > 0) {
			from = (User)User.get(User.class, getInt("user"));
		}
		
		if (post) {
			if (!user.hasAccess(SimConstants.RIGHTS_SUPPORTADMIN) && getInt("user") != 0) {
				setPath("/gfas.jsp");
				return;
			}
			
			String reason = getString("reason");
			Date time = new Date();
			
			if (getInt("amount") > 0) {
				if (!Support.addDonation(time, from, getString("to"), getInt("amount"), reason)) {
					request.setAttribute("message", "There is already a donation with that timestamp!");
				}
			} else if (getString("donation").length() > 0) {
				String donation = getString("donation");
				
				int status = PageParser.parseDonation(donation, reason, from);
				if (status == PageParser.DONATION_FAILED) request.setAttribute("message", "You already have a donation with that timestamp!");
				
			}
		}
		
		if (getInt("user") > 0 && getInt("user") != user.getId()) {
			request.setAttribute("support_from", from);
		}
		
		request.setAttribute("support_donations", Support.getSupport(from));
		request.setAttribute("support_totalamount", Support.getTotals(from)[0]);

		setPath("/support.jsp");
	}
}
