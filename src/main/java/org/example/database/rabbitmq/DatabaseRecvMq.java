package org.example.database.rabbitmq;

import org.example.database.entities.Nasabah;
//import org.example.database.rabbitmq.DatabaseSendMq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.example.database.daos.NasabahDao;
import org.example.database.service.Session;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class DatabaseRecvMq {
     DatabaseSendMq send = new DatabaseSendMq();
     private Connection connection;
     private Channel channel;
     private EntityManager entityManager;
     private NasabahDao nasabahDao;
     private final List<Session> session = new ArrayList<>();


    public DatabaseRecvMq (EntityManager entityManager){
        this.entityManager = entityManager;
        nasabahDao = new NasabahDao(entityManager);
    }

    public void connectToRabbitMQ() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
    }

    public void connectJPA(){
        this.entityManager = Persistence
                .createEntityManagerFactory("nasabah-unit")
                .createEntityManager();
        nasabahDao = new NasabahDao(entityManager);
        try {
            entityManager.getTransaction().begin();
        } catch (IllegalStateException e) {
            entityManager.getTransaction().rollback();
        }
    }

    public void commitJPA(){
        try {
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (IllegalStateException e) {
            entityManager.getTransaction().rollback();
        }
    }

    public void addNasabah(){
        try{
            connectToRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("createDataNasabah", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery ) -> {
                String nasabahString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + nasabahString + "'");
                connectJPA();
                nasabahDao.persist(nasabahString);
                commitJPA();
            };
            channel.basicConsume("createDataNasabah", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("ERROR! on addNasabah -dbrmq : " + e);
        }
    }

    public void getAllNasabah() {
        try {
            connectToRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("getAlldataNasabah", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String nasabahString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + nasabahString + "'");
                connectJPA();
                try {
                    List<Nasabah> listNasabah= nasabahDao.getAllNsb();
                    send.sendToRestApi(new Gson().toJson(listNasabah));
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                commitJPA();
            };
            channel.basicConsume("getAlldataNasabah", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error getAlldataNasabah = " + e);
        }
    }

    public void updateNasabah(){
        try{
            connectToRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("updateDataNasabah", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery ) -> {
                String nasabahString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + nasabahString + "'");
                connectJPA();
                nasabahDao.update(nasabahString);
                commitJPA();
            };
            channel.basicConsume("updateDataNasabah", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("ERROR! on updateNasabah -dbrmq : " + e);
        }
    }

    public void findDataById(){
        try{
            connectToRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("findDataNasabah", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery ) -> {
                String idNsbString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + idNsbString + "'");
                connectJPA();
                try {
                    Nasabah nasabah = nasabahDao.findId(idNsbString);
//                    Nasabah nasabah = nasabahDao.find(idNsbString);
                    boolean statusLogin = false;
                    for (Session obj: session){
                        if (nasabah != null){
                            if (obj.getUsernames().equalsIgnoreCase(nasabah.getUsername()) && obj.getPasswords().equalsIgnoreCase(nasabah.getPassword())){
                                statusLogin = true;
                                break;
                            }
                        }
                    }  if (statusLogin) {
                        String nasabahString = new Gson().toJson(nasabah);
                        if(nasabahDao.isRegistered(nasabahString)){
                            send.sendNasabahData(nasabahString);
                        } else {
                            send.sendNasabahData("User not found!");
                        }
                    } else {
                        send.sendNasabahData("Login is required, please Login first!");
                    }


                    send.sendToRestApi(new Gson().toJson(nasabah));
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                commitJPA();
            };
            channel.basicConsume("findDataNasabah", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("ERROR! on findDataById -dbrmq : " + e);
        }
    }

    public void deleteNasabahById(){
        try{
            connectToRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("deleteDataNasabah", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery ) -> {
                String idNsbString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + idNsbString + "'");
                connectJPA();
                nasabahDao.remove(idNsbString);
                commitJPA();
            };
            channel.basicConsume("deleteDataNasabah", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("ERROR! on deleteNasabahById -dbrmq : " + e);
        }
    }

//    public void loginNasabah() {
//        try {
//            connectToRabbitMQ();
//            channel = connection.createChannel();
//            channel.queueDeclare("doLoginNasabah", false, false, false, null);
//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                String idNsbString = new String(delivery.getBody(), StandardCharsets.UTF_8);
////                String MyNsb = new String(delivery.getBody(), StandardCharsets.UTF_8);
//                System.out.println(" [x] Received '" + idNsbString + "'");
//                connectJPA();
//                Nasabah myNsb = new Gson().fromJson(idNsbString, Nasabah.class);
//                nasabahDao.doLogin(idNsbString);
////                nasabahDao.doLogin(MyNsb, idNsbString);
//                commitJPA();
//            };
//            channel.basicConsume("doLoginNasabah", true, deliverCallback, consumerTag -> {
//            });
//        } catch (Exception e) {
//            System.out.println("ERROR! on loginNasabah -dbrmq : " + e);
//        }
//    }
//
//
//
//
//    public void logoutNasabah(){
//        try{
//            connectToRabbitMQ();
//            channel = connection.createChannel();
//            channel.queueDeclare("doLogoutNasabah", false, false, false, null);
//            DeliverCallback deliverCallback = (consumerTag, delivery ) -> {
//                String idNsbString = new String(delivery.getBody(), StandardCharsets.UTF_8);
//                System.out.println(" [x] Received '" + idNsbString + "'");
//                connectJPA();
//                nasabahDao.doLogout(idNsbString);
//                commitJPA();
//            };
//            channel.basicConsume("doLogoutNasabah", true, deliverCallback, consumerTag -> {
//            });
//        } catch (Exception e) {
//            System.out.println("ERROR! on logoutNasabah -dbrmq : " + e);
//        }
//    }
//
    public void loginNasabah() {
        try {
            connectToRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("doLoginNasabah", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String idNsbString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + idNsbString + "'");
                connectJPA();
                Nasabah myNsb = new Gson().fromJson(idNsbString, Nasabah.class);
                boolean statusLogin = false;
                for(Session obj : session) {
                    if (obj.getUsernames().equalsIgnoreCase(myNsb.getUsername()) && obj.getPasswords().equalsIgnoreCase(myNsb.getPassword())) {
                        statusLogin = true;
//                        myNsb.setIsLogin(true);
                        break;
                    }
                }
                if(statusLogin) {
                    send.sendLogin(myNsb.getUsername() + " has already login");
                } else {
                    nasabahDao.checkPassword(idNsbString);
                    session.add(new Session(myNsb.getUsername(), myNsb.getPassword()));
                    send.sendLogin("Login Berhasil");
                }
//                nasabahDao.doLogin(idNsbString);
                commitJPA();
            };
            channel.basicConsume("doLoginNasabah", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("ERROR! on loginNasabah -dbrmq : " + e);
        }
    }




    public void logoutNasabah(){
        try{
            connectToRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare(   "doLogoutNasabah", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery ) -> {
                String idNsbString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + idNsbString + "'");
//                Nasabah myNsb = new Gson().fromJson(idNsbString, Nasabah.class);
                if (!session.isEmpty()) {
                    session.clear();
                    send.sendLogout("Logout success!");
//                    myNsb.setIsLogin(false);
                } else {
                    send.sendLogout("Logout fail! no session detected");
                }
//                nasabahDao.doLogout(idNsbString);
                commitJPA();
            };
            channel.basicConsume("doLogoutNasabah", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("ERROR! on logoutNasabah -dbrmq : " + e);
        }
    }



}
