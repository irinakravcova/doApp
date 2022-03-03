package com.doapp.doApp.service;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class ListService {
    @PersistenceContext
    EntityManager em;

    public void listTasksForUser(String user) {
        int userId = 1; // convert user to userId
        Query qry = em.createQuery("SELECT t FROM Task t WHERE t.userId = :pUser")
                .setParameter("pUser", userId);
        List list = qry.getResultList();
        System.out.println("Tasks found: " + list.size());
        for (Object t : list) {
            System.out.println("Task: " + t);
        }
    }
}
