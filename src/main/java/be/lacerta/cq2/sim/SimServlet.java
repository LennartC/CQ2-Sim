package be.lacerta.cq2.sim;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import be.lacerta.cq2.sim.hbn.HibernateUtil;
import be.lacerta.cq2.sim.hbn.Simlog;
import be.lacerta.cq2.sim.hbn.User;
import be.lacerta.cq2.utils.SimConstants;

public class SimServlet extends HttpServlet {

	private static final long serialVersionUID = -2041703667565454442L;
	private ServletConfig config;
//	String page;
//	String action;
//	User user;
//	HttpServletRequest request;
//	HttpServletResponse response;
//	HttpSession session;
	

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.config = config;
   }
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doAll(req,resp,false);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doAll(req,resp,true);
	}
	
	private void doAll(HttpServletRequest request, HttpServletResponse response, boolean post) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		
		request.setAttribute("start", System.currentTimeMillis());
		
		String page = request.getParameter("page");
		
		User user = getSessionUser(request);
		
		if (user == null) {
			if (("login").equals(request.getParameter("action"))) {
			    page = "";
			} else {
				session.invalidate();
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				return;
			}
		}

		SimServletExtension ext = null;
		
		if (user != null && user.isPassExpired()) {
			page="options";
		}
		
		if (page == null || page.equals("")) {
		} else {
			try {
				ext = (SimServletExtension)Class.forName(config.getInitParameter("page."+page)).getConstructor().newInstance();
			} catch (Exception e) {
				System.err.println("Class loading failed: "+page);
			}
		}
		
		if (ext != null) {
			ext.setRequest(request);
			ext.setUser(user);
			ext.setPost(post);
			ext.run(page);
			String path = ext.getPath();
			try {
				request.getRequestDispatcher(path).forward(request,response);
			} catch (NullPointerException npe) {
				System.err.println("+++++++++++++++++++ INFAMOUS NULLPOINTEREXCEPTION +++++++++++++++++++");
				request.getRequestDispatcher(ext.getPath()).forward(request,response);
			}
		} else if (page != null && page.equals("source") 
				&& user.hasAccess(SimConstants.RIGHTS_DEVELOPER)) {
			String path = "/lala/zod.war";
			if (("bc").equals(request.getParameter("f"))) {
				path = "/lala/battlecalc3.jar";
			}
			request.getRequestDispatcher(path).forward(request,response);
		} else {
			String path = doNoPage(request);
			request.getRequestDispatcher(path).forward(request,response);
		}
	}
	
	private User getSessionUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		
		if (session.getAttribute("user") == null || (action != null && action.equals("login"))) {
			return null;
		}
		try {
			Session hbnsession = HibernateUtil.getSessionFactory().getCurrentSession();
			User user = (User)hbnsession.merge((User)session.getAttribute("user"));
			session.setAttribute("user",user);
			
			String ipAddress = request.getRemoteAddr();
			if (ipAddress != null && !ipAddress.equals(user.getIpAddress())) {
				// extra check for vicca and Xsion
				if ( !((user.getId() == 41 || user.getId() == 35) && ipAddress.matches("193[.]178[.]209[.].*")) ) {
					return null;
				}
			}
			
			long previous = user.getLastseen().getTime();
			if (System.currentTimeMillis() > previous+(60*1000)) {
				user.refresh();
				user.setLastseen(new Date());
				user.update();
			}
			if (user.hasAccess(SimConstants.RIGHTS_ALL)) return user;
		} catch (NullPointerException npe) {
			session.invalidate();
		}
		return null;
	}

	private String doNoPage(HttpServletRequest request) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if (action != null && action.equals("login")) {
		    return doLogin(request);
		} else {
			if (action != null && action.equals("logout")) {
				request.getSession().invalidate();
				return "/index.jsp";
			} else if (request.getSession().getAttribute("user") != null && ((User)request.getSession().getAttribute("user")).getUlvl()>0) {
				return "/main.jsp";
			} else {
				request.getSession().invalidate();
				return "/index.jsp";
			}
		}
	}
	
	private String doLogin(HttpServletRequest request) throws ServletException, IOException {
		User u = null;
		if ( !(request.getParameter("username") == null || request.getParameter("username").equals("")) &&
				!(request.getParameter("password") == null || request.getParameter("password").equals("")) )
		{
			u = User.createFromDatabase(request.getParameter("username"),request.getParameter("password"));
		}
		
		if (u != null && !u.isSystemUser()) {
			u.setIpAddress(request.getRemoteAddr());
    		u.setLastseen(new Date());
    		u.update();
    		
    		Session hbnsession = HibernateUtil.getSessionFactory().getCurrentSession();
    		hbnsession.getTransaction();
    		hbnsession.flush();
    		Simlog sl = new Simlog();
    		sl.setUserid(u.getId());
    		sl.setAction("LOGIN");
    		sl.setIp(request.getRemoteAddr());
    		hbnsession.save(sl);
    		request.getSession().setAttribute("user", u);
    		
    		if (u.isPassExpired()) {
    			request.setAttribute("message", "Your password is expired. Please set a new password!");
    			request.setAttribute("options_user", u);
    			return "/options.jsp";
    		} else {
        		return "/main.jsp";
    		}
    		
    		
		} else {
			request.getSession().invalidate();
			request.setAttribute("message", "login failed");
			return "/index.jsp";	
		}
	}
	
}
