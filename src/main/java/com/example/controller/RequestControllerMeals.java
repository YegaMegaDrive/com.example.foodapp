package com.example.controller;

import com.example.services.MealService;
import com.example.to.controller.AddOrDeleteProductRq;
import com.example.to.controller.GetStatisticsRq;
import com.example.to.controller.GetStatisticsRs;
import com.example.to.controller.SimpleResp;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Meals")
public class RequestControllerMeals {

    @Autowired
    private MealService service;

    @PostMapping("/Add/Product")
    public ResponseEntity addProduct(@RequestBody AddOrDeleteProductRq rq)throws NotFoundException {
        service.addProductToMeal(rq);
        return ResponseEntity.ok(new SimpleResp());
    }
    @PostMapping("/Delete/Product")
    public ResponseEntity deleteProduct(@RequestBody AddOrDeleteProductRq rq)throws NotFoundException{
        service.deleteProductFromMeal(rq);
        return ResponseEntity.ok(new SimpleResp());
    }

    @PostMapping("/Get/Products")
    public ResponseEntity getAllProductsByDateAndUserName(@RequestBody GetStatisticsRq rq)throws NotFoundException{
        return ResponseEntity.ok(service.getAllProductsByDateAndUserName(rq));
    }

}
