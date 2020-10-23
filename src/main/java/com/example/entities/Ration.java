package com.example.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ration")
@Data
public class Ration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false)
    private User user;

    @Column(name = "date")
    @DateTimeFormat
    private Date date;

    @Column(name = "calories")
    private Double calories;

    @Column(name = "fat")
    private Double fat;

    @Column(name = "carbs")
    private Double carbs;

    @Column(name = "protein")
    private Double protein;

}
