package com.smartpasswordmanager.repository;

import com.smartpasswordmanager.entity.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordRepository extends JpaRepository<Password,Integer> {
    @Query(value = "SELECT * FROM user_passwords WHERE user_Id = :user_Id",nativeQuery = true)
    List<Password> findPasswordByUserId(@Param("user_Id") int user_Id);

    @Query(value = "SELECT * FROM user_passwords WHERE password_id=:password_id",nativeQuery = true)
    Password findPasswordById(@Param("password_id")int password_id);
}
