package com.example.to.controller;

import com.example.to.enums.SexEnum;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserDataRq {
    @NonNull
    private String login;
    @NonNull
    private String name;
    private Integer age;
    @NonNull
    private SexEnum sex;
    private Double weight;
    private Double height;
}
