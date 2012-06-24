//package be.lacerta.cq2.soap;
//
//import be.lacerta.cq2.travelcalc.Battle;
//import be.lacerta.cq2.utils.SimConstants;
//import be.lacerta.cq2.sim.hbn.Simlog;
//import be.lacerta.cq2.sim.hbn.User;
//
//import org.apache.axis2.context.MessageContext;
//import org.apache.axis2.context.ServiceContext;
//
//public class TravelCalc {
//	
//	ServiceContext serviceContext;
//	String critlist = "";
//	User u = null;
//	int count;
//
//	public String calc(String encounter) {
//		String result = null;
//		try {
//		if (u.hasAccess(SimConstants.RIGHTS_ALL)) {
//			Battle battle = new Battle(critlist, encounter);
//			result = battle.calc();
//			count++;
//		}
//		} catch (NullPointerException npe) { }
//		
//		return result;
//	}
//
//	public boolean authenticate(String username, String password) {
//		u = User.createFromDatabase(username, password);
//		try {
//			Simlog sl = new Simlog();
//			sl.setUserid(u.getId());
//			sl.setAction("TRAVELCALCAUTH ");
//			sl.setIp((String)MessageContext.getCurrentMessageContext().getProperty("REMOTE_ADDR"));
//			sl.save();
//		} catch (Exception e) {}
//		serviceContext.setProperty("user", u);
//		return (u != null && !u.isDisabled() && !u.isPassExpired());
//	}
//
//	public void setCritlist(String critlist) {
//		this.critlist = critlist;
//		count = 0;
//	}
//	
//	public void init(ServiceContext serviceContext) {
//		this.serviceContext = serviceContext;
//		count = 0;
//	}
//
//	public void destroy(ServiceContext serviceContext) {
//
//	}
//
//}
