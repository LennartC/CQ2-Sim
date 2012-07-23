package be.lacerta.cq2.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import be.lacerta.cq2.objects.Creature;
import be.lacerta.cq2.sim.hbn.User;

public class SessionListener implements javax.servlet.http.HttpSessionListener {

	private static List<HttpSession> sessionList = new Vector<HttpSession>();
	private static List<Creature> critList = null;

	/**
	 * The method is called by the servlet container just after http session is
	 * created. The implementation does nothing.
	 *
	 * @param <b>event</b> HttpSessionEvent
	 */
	public void sessionCreated(HttpSessionEvent event) {
		SessionListener.addSession(event.getSession());
	}
	/**

	 * The method is called by the servlet container just before http session is
	 * destroyed. The implementation removes any references of ShoppingCart bean
	 *
	 * @param <b>event</b> HttpSessionEvent
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		SessionListener.removeSession(event.getSession());
	}

	public static void addSession(HttpSession session) {
		sessionList.add(session);
	}

	public static void removeSession(HttpSession session) {
		sessionList.remove(session);
	}

	public static List<HttpSession> getSessionList() {
		return sessionList;
	}

	public static List<Creature> getCreatureList() {
		if (critList != null) return critList;
		
		critList = new ArrayList<Creature>();
		List<be.lacerta.cq2.sim.hbn.Creature> dbCritList = be.lacerta.cq2.sim.hbn.Creature.getCreatures();
		for (be.lacerta.cq2.sim.hbn.Creature crit : dbCritList) {
			critList.add( 
					new Creature(
							crit.getName(),
							crit.getRace().getCreatureClass(),
							crit.getRace().getName(),
							crit.getDamage(),
							crit.getHealth(),
							crit.getSkill(),
							crit.getRace().getFD(),
							crit.getRace().getDD(),
							crit.getRace().getAD(),
							crit.getRace().getED(),
							null));
		}
		return critList;
	}
	
	public static Set<User> getOnlineUsers() {

		Set<User> userList = new TreeSet<User>(new UserComparator());
		for (HttpSession session : sessionList) {
			if (session.getAttribute("user") != null && session.getAttribute("user") instanceof User) {
				User u = (User)session.getAttribute("user");
				if (u.isOnline() && !u.isHideOnline()) {
					userList.add((User)session.getAttribute("user"));
				}
			}
		}
		return userList;
	}
	
	public static Set<User> getOnlineTravelers() {

		Set<User> userList = new TreeSet<User>(new UserComparator());
//		for (HttpSession session : sessionList) {
//			
//			int inactive = (int) (System.currentTimeMillis()-session.getLastAccessedTime());
//			if (inactive < 300000
//					&& session.getAttribute("SessionContext") != null 
//					&& session.getAttribute("SessionContext") instanceof SessionContext) {
//				
//				SessionContext sc = (SessionContext)session.getAttribute("SessionContext");
//				for (Iterator<ServiceGroupContext> it2=sc.getServiceGroupContext(); it2.hasNext();) {
//					ServiceGroupContext sgc = it2.next();
//					for (Iterator<ServiceContext> it3=sgc.getServiceContexts();it3.hasNext();) {
//						ServiceContext sc2 = it3.next();
//						if (sc2.getProperty("user") != null) {
//							userList.add((User)sc2.getProperty("user"));
//						}
//					}
//				}
//			}
//
//		}
		return userList;
	}
	
}