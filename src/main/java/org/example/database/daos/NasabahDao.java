package org.example.database.daos;

import org.example.database.entities.Nasabah;

import com.google.gson.Gson;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.List;

public class NasabahDao {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public NasabahDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = entityManager.getTransaction();
    }

    public Nasabah find(String id) {
        return entityManager.find(Nasabah.class, Long.valueOf(id));
    }

    public List<Nasabah> getAllNsb(){
        return entityManager.createQuery("SELECT a FROM Nasabah a", Nasabah.class).getResultList();
    }


    public Nasabah findByRekening(String nasabahString) {
        Nasabah nsb = new Gson().fromJson(nasabahString, Nasabah.class);
        Nasabah nasabah;
        try {
            nasabah = entityManager.createQuery("SELECT a FROM Nasabah a where accountnumber =" + nsb.getAccountnumber(), Nasabah.class).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
        System.out.println("Debugginngs  : " + nasabah);
        return nasabah;
    }


    public Nasabah findId(String idString) {
        Nasabah  nasabah;
        try {
            nasabah = entityManager.createQuery("SELECT a FROM Nasabah a where id ="+Long.valueOf(idString), Nasabah.class).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
        System.out.println("debug : "+ nasabah);
        return nasabah;
    }


    public void persist(String nasabahString){
        Nasabah nasabah = new Gson().fromJson(nasabahString, Nasabah.class);
        entityManager.persist(nasabah);
    }

    public void update(String nsbString){
        Nasabah currentNasabah = new Gson().fromJson(nsbString, Nasabah.class);
        Nasabah nextNasabah = entityManager.find(Nasabah.class, currentNasabah.getId());
        nextNasabah.setFullname(currentNasabah.getFullname());
        nextNasabah.setPhonenumber(currentNasabah.getPhonenumber());
        nextNasabah.setAddress(currentNasabah.getAddress());
        nextNasabah.setPassword(currentNasabah.getPassword());
        nextNasabah.setStatus(currentNasabah.getStatus());
        entityManager.merge(nextNasabah);
    }

    public void remove(String id) {
        Nasabah nasabah = entityManager.find(Nasabah.class, Long.valueOf(id));
        entityManager.remove(nasabah);
    }
//
//    public void doLogin(String nsbString) {
//        Nasabah currentState = new Gson().fromJson(nsbString, Nasabah.class);
//        Nasabah nextState = entityManager.findAll
//    }


//  Yang Betul
    public void doLogin(String id) {
//    public void doLogin(String myNsb, String id) {
        Nasabah nasabah = entityManager.find(Nasabah.class, Long.valueOf(id));
//        Nasabah cekNasabah = new Gson().fromJson(myNsb, Nasabah.class);
//        if(cekNasabah.getUsername().equals(nasabah.getUsername()) && cekNasabah.getPassword().equals(nasabah.getPassword())) {
            Boolean statusLogin = true;
            nasabah.setIsLogin(statusLogin);
//        }
    }

//    public void doLogin(String myNsb, String id) {
//        Nasabah nasabah = entityManager.find(Nasabah.class, Long.valueOf(id));
//        Nasabah cekNasabah = new Gson().fromJson(myNsb, Nasabah.class);
//        if(cekNasabah.getUsername().equals(nasabah.getUsername()) && cekNasabah.getPassword().equals(nasabah.getPassword())) {
//            Boolean statusLogin = true;
//            nasabah.setIsLogin(statusLogin);
//        }
//    }

//    public void doLogin(Nasabah nasabah) {
//        String select = "SELECT id FROM datanasabah WHERE username=:username and password=:password";
//        Query query = entityManager.createQuery(select);
//        query.setParameter("username", nasabah.getUsername());
//        query.setParameter("password", nasabah.getPassword());
//        Boolean statusLogin = true;
//        nasabah.setIsLogin(statusLogin);
//    }

    public void doLogout(String id) {
        Nasabah nasabah = entityManager.find(Nasabah.class, Long.valueOf(id));
        Boolean statusLogin = false;
        nasabah.setIsLogin(statusLogin);
    }

    public boolean isRegistered(String nasabahString) {
        List<Nasabah> listAllNasabah = getAllNsb();
        Nasabah nasabah = new Gson().fromJson(nasabahString, Nasabah.class);
        boolean registered = false;
        for (Nasabah obj : listAllNasabah){
            if(obj.getUsername().equalsIgnoreCase(nasabah.getUsername()) || obj.getAccountnumber().equals(nasabah.getAccountnumber())){
                registered = true;
            }
        }
        return registered;
    }

    public boolean checkPassword(String nasabahString){
        List<Nasabah> listAllUser = getAllNsb();
        Nasabah nasabah = new Gson().fromJson(nasabahString, Nasabah.class);
        boolean canLogin = false;
        for (Nasabah obj : listAllUser){
            if(obj.getUsername().equalsIgnoreCase(nasabah.getUsername())){
                if(obj.getPassword()==nasabah.getPassword()){
                    canLogin = true;
                }
            }
        }
        return canLogin;
    }

}