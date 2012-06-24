package be.lacerta.cq2.sim;

import java.util.List;

import be.lacerta.cq2.sim.hbn.CursedMage;
import be.lacerta.cq2.sim.hbn.RaidResult;
import be.lacerta.cq2.sim.hbn.Reveal;
import be.lacerta.cq2.sim.hbn.User;
import be.lacerta.cq2.utils.CQ2Functions;

public class MageExtension extends AbstractSimExtension {

	public void run(String page) {
		String action = getString("action");
		String mage = getString("mage");
		
		if (post) {
			if (action.equals("skills")) {
				if (!mage.equals("")) {
					List<User> users = User.getUsersByMageName(mage);
					if (users != null && users.size() > 0) {
						request.setAttribute("message",mage+" is a member of our alliance. You are not allowed to update his/her skills.");
					} else {
						SkillsExtension.setSkills(mage,getString("skills"));
						request.setAttribute("message",mage+" updated.");
					}
				}
			} else if (action.equals("addnote")) {
				RaidExtension.addNote(user, getInt("id"),getString("note"));
			}
		}
		
		if (action.equals("like")) {
			RaidExtension.doLike(user, getInt("id"));
		}
		
		List<Reveal> reveals = Reveal.getAllForMage(mage);
		List<RaidResult> raidson = RaidResult.loadByMageAndType(mage, RaidResult.TYPE_OUTGOING);
		List<RaidResult> raidsby = RaidResult.loadByMageAndType(mage, RaidResult.TYPE_INCOMING);
		
		if ( (reveals == null || reveals.size() == 0) &&
			 (raidson == null || raidson.size() == 0) &&
			 (raidsby == null || raidsby.size() == 0)) {
			
			String retMage = CQ2Functions.findMage(mage);
			if (retMage != null && !retMage.toUpperCase().equals(mage.toUpperCase())) {
				request.setAttribute("message", mage+" was not found. Did you mean <a href=\"?page=mage&mage="+retMage+"\">"+retMage+"</a>?");
			}
			
		}
		
		
		request.setAttribute("mage", mage);
		request.setAttribute("mage_showreveal", getInt("showreveal"));
		request.setAttribute("mage_reveals", reveals);
		request.setAttribute("mage_curse", CursedMage.getByMageName(mage));
		request.setAttribute("mage_raidson", raidson);
		request.setAttribute("mage_raidsby", raidsby);
		setPath("/mage.jsp");
	}
}
