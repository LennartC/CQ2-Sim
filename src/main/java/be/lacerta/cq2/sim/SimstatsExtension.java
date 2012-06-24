package be.lacerta.cq2.sim;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;

import be.lacerta.cq2.objects.Gem;
import be.lacerta.cq2.sim.hbn.HibernateUtil;
import be.lacerta.cq2.sim.hbn.User;
import be.lacerta.cq2.sim.hbn.XPLog;
import be.lacerta.cq2.utils.StringUtils;

public class SimstatsExtension extends AbstractSimExtension {
	Session session;
	public void run(String page) {
		List<Object[]> powerSpent = new ArrayList<Object[]>();
		List<Object[]> powerSpells = new ArrayList<Object[]>();
		List<Object[]> forumPosts = new ArrayList<Object[]>();
		List<Object[]> loggedIn = new ArrayList<Object[]>();
		List<Object[]> traveled = new ArrayList<Object[]>();
		List<Object[]> mostSupport = new ArrayList<Object[]>();
		List<Object[]> leastSupport = new ArrayList<Object[]>();
		List<Object[]> revealsInserted = new ArrayList<Object[]>();
		List<Object[]> curses = new ArrayList<Object[]>();
		List<Object[]> travelrecord = new ArrayList<Object[]>();
		List<Object[]> raidrecord = new ArrayList<Object[]>();
		List<Object[]> raidloss = new ArrayList<Object[]>();
		List<Object[]> oldest = new ArrayList<Object[]>();
		List<Object[]> youngest = new ArrayList<Object[]>();
		List<Object[]> resLost = new ArrayList<Object[]>();
		List<Object[]> resWon = new ArrayList<Object[]>();
		List<Object[]> gemsWon = new ArrayList<Object[]>();
		
		Transaction tx = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.getTransaction();
				
			powerSpent  = getStats("select sum(amount), userid from PowerspellLog as pspl group by userid order by sum(amount) desc");
			powerSpells = getStats("select count(*), userid from PowerspellLog as pspl group by userid order by count(*) desc");
			forumPosts  = getStats("select count(*), user from Post as post group by userid order by count(*) desc");
			loggedIn    = getStats("select count(*), userid from Simlog as log where log.action = :parameter1 group by userid order by count(*) desc", "LOGIN");
			mostSupport = getStats("select sum(amount), fromUser from Support as s group by fromUser order by sum(amount) desc");
			leastSupport= getStats("select sum(amount), fromUser from Support as s group by fromUser order by sum(amount) asc");
			revealsInserted= getStats("select count(*), user from News as news where news.newsfor = :parameter1 group by userid order by count(*) desc", "Reveal");
			traveled    = getStats("select count(*), userid from Simlog as log where log.action = :parameter1 group by userid order by count(*) desc", "TRAVELCALCAUTH");
			curses      = getStats("select count(*), user from News as news where news.newsfor = :parameter1 group by userid order by count(*) desc", "Curse");
			travelrecord= getStats("select travelgain, user from User as user order by travelgain desc");
			raidrecord  = getStats("select raidgain, user from User as user order by raidgain desc");
			raidloss    = getStats("select raidloss, user from User as user order by raidloss desc");
			oldest		= getStats("SELECT floor((now()-birthday)/10000000000), user FROM User as user where birthday is not null and year(now()) > year(birthday) order by birthday asc");
			youngest	= getStats("SELECT floor((now()-birthday)/10000000000), user FROM User as user where birthday is not null and year(now()) > year(birthday) and floor((now()-birthday)/10000000000) > 1 order by birthday desc");
			resLost		= getStats("SELECT sum(totalres), user FROM RaidResult as rr where incoming = :parameter1 and totalres > 0 group by userid order by sum(totalres) desc", true);
			resWon		= getStats("SELECT sum(totalres), user FROM RaidResult as rr where incoming = :parameter1 and totalres > 0 group by userid order by sum(totalres) desc", false);
			gemsWon		= getStats("SELECT count(*), user FROM RaidResult as rr where incoming = :parameter1 and totalgem is not null group by userid order by count(*) desc", false);

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
		}

		request.setAttribute("stats_powerSpent", powerSpent);
		request.setAttribute("stats_powerSpells", powerSpells);
		request.setAttribute("stats_forumPosts", forumPosts);
		request.setAttribute("stats_loggedIn", loggedIn);
		request.setAttribute("stats_mostSupport", mostSupport);
		request.setAttribute("stats_leastSupport", leastSupport);
		request.setAttribute("stats_revealsInserted", revealsInserted);
		request.setAttribute("stats_traveled", traveled);
		request.setAttribute("stats_curses", curses);
		request.setAttribute("stats_travelrecord", travelrecord);
		request.setAttribute("stats_raidrecord", raidrecord);
		request.setAttribute("stats_raidloss", raidloss);
		request.setAttribute("stats_mostRecentXP", getMostRecentXP());
		request.setAttribute("stats_oldest", oldest);
		request.setAttribute("stats_youngest", youngest);
		request.setAttribute("stats_resLost", resLost);
		request.setAttribute("stats_resWon", resWon);
		request.setAttribute("stats_gemsWon", gemsWon);
		request.setAttribute("stats_gemPowerWon", getMostGemPower());
		
		setPath("/stats.jsp");
	}
	
	
	private List<Object[]> getStats(String query, Object ...parameters) {
		List<Object[]> l = new ArrayList<Object[]>();
		Query q = session.createQuery(query);
		int i=1;
		for (Object parameter : parameters) {
			q.setParameter("parameter"+(i++), parameter);
		}
		q.setMaxResults(20);
		ScrollableResults result = q.scroll();
		while (result.next()) { 
			try {
				Object[] o = new Object[2];
				o[0] = new Integer(""+result.get(0));
				
				if (result.get(1) instanceof User) {
					o[1] = StringUtils.getUserLink((User)result.get(1));
				} else {
					User u = (User)session.load(User.class,Integer.valueOf(""+result.get(1)));
					o[1] = StringUtils.getUserLink(u);
				}
				l.add(o);
			} catch (NullPointerException npe) {}
		}
		return l;
	}
	
	public static HashMap<User,Integer> getXpActivityStats() {
		HashMap<User,Integer> result = new HashMap<User,Integer>();
		for(User user : User.getUserList()) {
			result.put(user, XPLog.getAmountOfRecentXP(user.getId()));
			//System.out.println(user.getUsername()+" " + result.get(user));
		}
		return result;
	}

	private List<Object[]> getMostRecentXP() {
		List<Object[]> l = new ArrayList<Object[]>();
		
    	final HashMap<User, Integer> xp = SimstatsExtension.getXpActivityStats();
		List<User> keys = new ArrayList<User>(xp.keySet());

		Collections.sort(keys, new Comparator<User>() {
			public int compare(User left, User right) {
					Integer leftValue = (Integer) xp.get(left);
				Integer rightValue = (Integer) xp.get(right);
				return -1*leftValue.compareTo(rightValue);
			}
		});
		int i=0;
		for (User key : keys) {
				Object[] o = new Object[2];
				o[0] = xp.get(key);
				o[1] = StringUtils.getUserLink(key);
				l.add(o);
				if (++i == 20) break;
		}
		
		return l;
	}
	
	private List<Object[]> getMostGemPower() {
		List<Object[]> l = new ArrayList<Object[]>();
		
    	final HashMap<User, Integer> gemsPerUser = getGemPowerPerUser();
    	
		List<User> keys = new ArrayList<User>(gemsPerUser.keySet());

		Collections.sort(keys, new Comparator<User>() {
			public int compare(User left, User right) {
					Integer leftValue = (Integer) gemsPerUser.get(left);
				Integer rightValue = (Integer) gemsPerUser.get(right);
				return -1*leftValue.compareTo(rightValue);
			}
		});
		int i=0;
		for (User key : keys) {
				Object[] o = new Object[2];
				o[0] = gemsPerUser.get(key);
				o[1] = StringUtils.getUserLink(key);
				l.add(o);
				if (++i == 20) break;
		}
		
		return l;
	}
	
	private HashMap<User, Integer> getGemPowerPerUser() {
    	HashMap<User, Integer> gemsPerUser = new HashMap<User, Integer>();
    	
    	Query q = session.createQuery("SELECT totalgem, user FROM RaidResult as rr where incoming = :parameter1 and totalgem is not null");
    	q.setParameter("parameter1", false);
		ScrollableResults result = q.scroll();
		while (result.next()) { 
			try {
				Gem g = new Gem(""+result.get(0));
				User u = (User)result.get(1);
				
				if (gemsPerUser.containsKey(u)) {
					gemsPerUser.put(u, g.getPower()+gemsPerUser.get(u));
				} else {
					gemsPerUser.put(u, g.getPower());
				}
			} catch (NullPointerException npe) {
			} catch (InvalidParameterException ipe) {}
		}
		return gemsPerUser;
	}
}
