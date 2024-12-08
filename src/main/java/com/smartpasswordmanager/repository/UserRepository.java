package com.smartpasswordmanager.repository;

import com.smartpasswordmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Override
    Optional<User> findById(Integer integer);

    User findByuserName(String username);

    User findByuserMail(String userMail);

}
