package be.lacerta.cq2.sim;

import java.util.List;

import be.lacerta.cq2.sim.hbn.Creature;
import be.lacerta.cq2.sim.hbn.CritCurses;
import be.lacerta.cq2.sim.hbn.CursedMage;
import be.lacerta.cq2.sim.hbn.News;
import be.lacerta.cq2.utils.PageParser;

public class CurseExtension extends AbstractSimExtension {
	String action = "";
	public void run(String page) {
		action = getString("action");
		if (action.equals("poolshards")) {
			setPath("/poolshards.jsp");
		} else if (action.equals("creature")) {
			doCreatureCurse();
		} else {
			doSACurse();
		}
		
	}
	
	private void doCreatureCurse() {
		if (post) {
			if (!getString("spellbook").equals("")) {
				String reply = PageParser.parseSpellbook(getString("spellbook"), user);
				request.setAttribute("message", reply);
				request.setAttribute("curse_critcurses", CritCurses.getCritCurses("skill","creature","level"));
			} else {
				Creature crit = Creature.getCreature(getString("creature"));
				if (crit==null) {
					request.setAttribute("message", "no creature found matching \""+getString("creature")+"\"");
				} else {
					List<CritCurses> cc = CritCurses.getCursesForCreature(Creature.getCreature(getString("creature")));
					request.setAttribute("message", cc.size()+" curses found for \""+getString("creature")+"\"");
					request.setAttribute("curse_critcurses", cc);
				}
			}
		} else {
			request.setAttribute("curse_critcurses", CritCurses.getCritCurses("skill","creature","level"));
		}
		setPath("/critcurses.jsp");
	}
	
	private void doSACurse() {
		if (post) {
			List<CursedMage> curses = PageParser.parseActiveCurses(getString("spellbook"),user);
			publishSACursed(curses);
		}
		request.setAttribute("curse_activecurses", CursedMage.getActiveCurses());
		setPath("/curses.jsp");
	}
	
	protected void publishSACursed(List<CursedMage> curses) {
		if (curses != null) for (CursedMage curse : curses ) {
			News n = new News();
			n.setUser(user);
			n.setNewsfor("Curse");
			n.setTitle(curse.getMage().getName());
			n.setDirectlink("?page=curse");
			n.save();
		}
	}

}
