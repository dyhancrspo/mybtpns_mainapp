package org.example.bankserver.application;

import org.example.bankserver.rabbitmq.ReceiverMq;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class BankMainApp {

    public static EntityManager entityManager = Persistence
            .createEntityManagerFactory("nasabah-unit")
            .createEntityManager();

    public static ReceiverMq receiveMq = new ReceiverMq(entityManager);

    public static void main(String[] args) {
        try{
            System.out.println(" [*] Waiting for messages...");
            //CRUD Nasabah
            receiveMq.addNasabah();
            receiveMq.updateNasabah();
            receiveMq.deleteNasabahById();
            receiveMq.getAllNasabah();
            receiveMq.findDataById();

            //Get Nasabah Balance
            receiveMq.getSaldoNsb();
            receiveMq.getMutasi();

            //Session
            receiveMq.loginNasabah();
            receiveMq.logoutNasabah();
        }catch (Exception e){
            System.out.println("ERROR! on DatabaseMqMain : " + e);
        }
    }

}
