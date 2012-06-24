package be.lacerta.cq2.sim;

import java.util.Date;
import java.util.List;

import be.lacerta.cq2.sim.hbn.LastseenTopic;
import be.lacerta.cq2.sim.hbn.Post;
import be.lacerta.cq2.sim.hbn.Topic;
import be.lacerta.cq2.utils.SimConstants;

public class PubExtension extends AbstractSimExtension {
	
	private final long[] publocations = {
			SimConstants.RIGHTS_ALL, 		// 1: pub
			SimConstants.RIGHTS_ALL, 		// 2: suggestions
			SimConstants.RIGHTS_ALL, 		// 3: knowledge
			SimConstants.RIGHTS_ALL, 		// 4: votes
			SimConstants.RIGHTS_ALL, 		// 5: marketplace
			SimConstants.RIGHTS_ALL, 		// 6: tek
			SimConstants.RIGHTS_ZODONLY, 	// 7
			SimConstants.RIGHTS_SITEADMIN, 	// 8
			SimConstants.RIGHTS_ALL,  		// 9
			SimConstants.RIGHTS_TEK, 		// 10
			SimConstants.RIGHTS_NEXT_AGE  	// 11
	};
	public void run(String page) {
		try {
			int location = getInt("location");
			if (!user.hasAccess(publocations[location-1])) {
				setPath("/gfas.jsp");
				return;
			}
			String action = request.getParameter("action");
			if (action == null || action.equals("")) {
				preparePubTopics(location);
			} else if (action.equals("view")) {
				preparePubView(Topic.createFromDB(Integer.parseInt(request.getParameter("topic"))));
			} else if (action.equals("allread")) {
				List<Topic> topics = Topic.getTopics(location, getInt("offset"));
				for (Topic topic : topics) {
					new LastseenTopic(user,topic).saveOrUpdate();
				}
				preparePubTopics(location);
			} else if (action.equals("newtopic")) {
				request.setAttribute("pub_location", location);
				if (post && location > 0 && location <= publocations.length) {
					Topic topic = new Topic();
					topic.setName(request.getParameter("topic"));
					topic.setStarter(user.getId());
					topic.setLocation(location);
					topic.setLastedit(new Date());
					topic.setStarted(new Date());
					topic.save();
					
					Post p = new Post();
					p.setUser(user);
					p.setTopicID(topic.getTopicID());
					p.setPost(request.getParameter("text"));
					p.setDate(new Date());
					p.save();
		
					preparePubView(topic);
				} else {
					request.setAttribute("pub_action", "newtopic");
				}
			} else if (action.equals("newreply")) {
				Topic topic = Topic.createFromDB(Integer.parseInt(request.getParameter("topic")));
				if (post) {

					if (!topic.isClosed()) {
						Post p = new Post();
						p.setUser(user);
						p.setTopicID(topic.getTopicID());
						p.setPost(request.getParameter("text"));
						p.setDate(new Date());
						p.save();
						topic.refresh();
						topic.setLastedit(new Date());
						topic.update();
					}

					preparePubView(topic);
				} else {
					Post p = null;
					if (request.getParameter("post") != null) {
						p = (Post)Post.get(Post.class, getInt("post"));
						int plocation = Topic.createFromDB(p.getTopicID()).getLocation();
						if (plocation != location) p=null;
						//else p.setUser((User)User.get(User.class, p.getUserid()));
					}
					preparePubView(topic);
					request.setAttribute("pub_post", p);
					request.setAttribute("pub_action", "newreply");
				}
			} else if (action.equals("edit")) {
				Post p = (Post)Post.get(Post.class, getInt("post"));
				if (p.getUser().equals(user) || user.hasAccess(SimConstants.RIGHTS_SUPERADMIN)) {
				  if (post) {
					p.setPost(request.getParameter("text"));
					p.setEdit(new Date());
					p.setEditflag(1);
					p.update();
					preparePubView(Topic.createFromDB(Integer.parseInt(request.getParameter("topic"))));
				  } else {
					p.setUser(user);
					preparePubView(Topic.createFromDB(Integer.parseInt(request.getParameter("topic"))));
					request.setAttribute("pub_post", p);
					request.setAttribute("pub_action", "edit");
				  }
				} else preparePubView(Topic.createFromDB(Integer.parseInt(request.getParameter("topic"))));
			} else if (action.equals("delete") && user.hasAccess(SimConstants.RIGHTS_PUBMOD)) {
				int topicid = Integer.parseInt(request.getParameter("topic"));
				if (request.getParameter("post")==null){
					Topic topic = Topic.createFromDB(topicid);
					topic.delete(true);
					preparePubTopics(Integer.parseInt(request.getParameter("location")));
				} else {
					Post p = (Post)Post.get(Post.class, getInt("post"));
					p.delete();
					request.setAttribute("pub_action", "view");
					preparePubView(Topic.createFromDB(topicid));
				}
			} else if (action.equals("switchsticky") && user.hasAccess(SimConstants.RIGHTS_PUBMOD)) {
				Topic topic = Topic.createFromDB(Integer.parseInt(request.getParameter("topic")));
				if (topic.isSticky()) topic.setSticky(false);
				else topic.setSticky(true);
				topic.update();
				preparePubView(topic);
			} else if (action.equals("switchclose") && user.hasAccess(SimConstants.RIGHTS_PUBMOD)) {
				Topic topic = Topic.createFromDB(Integer.parseInt(request.getParameter("topic")));
				if (topic.isClosed()) topic.setClosed(false);
				else topic.setClosed(true);
				topic.update();
				preparePubView(topic);
			} else {
				preparePubTopics(Integer.parseInt(request.getParameter("location")));
			}
			setPath("/pub.jsp");
		} catch (NumberFormatException nfe) {
			setPath("/main.jsp");
		} catch (ArrayIndexOutOfBoundsException nfe) {
			setPath("/main.jsp");
		}	
	}

	private void preparePubView(Topic topic) {
		int offset = 0;
		if (request.getParameter("offset") != null) {
			offset = Integer.parseInt(request.getParameter("offset"));
		}
		new LastseenTopic(user,topic).saveOrUpdate();
		request.setAttribute("pub_location", Integer.parseInt(request.getParameter("location")));
		request.setAttribute("pub_topic", topic);
		request.setAttribute("pub_offset", offset);
		request.setAttribute("pub_posts", Post.getPosts(topic.getTopicID(),offset));
		request.setAttribute("pub_action", "view");
	}
	
	private void preparePubTopics(int location) {
		List<Topic> topics = Topic.getTopics(location,getInt("offset"));
		request.setAttribute("pub_location", location);
		request.setAttribute("pub_topics", topics);
		request.setAttribute("pub_action", "");
	}

}
