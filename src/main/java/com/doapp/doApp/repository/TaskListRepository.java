package com.doapp.doApp.repository;

import com.doapp.doApp.models.TaskList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskListRepository extends CrudRepository<TaskList, Integer> {

    @Query("SELECT tl FROM TaskList tl LEFT JOIN tl.listPermission lp" +
            " WHERE (tl.owner.userId = ?1 OR lp.listPermissionId.grantee.userId = ?1)")
    List<TaskList> getLists(Integer userId);

}
