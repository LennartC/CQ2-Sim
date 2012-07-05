package be.lacerta.cq2.sim.messaging;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import be.lacerta.cq2.sim.hbn.HibernateUtil;
import be.lacerta.cq2.sim.hbn.Mage;
import be.lacerta.cq2.sim.hbn.Reveal;
import be.lacerta.cq2.sim.hbn.User;
import be.lacerta.cq2.sim.service.NewsService;
import be.lacerta.cq2.sim.service.RevealService;
import be.lacerta.cq2.utils.SimConstants;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class TopicService extends Thread {
	private Logger log = Logger.getLogger(TopicService.class);
	
    private static final String EXCHANGE_NAME = "CQ2_SIM";
    
    private Connection connection = null;
    private Channel channel = null;
    
    private int reconnect = 0;
	
    public TopicService() {
    	super();
    	this.setDaemon(true);
    }
    
    public static void sendMessage(String key, Message message) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        channel.basicPublish(EXCHANGE_NAME, key, null, new Gson().toJson(message).getBytes());
        System.out.println(" [x] Sent '" + key + "'");
        connection.close();
    }
    
	@Override
	public void run() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

		try {
			connection = factory.newConnection();
	        channel = connection.createChannel();

	        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
	        
	        bindReveal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
//		connect();
	}

	private void connect() {
		closeConnection();
		try {
			openConnection();
			bindReveal();
			reconnect = 8000;
		} catch (IOException e) {
			if (reconnect>0) reconnect*=2;
			if (reconnect > 172800000) {
				 System.out.println("unable to connect to queue for 2 days already... giving up.");
				return;
			}
			int realSleep = Math.min(reconnect, 7200000); // sleeping max 2 hours.
			try {
				 System.out.println("connection to queue lost, reconnecting in "+(realSleep/1000)+" seconds.");
				Thread.sleep(realSleep);
				connect();
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
		}
	}
	
	private void openConnection() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
	}
	
	private void closeConnection() {
		try { if (channel!=null) channel.close(); } catch (Exception ignore) {}
		try { if (connection!=null) connection.close(); } catch (Exception ignore) {}
	}
	
	private void bindReveal() throws IOException {
		String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "reveal.*");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        log.info("Listening to queue reveal.*");
        System.out.println("Listening to queue reveal.*");
        
        while (true) {
        	System.out.println("still listening");
            QueueingConsumer.Delivery delivery;
			try {
				delivery = consumer.nextDelivery();
	            String message = new String(delivery.getBody());
	            String routingKey = delivery.getEnvelope().getRoutingKey();

	            Gson gson = new Gson();
	            Transaction tx = null;
	            try {
	            	RevealMessage reveal = gson.fromJson(message, RevealMessage.class);
	            	
	            	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	            	tx = session.getTransaction();
	            	tx.begin();
	            	
	            	User user = User.createFromDatabase(routingKey.split("[.]")[1]);
	            	if (user!=null && User.hasAccess(user, SimConstants.RIGHTS_QUEUE)) {
	            		Reveal r = RevealService.INSTANCE.addReveal(
	            				user,
	            				Mage.getOrCreateMage(reveal.getMage().getName()),
	            				reveal.getMage().getCq2class(),
	            				reveal.getMage().getLevel(),
	            				reveal.getMage().getKingdom(),
	            				reveal.getMage().getForestSkill(), 
	            				reveal.getMage().getDeathSkill(),
	            				reveal.getMage().getAirSkill(),
	            				reveal.getMage().getEarthSkill(),
	            				reveal.getUnparsed()
	            		);
	            		
						if (r!=null) NewsService.INSTANCE.addNews(
								"?page=reveal&mage="+r.getMage().getName(),
								r.getMage().getName()+" (L"+r.getLevel()+" "+r.getMageClass()+" mage)",
								"Reveal",
								user
						);
						
						
	            	} else {
	            		System.out.println("user "+routingKey.split("[.]")[1]+" not found.");
	            	}
	            	
	            	tx.commit();
	            } catch (JsonSyntaxException jsonException){
	            	if (tx!=null) tx.rollback();
	            	log.info("invalid json received for reveal",jsonException);
	            	jsonException.printStackTrace();
	            } catch (ClassCastException classCastException){
	            	if (tx!=null) tx.rollback();
	            	log.info("reveal received but in wrong format",classCastException);
	            	classCastException.printStackTrace();
	            } catch (Exception simException){
	            	if (tx!=null) tx.rollback();
	            	log.info(simException);
	            	simException.printStackTrace();
	            } finally {
	            	HibernateUtil.closeSession();
	            }
	            
	            
	            System.out.println(" [x] Received '" + routingKey + "");
			} catch (ShutdownSignalException e) {
				log.error(e);
				e.printStackTrace();
			} catch (ConsumerCancelledException e) {
				log.error(e);
				e.printStackTrace();
			} catch (InterruptedException e) {
				log.error(e);
				e.printStackTrace();
			}

        }
	}
}
