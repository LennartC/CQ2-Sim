package be.lacerta.cq2.sim;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.lacerta.cq2.objects.Gem;
import be.lacerta.cq2.sim.hbn.GemTracker;
import be.lacerta.cq2.sim.hbn.Mage;

public class GemExtension extends AbstractSimExtension {

	public static final int SECONDSPERRAISE = 1008;
	
	public void run(String page) {
		if (page.equals("gemtracker")) {
			doGemTracker();
		} else {
			doGem();
		}
	}
	
	private List<Gem> parseGems(String s) {
		String[] input = s.split("\n");
		List<Gem> gems = new Vector<Gem>();
		for (int i=0; i<input.length; i++) {
			try {
			gems.add(new Gem(input[i]));
			} catch (InvalidParameterException ipe) {}
		}
		return gems;
	}
	
	private void doGem() {
		request.setAttribute("gem_gems", "");
		if (post) {
			request.setAttribute("gem_gems", getString("gems"));
			List<Gem> gems = parseGems(getString("gems"));
			
			if (request.getParameter("sort") != null) Collections.sort(gems);
			if (request.getParameter("showall") != null) {
				int total = 0;
				int count = 0;
				for (Iterator<Gem> i = gems.iterator(); i.hasNext();) {
					total += i.next().getPower();
					count++;
				}
				request.setAttribute("gem_count", count);
				request.setAttribute("gem_totalpower", total);
			}
			request.setAttribute("gem_gemlist", gems);
		}
		setPath("/gem.jsp");
	}
	
	private void doGemTracker() {
		String action = getString("action");
		if (action.equals("delete") && getInt("id") > 0) {
			GemTracker.get(GemTracker.class, getInt("id")).delete();
		} else if (post) {
			Pattern p = Pattern.compile(
					"(\\S*) is aligned towards the \\S* (\\S*) sign.*currently at (\\d*)%",Pattern.DOTALL);
			Matcher m = p.matcher(getString("text"));	
			if (m.find()) {
				String mage = m.group(1);
				GemTracker gt = GemTracker.loadByMage(user, mage);
				if (gt == null) gt = new GemTracker();
				gt.setUser(user);
				gt.setMage(Mage.getOrCreateMage(m.group(1)));
				gt.setGem(m.group(2));
				gt.setPercentage(Integer.parseInt(m.group(3)));
				gt.setSubmitDate(new Date());
				gt.setExpectedEndDate(calcExpectedEndDate(gt.getSubmitDate(),gt.getPercentage()));
				gt.saveOrUpdate();
			}
		}
		
		request.setAttribute("gemtracker_list", GemTracker.getList(user));
		setPath("/gemtracker.jsp");
	}
	
	private Date calcExpectedEndDate(Date current, int perc) {
		return new Date(current.getTime()+( (100-perc)*GemExtension.SECONDSPERRAISE*1000 ));
	}
	
}
