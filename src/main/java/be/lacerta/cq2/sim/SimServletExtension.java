package be.lacerta.cq2.sim;

import javax.servlet.http.HttpServletRequest;

import be.lacerta.cq2.sim.hbn.User;

public interface SimServletExtension {
	
	public void setUser(User user);
	public User getUser();
	
	public HttpServletRequest getRequest();
	public void setRequest(HttpServletRequest request);
	
	public void setPost(boolean post);
	public boolean getPost();
	
	public void run(String page);

	public String getPath();
}
