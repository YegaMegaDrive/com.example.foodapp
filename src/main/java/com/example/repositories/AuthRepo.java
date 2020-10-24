package com.example.repositories;

import com.example.entities.AuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepo extends JpaRepository<AuthInfo,Integer> {

    @Query("select i from AuthInfo i where i.login =: login")
    AuthInfo findUserInfoByLogin(@Param("login") String login);

}
