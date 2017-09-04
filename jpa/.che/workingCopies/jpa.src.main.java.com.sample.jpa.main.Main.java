package com.sample.jpa.main;

import javax.persistence.EntityManager;
import org.sample.jpa.entity.Address;
import com.sample.jpa.service.PersistenceManager;

public class Main {
    
    public static void main(String[] args) {
        Address address = new Address()
            .setCity("BLR")
            .setCountry("IND")
            .setProvince("BLR")
            .setPostcode("560001")
            .setStreet("M. G. Road");
        
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        em.getTransaction().begin();
        em.persist(address);
        em.getTransaction().commit();
        em.close();
        PersistenceManager.INSTANCE.close();
    }
}
