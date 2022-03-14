package com.doapp.doApp.repository;

import com.doapp.doApp.models.ListPermission;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListPermissionRepository extends CrudRepository<ListPermission, Integer> {

    @EntityGraph( attributePaths = {"listPermissionId.grantee"})
    List<ListPermission> findByListPermissionId_TaskList_ListId(Integer listId);

    @EntityGraph( attributePaths = {"listPermissionId.taskList"})
    List<ListPermission> findByListPermissionId_Grantee_UserId(Integer userId);

    ListPermission findByListPermissionId_TaskList_ListIdAndListPermissionId_Grantee_UserId(Integer listId, Integer granteeId);
}
