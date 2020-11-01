package com.example.entities;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "product_list")
public class Product {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "kal_consumption")
    private Double kal;

    @Column(name = "protein__consumption")
    private Double protein;

    @Column(name = "fat_consumption")
    private Double fat;

    @Column(name = "carbs_consumption")
    private Double carbs;

}
