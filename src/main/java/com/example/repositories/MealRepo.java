package com.example.repositories;

import com.example.entities.Meal;
import com.example.to.enums.MealEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface MealRepo extends JpaRepository<Meal,Integer> {

    List<Meal> findAllByDateAndUser_Name(Date date, String userName);

    Meal findFirstByDateAndUser_NameAndProduct_NameAndMeal(Date date, String userName, String productName, MealEnum meal);

}
