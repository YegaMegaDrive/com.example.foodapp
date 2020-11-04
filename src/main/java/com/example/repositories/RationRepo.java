package com.example.repositories;

import com.example.entities.Ration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface RationRepo extends JpaRepository<Ration,Integer> {

    @Query("select r from Ration r join fetch r.user where r.user.name =:name")
    Ration findByUserName(@Param("name") String name);

    @Query("select r from Ration r join fetch r.user where r.user.name =:name and r.date=:date")
    Ration findByUserNameAndDate(@Param("name") String name,@Param("date") Date date);

   // @Query("delete from Ration r where r.date > :date")
    @Transactional
    void deleteRationsByDateBefore(Date date);
}
