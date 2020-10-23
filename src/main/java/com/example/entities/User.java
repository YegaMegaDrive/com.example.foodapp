package com.example.entities;

import com.example.to.SexEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "sex")
    private SexEnum sex;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "height")
    private Double height;


}
