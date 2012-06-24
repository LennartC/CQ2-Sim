package be.lacerta.cq2.sim;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import be.lacerta.cq2.sim.hbn.User;

public abstract class AbstractSimExtension implements SimServletExtension {
	String path = null;
	boolean post = false;
	HttpServletRequest request = null;
	User user = null;

	public String getPath() {
		return path==null?"/index.jsp":path;
	}

	public boolean getPost() {
		return post;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public User getUser() {
		return user;
	}
	
	protected void setPath(String path) {
		this.path = path;
	}
	
	public void setPost(boolean post) {
		this.post = post;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	protected int getInt(String param) {
		try {
			return Integer.parseInt(request.getParameter(param));
		} catch (NullPointerException npe) {
		} catch (NumberFormatException nfe) {
		}
		return 0;
	}
	
	protected String getString(String param) {
		try {
			if (request.getParameter(param) == null) return "";
			return request.getParameter(param);
		} catch (NullPointerException npe) {
			return "";
		}
	}

	protected Date getDate(String date) {
		try {
			return new SimpleDateFormat("dd MMM yyyy").parse(getString(date));
		} catch (ParseException e) {
			return new Date();

		} catch (NullPointerException npe) {
			return new Date();
		}
	}
}
