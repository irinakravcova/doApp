package com.doapp.doApp.repository;

import com.doapp.doApp.models.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    @Query("SELECT t FROM Task t WHERE t.taskId = ?1 AND t.owner.userId = ?2")
    Task findByIdAndTaskOwner(Integer taskId, Integer userId);

    @Query("SELECT t FROM Task t, ListPermission lp WHERE t.taskId = ?1 AND t.list = lp.listPermissionId.taskList " +
            "AND lp.listPermissionId.grantee.userId = ?2")
    Task findByIdNonOwner(Integer taskId, Integer userId);
    @Query("SELECT t FROM Task t JOIN t.list tl LEFT JOIN t.list.listPermission lp" +
            " WHERE t.list.listId = ?1 AND (t.owner.userId = ?2 OR lp.listPermissionId.grantee.userId = ?2 OR tl.owner.userId = ?2)")
    List<Task> listTasks(Integer listId, Integer userId);
    @Query("SELECT t FROM Task t JOIN t.list tl LEFT JOIN t.list.listPermission lp" +
            " WHERE t.taskId = ?1 AND (t.owner.userId = ?2 OR lp.listPermissionId.grantee.userId = ?2 OR tl.owner.userId = ?2)")
    Task findTaskByIdIfAccessibleToUser(Integer taskId, Integer userId);
    //TODO: save and update also should be query-based to prevent modification by unauthorized (to this list) user
}
