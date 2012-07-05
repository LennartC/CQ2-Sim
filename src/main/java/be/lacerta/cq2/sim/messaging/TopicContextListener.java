package be.lacerta.cq2.sim.messaging;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;
import org.hibernate.Transaction;

import be.lacerta.cq2.sim.hbn.Configuration;
import be.lacerta.cq2.sim.hbn.HibernateUtil;

public class TopicContextListener implements ServletContextListener {
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("stopping topic listener");
		Executor.INSTANCE.getService().shutdown();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
        Transaction tx = null;
        try {
        	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        	tx = session.getTransaction();
        	tx.begin();
        	
    		if (new Boolean(Configuration.getValue(Configuration.MASTER))) {
    			Executor.INSTANCE.getService().submit(new TopicService());
    		}
    		
        	tx.commit();
        } catch (Exception e){
        	if (tx!=null) tx.rollback();
        	e.printStackTrace();
        } finally {
        	HibernateUtil.closeSession();
        }
	}


}
