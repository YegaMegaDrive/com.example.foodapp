package com.example.repositories;

import com.example.entities.Ration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RationRepo extends JpaRepository<Ration,Integer> {
}
