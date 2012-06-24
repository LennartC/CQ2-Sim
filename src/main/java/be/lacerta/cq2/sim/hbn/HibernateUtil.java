package be.lacerta.cq2.sim.hbn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Basic Hibernate helper class, handles SessionFactory, Session and Transaction.
 * <p>
 * Uses a static initializer for the initial SessionFactory creation
 * and holds Session and Transactions in thread local variables. All
 * exceptions are wrapped in an unchecked InfrastructureException.
 *
 * @author christian@hibernate.org
 */
public class HibernateUtil {

	private static Log log = LogFactory.getLog(HibernateUtil.class);
	private static String CONFIG_FILE_LOCATION = "hibernate.cfg.xml";
	private static Configuration configuration;
	private static final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();
	private static final ThreadLocal<Transaction> threadTransaction = new ThreadLocal<Transaction>();
	private static final ThreadLocal<Interceptor> threadInterceptor = new ThreadLocal<Interceptor>();


	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	
	// Create the initial SessionFactory from the default configuration files
	static {
		try {
			configuration = new Configuration();
			configuration.configure(HibernateUtil.class.getResource(CONFIG_FILE_LOCATION));
			serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();  
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			// We could also let Hibernate bind it to JNDI:
			// configuration.configure().buildSessionFactory()
		} catch (Throwable ex) {
			// We have to catch Throwable, otherwise we will miss
			// NoClassDefFoundError and other subclasses of Error
			log.error("Building SessionFactory failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Returns the SessionFactory used for this static class.
	 *
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		/* Instead of a static variable, use JNDI:
		SessionFactory sessions = null;
		try {
			Context ctx = new InitialContext();
			String jndiName = "java:hibernate/HibernateFactory";
			sessions = (SessionFactory)ctx.lookup(jndiName);
		} catch (NamingException ex) {
			throw new InfrastructureException(ex);
		}
		return sessions;
		*/
		return sessionFactory;
	}

	/**
	 * Returns the original Hibernate configuration.
	 *
	 * @return Configuration
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}
	
	/**
	 * Returns the original Hibernate configuration.
	 *
	 * @return Configuration
	 */
	public static ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	/**
	 * Rebuild the SessionFactory with the static Configuration.
	 *
	 */
	 public static void rebuildSessionFactory() {
		synchronized(sessionFactory) {
			sessionFactory = getConfiguration().buildSessionFactory(getServiceRegistry());
		}
	 }

	/**
	 * Rebuild the SessionFactory with the given Hibernate Configuration.
	 *
	 * @param cfg
	 */
	 public static void rebuildSessionFactory(Configuration cfg) {
		synchronized(sessionFactory) {
			serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();  
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			configuration = cfg;
		}
	 }

	/**
	 * Retrieves the current Session local to the thread.
	 * <p/>
	 * If no Session is open, opens a new Session for the running thread.
	 *
	 * @return Session
	 */
	public static Session getSession() {
		Session s = (Session) threadSession.get();

		if (s == null) {
			log.debug("Opening new Session for this thread.");
			s = getSessionFactory().openSession();
			threadSession.set(s);
		}
		return s;
	}

	/**
	 * Closes the Session local to the thread.
	 */
	public static void closeSession() {
		Session s = (Session) threadSession.get();
		threadSession.set(null);
		if (s != null && s.isOpen()) {
			log.debug("Closing Session of this thread.");
			s.close();
		}
	}

	/**
	 * Start a new database transaction.
	 */
	public static void beginTransaction() {
		Transaction tx = (Transaction) threadTransaction.get();

		if (tx == null) {
			log.debug("Starting new database transaction in this thread.");
			tx = getSession().beginTransaction();
			threadTransaction.set(tx);
		}
	}

	/**
	 * Commit the database transaction.
	 */
	public static void commitTransaction()  {
		Transaction tx = (Transaction) threadTransaction.get();
		try {
			if ( tx != null && !tx.wasCommitted()
							&& !tx.wasRolledBack() ) {
				log.debug("Committing database transaction of this thread.");
				tx.commit();
			}
			threadTransaction.set(null);
		} catch (HibernateException ex) {
			rollbackTransaction();
			throw ex;
		}
	}

	/**
	 * Commit the database transaction.
	 */
	public static void rollbackTransaction() {
		Transaction tx = (Transaction) threadTransaction.get();
		try {
			threadTransaction.set(null);
			if ( tx != null && !tx.wasCommitted() && !tx.wasRolledBack() ) {
				log.debug("Tyring to rollback database transaction of this thread.");
				tx.rollback();
			}
		} catch (HibernateException ex) {
			throw ex;
		} finally {
			closeSession();
		}
	}

	/**
	 * Disconnect and return Session from current Thread.
	 *
	 * @return Session the disconnected Session
	 */
	public static Session disconnectSession()  {

		Session session = getSession();
		
		threadSession.set(null);
		if (session.isConnected() && session.isOpen())
			session.disconnect();

		return session;
	}

}

