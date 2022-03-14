package com.doapp.doApp.repository;

import com.doapp.doApp.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUserName(String userName);

    User findByUserNameAndPassword(String userName, String password);

    User findByTokenEquals(String token);

    List<User> findByUserIdNot(Integer userId);
}
