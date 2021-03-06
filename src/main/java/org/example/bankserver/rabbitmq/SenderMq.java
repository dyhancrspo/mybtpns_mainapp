package org.example.bankserver.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class SenderMq {
    public void sendToRestApi(String message) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("messageFromDatabase", false, false, false, null);
            channel.basicPublish("", "messageFromDatabase", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent sendToRestApi : '" + message + "'");
        } catch (Exception e){
            System.out.println("Failed to send message to RestApi.." + e);
        }
    }


    public void sendLogin(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("sendLogin", false, false, false, null);
            channel.basicPublish("", "sendLogin", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent sendLogin : '" + message + "'");
        } catch (Exception e){
            System.out.println("Gagal mengirim pesan ke RestApi.." + e);
        }
    }

    public void sendLogout(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("sendLogout", false, false, false, null);
            channel.basicPublish("", "sendLogout", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent sendLogout : '" + message + "'");
        } catch (Exception e){
            System.out.println("Gagal mengirim pesan ke RestApi.." + e);
        }
    }

    public void sendNasabahData(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("sendNasabahData", false, false, false, null);
            channel.basicPublish("", "sendNasabahData", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent sendNasabahData : '" + message + "'");
        } catch (Exception e){
            System.out.println("Gagal mengirim pesan ke RestApi.." + e);
        }
    }

    public void sendSaldoData(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("sendSaldoData", false, false, false, null);
            channel.basicPublish("", "sendSaldoData", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent sendSaldoData'" + message + "'");
        } catch (Exception e){
            System.out.println("Error send saldo nasabah : " + e);
        }
    }

    public void sendMutasiData(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("sendMutasiData", false, false, false, null);
            channel.basicPublish("", "sendMutasiData", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent sendMutasiData '" + message + "'");
        } catch (Exception e){
            System.out.println("Error send mutasi nasabah : " + e);
        }
    }

}
