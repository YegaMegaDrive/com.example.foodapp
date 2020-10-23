package com.example.entities;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "product_list")
@Data
public class ProductList {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "picture")
    private String picture;

    @Column(name = "kal_consumption")
    private Double kal;

    @Column(name = "protein__consumption")
    private Double protein;

    @Column(name = "fat_consumption")
    private Double fat;

    @Column(name = "carbs_consumption")
    private Double carbs;

}
