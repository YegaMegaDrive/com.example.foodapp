package com.example.controller;

import com.example.services.StatisticsService;
import com.example.to.controller.GetStatisticsRq;
import com.example.to.controller.SimpleResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Statistics")
public class RequestControllerStatistics {

    @Autowired
    private StatisticsService service;

    @PostMapping("/Get")
    public ResponseEntity getRation(@RequestBody GetStatisticsRq rq){
        return ResponseEntity.ok(service.getStatistics(rq));
    }

    @GetMapping("/Delete")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity deleteProduct(){
        service.deleteStatistics();
        return ResponseEntity.ok(new SimpleResp());
    }

}
