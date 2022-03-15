package com.doapp.doApp.service;

import com.doapp.doApp.auth.UserLoginService;
import com.doapp.doApp.models.Status;
import com.doapp.doApp.models.Task;
import com.doapp.doApp.models.TaskList;
import com.doapp.doApp.models.User;
import com.doapp.doApp.repository.TaskListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.List;

@Service
public class TaskService {
    @PersistenceContext
    EntityManager em;

    @Autowired
    UserLoginService uls;

    @Autowired
    TaskListRepository tls;


    public Task get(String token, Integer taskId) {
        User user = uls.userLogin(token);
        if (user == null) {
            return null;
        }

        return null;

    }

    public Task addTask(String token, Integer listId, String task_content_here) {
        User user = uls.userLogin(token);
        if (user == null) {
            return null;
        }
        return addTask(user, listId, task_content_here);
    }

    public Task addTask(User user, Integer listId, String task_content_here) {

        TaskList taskList = tls.findById(listId).orElseThrow();
        // todo: add check if this list is writable for current user
        Task task = new Task(null, user, taskList, task_content_here, null, Status.INCOMPLETE.toString());
        em.persist(task);
        return task;
    }

    public List<Task> list(String token, Integer listId) {
        User user = uls.userLogin(token);
        if (user == null) {
            return null;
        }
        return list(user.getUserId(), listId);
    }

    public Task updateTask(String token, Integer taskId, String taskContent, Calendar dueDate) {
        User user = uls.userLogin(token);
        if (user == null) {
            return null;
        }
        return updateTask(user.getUserId(), taskId, taskContent, dueDate);
    }

    public Task updateTask(Integer userId, Integer taskId, String taskContent, Calendar dueDate) {
        System.err.println("modify task: " + taskId + " => " + taskContent);
        //todo: not implemented
        return null;
    }

    public Task doneTask(String token, Integer taskId) {
        User user = uls.userLogin(token);
        if (user == null) {
            return null;
        }
        return doneTask(user.getUserId(), taskId);
    }

    public Task doneTask(Integer userId, Integer taskId) {
        System.err.println("done task: " + taskId);
        //todo: implement task done functionality
        return null;
    }

    public Task deleteTask(String token, Integer taskId) {
        User user = uls.userLogin(token);
        if (user == null) {
            return null;
        }
        return deleteTask(user.getUserId(), taskId);
    }

    public Task deleteTask(Integer userId, Integer taskId) {
        System.err.println("delete task: " + taskId);
        // todo: not implemented
        return null;
    }

    public List<Task> list(Integer userId, Integer listId) {

        // todo: also check if list has been granted via ListPermission table
        Query qry = em.createQuery("SELECT t FROM Task t WHERE t.list.listId = :pList OR t.owner = :pUser")
                .setParameter("pList", listId)
                .setParameter("pUser", userId);
        List list = qry.getResultList();
        // todo: remove println code fragment later
        System.out.println("Tasks found: " + list.size());
        for (Object t : list) {
            System.out.println("Task: " + t);
        }
        return list;
    }
}
