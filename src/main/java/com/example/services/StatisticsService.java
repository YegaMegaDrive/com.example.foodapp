package com.example.services;

import com.example.entities.Meal;
import com.example.entities.Ration;
import com.example.repositories.MealRepo;
import com.example.repositories.ProductRepo;
import com.example.repositories.RationRepo;
import com.example.to.controller.GetStatisticsRq;
import com.example.to.controller.GetStatisticsRs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@Slf4j
public class StatisticsService {

    @Autowired
    private RationRepo rationRepo;

    @Autowired
    private MealRepo mealRepo;

    @Autowired
    private ProductRepo productRepo;

    @Value("${food.app.max.stats.days}")
    private Integer maxStatsDays;

    public GetStatisticsRs getStatistics(GetStatisticsRq rq){

        GetStatisticsRs rs = new GetStatisticsRs();
        Ration ration =null;
        log.info("name:{},date:{}",rq.getUserName(),rq.getDate());
        if(rq.getDate()!=null){
            refreshStatistics(rq.getDate(),rq.getUserName());
            ration = rationRepo.findByUserNameAndDate(rq.getUserName(),rq.getDate());
        }else{
            refreshStatistics(new Date(),rq.getUserName());
            ration = rationRepo.findByUserNameAndDate(rq.getUserName(),new Date());
            //ration = rationRepo.findByUserName(rq.getUserName());
        }
        if(ration!=null) {
            rs.setCalories(ration.getCalories());
            rs.setCarbs(ration.getCarbs());
            rs.setFat(ration.getFat());
            rs.setProtein(ration.getProtein());
        }
        return rs;
    }

    public void deleteStatistics(){
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, -maxStatsDays);
        rationRepo.deleteRationsByDateBefore(calendar.getTime());
    }

    public void refreshStatistics(Date date, String userName){
        List<Meal> mealList= mealRepo.findAllByDateAndUser_Name(date,userName);
        if(mealList!=null) {
            Ration ration = rationRepo.findByUserNameAndDate(userName,date);
            if(ration==null){
                ration = new Ration();
                ration.setUser(mealList.get(0).getUser());
                ration.setDate(new Date());
                for (Meal meal : mealList) {
                    if(ration.getCalories()==null&&ration.getCarbs()==null&&ration.getFat()==null&&ration.getProtein()==null){
                        ration.setCalories(meal.getProduct().getKal());
                        ration.setCarbs(meal.getProduct().getCarbs());
                        ration.setFat(meal.getProduct().getFat());
                        ration.setProtein(meal.getProduct().getProtein());
                    }else {
                        Ration cashRation = new Ration(ration);
                        ration.setCalories(cashRation.getCalories()+ meal.getProduct().getKal());
                        ration.setCarbs(meal.getProduct().getCarbs() + cashRation.getCarbs());
                        ration.setFat(cashRation.getFat() + meal.getProduct().getFat());
                        ration.setProtein(cashRation.getProtein() + meal.getProduct().getProtein());
                    }
                }
            }else{
                ration.cleanStats();
                for (Meal meal : mealList) {
                    Ration cashRation = new Ration(ration);
                    ration.setCalories(cashRation.getCalories()+ meal.getProduct().getKal());
                    ration.setCarbs(meal.getProduct().getCarbs() + cashRation.getCarbs());
                    ration.setFat(cashRation.getFat() + meal.getProduct().getFat());
                    ration.setProtein(cashRation.getProtein() + meal.getProduct().getProtein());
                }
            }
            rationRepo.save(ration);
        }
    }

}
