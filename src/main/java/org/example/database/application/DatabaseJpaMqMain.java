package org.example.database.application;

import org.example.database.rabbitmq.DatabaseRecvMq;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class DatabaseJpaMqMain {

    public static EntityManager entityManager = Persistence
            .createEntityManagerFactory("nasabah-unit")
            .createEntityManager();

    public static DatabaseRecvMq receiveMq = new DatabaseRecvMq(entityManager);

    public static void main(String[] args) {
        try{
            System.out.println(" [*] Waiting for messages...");
            //CRUD Nasabah
            receiveMq.addNasabah();
            receiveMq.updateNasabah();
            receiveMq.deleteNasabahById();
            receiveMq.getAllNasabah();
            receiveMq.findDataById();

            //Session change value of isLogin
            receiveMq.loginNasabah();
            receiveMq.logoutNasabah();
        }catch (Exception e){
            System.out.println("ERROR! on DatabaseMqMain : " + e);
        }
    }

}
