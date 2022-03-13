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
     /*
     "Normal" JOIN used instead of "WHERE" kind of JOIN:
     SELECT t FROM Task t
     JOIN ListPermission lp ON t.listId = lp.listPermissionId.listId
     WHERE t.taskId = ?1 AND lp.listPermissionId.granteeUserId = ?2

     Native select should look like (for prepareStatement):
     SELECT t FROM task t LEFT JOIN list_permission lp ON t.listId = lp.listId
     WHERE t.taskId = :1 AND lp.granteeUserId = :2
     */
    Task findByIdNonOwner(Integer taskId, Integer userId);

//    @Query(value = "SELECT t FROM Task t LEFT JOIN t. ", nativeQuery = true)
    /* Native SELECT expected to be generated:
     SELECT t FROM task t LEFT OUTER JOIN list_permission lp ON t.listId = lp.listId
     WHERE t.taskId = :1 AND (t.ownerUserId = :2 OR lp.granteeUserId = :2)
     */
    @Query("SELECT t FROM Task t JOIN t.list tl LEFT JOIN t.list.listPermission lp" +
            " WHERE t.list.listId = ?1 AND (t.owner.userId = ?2 OR lp.listPermissionId.grantee.userId = ?2 OR tl.owner.userId = ?2)")
    List<Task> listTasks(Integer listId, Integer userId);
    @Query("SELECT t FROM Task t JOIN t.list tl LEFT JOIN t.list.listPermission lp" +
            " WHERE t.taskId = ?1 AND (t.owner.userId = ?2 OR lp.listPermissionId.grantee.userId = ?2 OR tl.owner.userId = ?2)")
    Task findTaskByIdIfAccessibleToUser(Integer taskId, Integer userId);
    //TODO: save and update also should be query-based to prevent modification by unauthorized (to this list) user
}
