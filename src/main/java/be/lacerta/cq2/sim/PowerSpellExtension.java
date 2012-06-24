package be.lacerta.cq2.sim;

import java.util.Date;

import be.lacerta.cq2.sim.hbn.PowerspellLog;

public class PowerSpellExtension extends AbstractSimExtension {

	public void run(String page) {
		String action = getString("action");
		
		if (post) {
			PowerspellLog  p = new PowerspellLog();
			
			p.setUserid(user.getId());
			
			p.setAirSkill(getInt("air"));
			p.setForestSkill(getInt("forest"));
			p.setEarthSkill(getInt("earth"));
			p.setDeathSkill(getInt("death"));
			p.setLevel(getInt("level"));
			
			p.setAmount(getInt("amount"));
			p.setResult(getString("result"));
			
			p.setTime(new Date());
			
			p.save();
		} else if (action.equals("delete")) {
			PowerspellLog psp = (PowerspellLog)PowerspellLog.get(PowerspellLog.class, getInt("id"));
			if (psp != null && psp.getUserid() == user.getId()) {
				psp.delete();
			}
		}
		int latest = 100;
		if (getInt("all") > 0) latest = -1;
		Integer userid = getInt("user");
		if (userid==0) userid=user.getId();

		request.setAttribute("powerspelllog_userid", userid);
		request.setAttribute("powerspelllog_log", PowerspellLog.getLatest(userid, latest));
		int[] totals = PowerspellLog.getTotals(userid);
		request.setAttribute("powerspelllog_totalPower", totals[0]);
		request.setAttribute("powerspelllog_totalSpells", totals[1]);
		setPath("/powerspell.jsp");
	}

}
