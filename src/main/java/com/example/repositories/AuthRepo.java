package com.example.repositories;

import com.example.entities.User;
import com.example.entities.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepo extends JpaRepository<UserAuth,Integer> {

   // @Query("select i from UserAuth i where i.login =: login")
    //UserAuth findUserLoginByLogin(/*@Param("login") */ String login);
    Optional<UserAuth> findUserLoginByLogin(String login);

    UserAuth getUserAuthByLogin(String login);

}
