package org.example.springrestapi.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class SendMqRestAPI {

    // Show All Data Nasabah
    public static void getAll() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        String nasabahString = "Requesting All Data";
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("getAlldataNasabah", false, false, false, null);
            channel.basicPublish("", "getAlldataNasabah", null, nasabahString.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent getAll : '" + nasabahString + "'");
        }
    }

    // Create Data Nasabah
    public static void addNasabah(String nasabahString) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("createDataNasabah", false, false, false, null);
            channel.basicPublish("", "createDataNasabah", null, nasabahString.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent addNasabah : '" + nasabahString + "'");
        }
    }

    // Delete Data Nasabah by ID
    public static void deleteNasabahById(String idString) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("deleteDataNasabah", false, false, false, null);
            channel.basicPublish("", "deleteDataNasabah", null, idString.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent deleteNasabahById : '" + idString + "'");
        }
    }

    // Find Data Nasabah by ID
    public static void findDataById(String nasabahString) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("findDataNasabah", false, false, false, null);
            channel.basicPublish("", "findDataNasabah", null, nasabahString.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent findDataById : '" + nasabahString + "'");
        }
    }

    //Update Data Nasabah
    public static void updateNasabah(String nasabahString) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("updateDataNasabah", false, false, false, null);
            channel.basicPublish("", "updateDataNasabah", null, nasabahString.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent updateNasabah : '" + nasabahString + "'");
        }
    }

    //Change Status Login Nasabah
    public static void loginNasabah(String nasabahString) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("doLoginNasabah", false, false, false, null);
            channel.basicPublish("", "doLoginNasabah", null, nasabahString.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent loginNasabah :'" + nasabahString + "'");
        }
    }

    public static void logoutNasabah() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            String strings ="Request Logout";
            channel.queueDeclare("doLogoutNasabah", false, false, false, null);
            channel.basicPublish("", "doLogoutNasabah", null, strings.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent logoutNasabah : '" + strings + "'");
        }
    }
}
