package be.lacerta.cq2.sim.messaging;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogsTopic {

    private static final String EXCHANGE_NAME = "CQ2_SIM";

    public static void main(String[] argv)
                  throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();

//        if (argv.length < 1){
//            System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
//            System.exit(1);
//        }

//        for(String bindingKey : argv){
//            channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
//        }
        
        channel.queueBind(queueName, EXCHANGE_NAME, "reveal.*");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            String routingKey = delivery.getEnvelope().getRoutingKey();

            System.out.println(" [x] Received '" + routingKey + "':'" + message + "'");
        }
    }
}