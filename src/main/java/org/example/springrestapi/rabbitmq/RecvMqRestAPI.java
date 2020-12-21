package org.example.springrestapi.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RecvMqRestAPI {
    protected String message;
    protected String Loginmessage;
    protected String Registermessage;
    protected String Logoutmessage;
    protected String Datamessage;



    public void receiveFromDatabase() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("messageFromDatabase", false, false, false, null);
        System.out.println(" [*] Waiting for messages from database");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            this.message = message;
        };
        channel.basicConsume("messageFromDatabase", true, deliverCallback, consumerTag -> { });

    }

    public String getMessage() {
        return message;
    }



    public void RecvLoginMsg() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("sendLogin", false, false, false, null);
        System.out.println(" [*] Waiting for messages from database");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            setLoginmessage(message);
        };
        channel.basicConsume("sendLogin", true, deliverCallback, consumerTag -> { });

    }

    public void RecvLogoutMsg() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("sendLogout", false, false, false, null);
        System.out.println(" [*] Waiting for messages from database");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            setLogoutmessage(message);
        };
        channel.basicConsume("sendLogout", true, deliverCallback, consumerTag -> { });
    }

    public void RecvDataUser() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("sendNasabahData", false, false, false, null);
        System.out.println(" [*] Waiting for messages from database");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            setDatamessage(message);
        };
        channel.basicConsume("sendNasabahData", true, deliverCallback, consumerTag -> { });
    }

    public String getLoginmessage() {
        return Loginmessage;
    }

    public void setLoginmessage(String loginmessage) {
        Loginmessage = loginmessage;
    }

    public String getRegistermessage() {
        return Registermessage;
    }

    public void setRegistermessage(String registermessage) {
        Registermessage = registermessage;
    }

    public String getLogoutmessage() {
        return Logoutmessage;
    }

    public void setLogoutmessage(String logoutmessage) {
        Logoutmessage = logoutmessage;
    }

    public String getDatamessage() {
        return Datamessage;
    }

    public void setDatamessage(String getDatamessage) {
        this.Datamessage = getDatamessage;
    }

}
