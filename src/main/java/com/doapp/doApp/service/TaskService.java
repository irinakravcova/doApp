package com.doapp.doApp.service;

import com.doapp.doApp.models.Status;
import com.doapp.doApp.models.Task;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class TaskService {
    @PersistenceContext
    EntityManager em;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void addTask(String user, String currentList, String task_content_here) {
        Task task = new Task(null, 1, 1, task_content_here, null, Status.INCOMPLETE);
        em.persist(task);
    }
}
