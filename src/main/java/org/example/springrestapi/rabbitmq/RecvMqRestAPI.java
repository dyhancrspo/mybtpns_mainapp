package org.example.springrestapi.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
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
            System.out.println(" [x] Received receiveFromDatabase : '" + message + "'");
            this.message = message;
        };
        channel.basicConsume("messageFromDatabase", true, deliverCallback, consumerTag -> { });

    }

    public String getMessage() {
        return message;
    }



//    public void RecvLoginMsg() throws IOException, TimeoutException {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//        channel.queueDeclare("sendLogin", false, false, false, null);
//        System.out.println(" [*] Waiting for messages from database");
//
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//            System.out.println(" [x] Received RecvLoginMsg : '" + message + "'");
//            setLoginmessage(message);
//        };
//        channel.basicConsume("sendLogin", true, deliverCallback, consumerTag -> { });
//
//    }

    public String RecvLoginMsg() throws IOException, TimeoutException {
        String loginResponse = "";
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                this.message = message;
            };
            channel.basicConsume("sendLogin", true, deliverCallback, consumerTag -> {
            });
            TimeUnit.SECONDS.sleep(1);
            if (!this.message.equals("")) {
                JSONObject object = new JSONObject();
//              object.put("id", message);
                object.put("response", 200);
                object.put("status", "Success");
                object.put("message", "Success Login");
                loginResponse = object.toJSONString();
            } else {
                JSONObject object = new JSONObject();
                object.put("response", 400);
                object.put("status", "Error");
                object.put("message", "Error Login");
                loginResponse = object.toJSONString();
            }
        } catch (Exception e) {
            System.out.println("Exception login Res: " + e);
        }
        return loginResponse;
    }

//    public void RecvLogoutMsg() throws IOException, TimeoutException {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//        channel.queueDeclare("sendLogout", false, false, false, null);
//        System.out.println(" [*] Waiting for messages from database");
//
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//            System.out.println(" [x] Received RecvLogoutMsg : '" + message + "'");
//            setLogoutmessage(message);
//        };
//        channel.basicConsume("sendLogout", true, deliverCallback, consumerTag -> { });
//    }

    public String RecvLogoutMsg() throws IOException, TimeoutException {
        String logoutResponse = "";
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare("sendLogout", false, false, false, null);
            System.out.println(" [*] Waiting for messages from database");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
                this.message = message;
            };
            channel.basicConsume("sendLogout", true, deliverCallback, consumerTag -> { });
            TimeUnit.SECONDS.sleep(1);
            if (!this.message.equals("0")) {
                JSONObject object = new JSONObject();
                object.put("response", 200);
                object.put("status", "Success");
                object.put("message", "Success Logout");
                logoutResponse = object.toJSONString();
            } else {
                JSONObject object = new JSONObject();
                object.put("response", 400);
                object.put("status", "Error");
                object.put("message", "Error Logout");
                logoutResponse = object.toJSONString();
            }
        } catch (Exception e) {
            System.out.println("Exception logout: " + e);
        }
        return logoutResponse;
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
            System.out.println(" [x] Received RecvDataUserMsg : '" + message + "'");
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
