package be.lacerta.cq2.sim;

import java.util.Date;

import be.lacerta.cq2.sim.hbn.Like;
import be.lacerta.cq2.sim.hbn.News;
import be.lacerta.cq2.sim.hbn.Note;
import be.lacerta.cq2.sim.hbn.RaidResult;
import be.lacerta.cq2.sim.hbn.RaidResultNote;
import be.lacerta.cq2.sim.hbn.User;
import be.lacerta.cq2.utils.PageParser;

public class RaidExtension extends AbstractSimExtension {

	public void run(String page) {
		
		String action = getString("action");
		
		if (post) {
			
			if (action.equals("addraid")) {
				RaidResult rr = PageParser.parseRaid(getString("text"), user);
				
				if (rr == null) {
					request.setAttribute("raid_message", "Unable to add raid. Could not find general information.");
				} else {
					
					News n = new News();
					
					if (rr.isIncoming()) {
						if (rr.getTotalpb() != null) {
							n.setTitle(user.getUsername()+" got raided by "+rr.getMage()+" and lost "+rr.getTotalpb()+"% power balance");
						} else if (rr.getTotalgem() != null && !rr.getTotalgem().equals("")){
							n.setTitle(user.getUsername()+" got raided by "+rr.getMage()+" and lost a "+ rr.getTotalgem() +" Gem");
						} else if (rr.getTotalworkers() != null){
							n.setTitle(user.getUsername()+" got raided by "+rr.getMage()+" and lost "+ rr.getTotalworkers() +" workers");
						} else {
							n.setTitle(user.getUsername()+" got raided by "+rr.getMage()+" and lost "+rr.getTotalres()+" res and "+rr.getTotalpwr()+" power");
						}
					} else {
						if (rr.getTotalpb() != null) {
							n.setTitle(user.getUsername()+" raided "+rr.getMage()+" and won "+rr.getTotalpb()+"% power balance");
						} else if (rr.getTotalgem() != null && !rr.getTotalgem().equals("")){
							n.setTitle(user.getUsername()+" raided "+rr.getMage()+" and won a "+rr.getTotalgem()+" Gem");
						} else if (rr.getTotalworkers() != null){
							n.setTitle(user.getUsername()+" raided "+rr.getMage()+" and killed "+ rr.getTotalworkers() +" workers");
						} else {
							n.setTitle(user.getUsername()+" raided "+rr.getMage()+" and won "+rr.getTotalres()+" res and "+rr.getTotalpwr()+" power");
						}
					};
					
					n.setNewsfor("Raid");
					n.setTime(new Date());
					n.setDirectlink("?page=mage&mage="+rr.getMage());
					n.setUser(user);
					n.save();
				
					request.setAttribute("raid_message", "Raid added!");
				}
			} else if (action.equals("addnote")) {
				if (getInt("id") > 0) {
					addNote(user, getInt("id"),getString("note"));
				}
			}
		}
		
		if (action.equals("like")) {
			doLike(user, getInt("id"));
		}
		

		//request.setAttribute("raid.total", RaidResult.getTotalResults(getInt("type")));
		request.setAttribute("raid_raids", RaidResult.getRaidResults(14, getInt("type")));
		setPath("/raids.jsp");
	}

	public static void addNote(User user, int raidid, String note) {
		RaidResult r = (RaidResult)RaidResult.get(RaidResult.class, raidid);
		
		Note n = new Note();
		n.setDate(new Date());
		n.setUser(user);
		n.setNote(note);
		n.save();
		
		RaidResultNote rn = new RaidResultNote();
		rn.setRaidresult(r);
		rn.setNote(n);
		
		r.addNote(rn);
		r.saveOrUpdate();
	}
	
	public static void doLike(User user, int raidid) {
		Like l = Like.load(user, raidid, Like.TYPE_RAIDRESULT);
		
		if (l!=null) l.delete();
		else {
			l = new Like(user, raidid, Like.TYPE_RAIDRESULT);
			l.save();
		}
	}
}
