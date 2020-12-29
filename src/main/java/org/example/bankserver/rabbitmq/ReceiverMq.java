package org.example.bankserver.rabbitmq;

import org.example.bankserver.daos.MutasiDao;
import org.example.bankserver.entities.Mutasi;
import org.example.bankserver.entities.Nasabah;
//import org.example.database.rabbitmq.DatabaseSendMq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.example.bankserver.daos.NasabahDao;
import org.example.bankserver.service.Session;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class ReceiverMq {
     SenderMq send = new SenderMq();
     private Connection connection;
     private Channel channel;
     private EntityManager entityManager;
     private NasabahDao nasabahDao;
     private MutasiDao mutasiDao;
     private final List<Session> session = new ArrayList<>();


    public ReceiverMq(EntityManager entityManager){
        this.entityManager = entityManager;
        nasabahDao = new NasabahDao(entityManager);
//        mutasiDao = new MutasiDao(entityManager);
    }

    public void connectToRabbitMQ() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
    }

    public void connectJPANasabah(){
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

    public void connectJPAMutasi(){
            this.entityManager = Persistence
                    .createEntityManagerFactory("nasabah-unit")
                    .createEntityManager();
            mutasiDao = new MutasiDao(entityManager);
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



//---------------  Receiver Method -----------------
//------------------------------------------------------------------------------
    public void addNasabah(){
        try{
            connectToRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("createDataNasabah", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery ) -> {
                String nasabahString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + nasabahString + "'");
                connectJPANasabah();
                nasabahDao.persist(nasabahString);
                commitJPA();
            };
            channel.basicConsume("createDataNasabah", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("ERROR! on addNasabah -ReceiverMq : " + e);
        }
    }

//    Get All Nasabah Data
    public void getAllNasabah() {
        try {
            connectToRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("getAlldataNasabah", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String nasabahString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + nasabahString + "'");
                connectJPANasabah();
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
                connectJPANasabah();
                nasabahDao.update(nasabahString);
                commitJPA();
            };
            channel.basicConsume("updateDataNasabah", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("ERROR! on updateNasabah -ReceiverMq : " + e);
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
                connectJPANasabah();
                nasabahDao.remove(idNsbString);
                commitJPA();
            };
            channel.basicConsume("deleteDataNasabah", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("ERROR! on deleteNasabahById -ReceiverMq : " + e);
        }
    }

    public void loginNasabah() {
        try {
            connectToRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("doLoginNasabah", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String idNsbString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received loginNasabah : '" + idNsbString + "'");
                connectJPANasabah();
                Nasabah nasabah = new Gson().fromJson(idNsbString, Nasabah.class);
                boolean statusLogin = false;
                for(Session obj : session) {
                    if (obj.getUsernames().equalsIgnoreCase(nasabah.getUsername()) && obj.getPasswords().equalsIgnoreCase(nasabah.getPassword())) {
                        statusLogin = true;
                        break;
                    }
                }
                if(statusLogin) {
                    send.sendLogin(nasabah.getUsername() + " has already login");
                } else {
                    nasabahDao.checkPassword(idNsbString);
                    session.add(new Session(nasabah.getUsername(), nasabah.getPassword()));
                    send.sendLogin("Login Berhasil");
                }
                commitJPA();
            };
            channel.basicConsume("doLoginNasabah", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("ERROR! on loginNasabah -ReceiverMq : " + e);
        }
    }

    public void logoutNasabah(){
        try{
            connectToRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare(   "doLogoutNasabah", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery ) -> {
                String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received logoutNasabah : '" + msg + "'");
                connectJPANasabah();
                if (!session.isEmpty()) {
                    session.clear();
                    send.sendLogout("Logout success!");
                } else {
                    send.sendLogout("Logout fail! no session detected");
                }
                commitJPA();
            };
            channel.basicConsume("doLogoutNasabah", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("ERROR! on logoutNasabah -ReceiverMq : " + e);
        }
    }

    public void findDataById(){
        try{
            connectToRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("findDataNasabah", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery ) -> {
                String idNsbString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received findDataById : '" + idNsbString + "'");
                connectJPANasabah();
                try {
                    Nasabah nasabah = nasabahDao.findUser(idNsbString);
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
            System.out.println("ERROR! on findDataById -ReceiverMq : " + e);
        }
    }

    public void getSaldoNsb(){
        try{
            connectToRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("getSaldoNasabah", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery ) -> {
                String usernameNsb = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + usernameNsb + "'");
                connectJPANasabah();
                try {
                    Integer saldo = nasabahDao.getSaldo(usernameNsb);
                    Nasabah nasabah = nasabahDao.findUser(usernameNsb);
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
                        String saldoString = new Gson().toJson(saldo);
                        if(nasabahDao.isRegistered(nasabahString)){
                            send.sendSaldoData(saldoString);
                        } else {
                            send.sendSaldoData("User not found!");
                        }
                    } else {
                        send.sendSaldoData("Login is required, please Login first!");
                    }

                    send.sendToRestApi(new Gson().toJson(saldo));
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                commitJPA();
            };
            channel.basicConsume("getSaldoNasabah", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("ERROR! on getSaldo -ReceiverMq : " + e);
        }
    }



//--------------------------  Mutasi  ----------------------------------------------
    public void getMutasi(){
        try{
            connectToRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("getMutasi", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery ) -> {
                String accountnumber = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + accountnumber + "'");
                connectJPAMutasi();
                try {
                    List<Mutasi> mutasi = mutasiDao.getMutasi(accountnumber);
                    String mutasiString = new Gson().toJson(mutasi);
                    send.sendMutasiData(mutasiString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                commitJPA();
            };
            channel.basicConsume("getMutasi", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("ERROR! on getMutasi -ReceiverMq : " + e);
        }
    }


}
