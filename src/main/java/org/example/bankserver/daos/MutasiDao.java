package org.example.bankserver.daos;

import org.example.bankserver.entities.Mutasi;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class MutasiDao {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public MutasiDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = entityManager.getTransaction();
    }


    public List<Mutasi> getMutasi(String accountnumber) {
        try {
            String select = "SELECT a FROM Mutasi a WHERE accountnumber=:accountnumber";
            Query query = entityManager.createQuery(select, org.example.bankserver.entities.Mutasi.class);
            query.setParameter("accountnumber", accountnumber);
            System.out.println("debug : "+ (List<Mutasi>)query.getResultList());
            return (List<Mutasi>) query.getResultList();
        } catch (NoResultException e){
            return null;
        }
    }
}
