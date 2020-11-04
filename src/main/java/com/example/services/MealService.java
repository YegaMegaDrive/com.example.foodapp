package com.example.services;

import com.example.entities.Meal;
import com.example.entities.Product;
import com.example.entities.User;
import com.example.repositories.MealRepo;
import com.example.repositories.ProductRepo;
import com.example.repositories.RationRepo;
import com.example.repositories.UserRepo;
import com.example.to.controller.AddOrDeleteProductRq;
import com.example.to.enums.MealEnum;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MealService {

    @Autowired
    private MealRepo mealRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired StatisticsService statisticsService;

    public void addProductToMeal(AddOrDeleteProductRq rq)throws NotFoundException {
        Product product = productRepo.findProductByName(rq.getProductName());
        User user = userRepo.findByName(rq.getUserName());
        if(product==null||user == null){
            throw new NotFoundException("Product or User with this name not found");
        }
        Meal meal = new Meal();
        if(rq.getMeal()!=null){
            meal.setMeal(rq.getMeal());
        }else{
            meal.setMeal(MealEnum.BREAKFEST);
        }
        meal.setProduct(product);
        meal.setUser(user);
        meal.setDate(new Date());
        mealRepo.save(meal);
        statisticsService.refreshStatistics(new Date(),rq.getUserName());
    }

    public void deleteProductFromMeal(AddOrDeleteProductRq rq)throws NotFoundException{
        Meal meal = mealRepo.findFirstByDateAndUser_NameAndProduct_NameAndMeal(new Date(),
                rq.getUserName(),rq.getProductName(),rq.getMeal());
        if(meal==null){
            throw new NotFoundException("meal with this name not found");
        }else {
            mealRepo.delete(meal);
            statisticsService.refreshStatistics(new Date(),rq.getUserName());
        }
    }

}
