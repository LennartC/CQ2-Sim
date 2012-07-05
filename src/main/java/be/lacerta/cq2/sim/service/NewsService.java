package be.lacerta.cq2.sim.service;

import java.util.Date;

import be.lacerta.cq2.sim.hbn.News;
import be.lacerta.cq2.sim.hbn.User;

public enum NewsService {
    INSTANCE;
    
	public void addNews(String url, String title, String type, User user) {
		News n = new News();
		n.setTitle(title);
		n.setNewsfor(type);
		n.setTime(new Date());
		n.setDirectlink(url);
		n.setUser(user);
		n.save();
	}
	
}
