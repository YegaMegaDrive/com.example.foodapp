package com.example.entities;

import com.example.to.MealEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "meal")
public class Meal {

    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductList product;

    @Column(name = "meal")
    @Enumerated(EnumType.STRING)
    private MealEnum meal;

}
