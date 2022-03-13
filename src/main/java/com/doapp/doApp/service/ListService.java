package com.doapp.doApp.service;

import com.doapp.doApp.auth.UserLoginService;
import com.doapp.doApp.models.TaskList;
import com.doapp.doApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class ListService {
    @PersistenceContext
    EntityManager em;

    @Autowired
    UserLoginService uls;

    // todo: add also lists that were granted via ListPermission table
    public List<TaskList> getTaskLists(String token) {
        User user = uls.userLogin(token);
        Query qry = em.createQuery("SELECT tl FROM TaskList tl WHERE tl.owner = :pUser")
                .setParameter("pUser", user.getUserId());
        return qry.getResultList();
    }

}
