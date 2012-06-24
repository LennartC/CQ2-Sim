//package be.lacerta.cq2.soap;
//
//import java.security.InvalidParameterException;
//import java.util.List;
//
//import org.apache.axis2.context.ServiceContext;
//import org.apache.axis2.context.MessageContext;
//
//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.xml.DomDriver;
//
//import be.lacerta.cq2.sim.hbn.*;
//import be.lacerta.cq2.objects.*;
//import be.lacerta.cq2.utils.*;
//
//public class IRCService {
//	ServiceContext serviceContext;
//	
//	private boolean hasAccess() {
//		MessageContext messageContext = MessageContext.getCurrentMessageContext();
//		if(messageContext != null){
//            String ipAddress = (String)messageContext.getProperty("REMOTE_ADDR");
//            return ("84.195.104.207").equals(ipAddress) || ("81.164.96.201").equals(ipAddress);
//        }
//		return false;
//	}
//	
//	
//	public int getGempower(String name) {
//		if (!hasAccess()) return 0;
//		try {
//			Gem g = new Gem(name);
//			return g.getPower();
//		} catch (InvalidParameterException ipe) {
//			return 0;
//		}
//	}
//	
//	@Deprecated
//	public String getUserinfo(String qauth) {
//		if (!hasAccess()) return "";
//		List<User> users = User.getUsersByQauth(qauth);
//		try {
//			for (User u : users) {
//				return u.getUsername() + " " + u.getMage() + " " + u.getKingdom() + " " + u.getCq2class() + " " + u.getLevel() + " " + u.getBirthdayString() + " " + u.getUlvl();
//			}
//		} catch (NumberFormatException nfe) {
//			nfe.printStackTrace();
//		}
//		return "-1";
//	}
//	
//	private String getUserXML(String qauth, String... omit) {
//		if (!hasAccess()) return "";
//		List<User> users = User.getUsersByQauth(qauth);
//		if (users != null && users.size() > 0) {
//			XStream xstream = new XStream(new DomDriver());
//			xstream.alias("user", User.class);
//			for (String field : omit) {
//				xstream.omitField(User.class, field);
//			}
//			String xml = xstream.toXML(users.get(0));
//			return xml;
//		}
//		return "";	
//	}
//	
//	public String getUserShort(String qauth) {
//		return getUserXML(qauth, "ulvl","passExpired","imagepac","phone","userOrbs","userImage");
//	}
//	
//	public String getUser(String qauth) {
//		return getUserXML(qauth);
//	}
//	
//	public boolean setTravelgain(String qauth, String gain) {
//		if (!hasAccess()) return false;
//		//System.out.println("setTravelgain for " + qauth + ": " + gain);
//		
//		for (User u : SessionListener.getOnlineUsers()) {
//			if (u.getQauth().equals(qauth)) {
//				u.setTravelgain(Integer.parseInt(gain));
//				u.update();
//				return true;
//			}
//		}
//		
//		List<User> users = User.getUsersByQauth(qauth);
//		try {
//			for (User u : users) {
//				u.setTravelgain(Integer.parseInt(gain));
//				u.update();
//				return true;
//			}	
//		} catch (NumberFormatException nfe) {
//			nfe.printStackTrace();
//		}
//		return false;
//	}
//
//		public boolean setRaidgain(String qauth, String gain) {
//			if (!hasAccess()) return false;
//                //System.out.println("setTravelgain for " + qauth + ": " + gain);
//			
//			for (User u : SessionListener.getOnlineUsers()) {
//				if (u.getQauth().equals(qauth)) {
//					u.setRaidgain(Integer.parseInt(gain));
//					u.update();
//					return true;
//				}
//			}
//            
//			List<User> users = User.getUsersByQauth(qauth);
//            try {
//            	for (User u : users) {
//            		u.setRaidgain(Integer.parseInt(gain));
//            		u.update();
//                    return true;
//            	}
//            } catch (NumberFormatException nfe) {
//                        nfe.printStackTrace();
//            }
//		return false;
//        }
//
//        public boolean setRaidloss(String qauth, String gain) {
//    		if (!hasAccess()) return false;
//    		
//                //System.out.println("setTravelgain for " + qauth + ": " + gain);
//        	for (User u : SessionListener.getOnlineUsers()) {
//				if (u.getQauth().equals(qauth)) {
//					u.setRaidloss(Integer.parseInt(gain));
//					u.update();
//					return true;
//				}
//			}
//                List<User> users = User.getUsersByQauth(qauth);
//                try {
//                        for (User u : users) {
//                                u.setRaidloss(Integer.parseInt(gain));
//                                u.update();
//				return true;
//                        }
//                } catch (NumberFormatException nfe) {
//                        nfe.printStackTrace();
//                }
//		return false;
//        }
//
//
//	public int getTravelgain(String qauth) {
//		if (!hasAccess()) return 0;
//		
//		List<User> users = User.getUsersByQauth(qauth);
//		try {
//			for (User u : users) {
//				return u.getTravelgain();
//			}
//		} catch (NumberFormatException nfe) {
//			nfe.printStackTrace();
//		}
//		return -1;
//	}
//
//        public int getRaidgain(String qauth) {
//    		if (!hasAccess()) return 0;
//    		
//                List<User> users = User.getUsersByQauth(qauth);
//                try {
//                        for (User u : users) {
//                                return u.getRaidgain();
//                        }
//                } catch (NumberFormatException nfe) {
//                        nfe.printStackTrace();
//                }
//                return -1;
//        }
//
//        public int getRaidloss(String qauth) {
//    		if (!hasAccess()) return 0;
//    		
//                List<User> users = User.getUsersByQauth(qauth);
//                try {
//                        for (User u : users) {
//                                return u.getRaidloss();
//                        }
//                } catch (NumberFormatException nfe) {
//                        nfe.printStackTrace();
//                }
//                return -1;
//        }
//	
//	public void init(ServiceContext serviceContext) {
//		this.serviceContext = serviceContext;
//	}
//
//	public void destroy(ServiceContext serviceContext) {
//
//	}
//}
