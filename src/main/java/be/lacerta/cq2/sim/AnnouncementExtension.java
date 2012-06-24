package be.lacerta.cq2.sim;

import be.lacerta.cq2.sim.hbn.Announcement;
import be.lacerta.cq2.sim.hbn.News;
import be.lacerta.cq2.utils.SimConstants;
import be.lacerta.cq2.utils.StringUtils;

public class AnnouncementExtension extends AbstractSimExtension {
	

	public void run(String page) {
		try {
			String action = request.getParameter("action");
			if (action == null || action.equals("")) {
			} else if (action.equals("new") && user.hasAccess(SimConstants.RIGHTS_SITEADMIN)) {
				if (post) {
						Announcement a = new Announcement();
						a.setUser(user.getUsername());
						a.setTitle(StringUtils.stripHTML(request.getParameter("title")));
						a.setContent(StringUtils.stripHTML(request.getParameter("text")));
						a.save();
						News n = new News();
						n.setUser(user);
						n.setNewsfor("Announcement");
						n.setTitle(a.getTitle());
						n.setDirectlink("?page=announcement&aid="+a.getAnnID());
						n.save();
				} else {
					request.setAttribute("announcement_action", "new");
				}
			} else if (action.equals("edit") && user.hasAccess(SimConstants.RIGHTS_SITEADMIN)) {
				Announcement a = (Announcement)Announcement.get(Announcement.class, getInt("aid"));
				  if (post) {
					  a.setContent(StringUtils.stripHTML(request.getParameter("text")));
					  a.update();
				  } else {
					request.setAttribute("announcement_announcement", a);
					request.setAttribute("announcement_action", "edit");
				  }
			} else if (action.equals("delete") && user.hasAccess(SimConstants.RIGHTS_SITEADMIN)) {
				if (page.equals("news")) {
					News n = (News)News.get(News.class, getInt("nid"));
					n.delete();
				} else {
					Announcement a = (Announcement)Announcement.get(Announcement.class, getInt("aid"));
					a.delete();
				}
			}
			setPath("/main.jsp");
		} catch (NumberFormatException nfe) {
			setPath("/main.jsp");
		}	
	}

}
