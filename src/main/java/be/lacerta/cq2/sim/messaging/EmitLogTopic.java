package be.lacerta.cq2.sim.messaging;

import java.util.Date;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "CQ2_SIM";

    public static void main(String[] argv)
                  throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String routingKey ="reveal.wcs_sim";

        RevealMessage reveal = new RevealMessage();
        reveal.setUnparsed("test");
        MageMessage mage = new MageMessage();
        mage.setName("test");
        mage.setCq2class("Forest");
        mage.setLevel(32);
        mage.setForestSkill(120);
        mage.setDeathSkill(100);
        mage.setAirSkill(100);
        mage.setEarthSkill(100);
        mage.setKingdom("test");
        reveal.setMage(mage);
        reveal.setDate(new Date());

        Gson gson = new Gson();
        
        String message = gson.toJson(reveal);
        
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
        System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

        RevealMessage reveal2 = gson.fromJson(message, RevealMessage.class);
        
        System.out.println(reveal2.getUnparsed());
        connection.close();
    }
    //...
}