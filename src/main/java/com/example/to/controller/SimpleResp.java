package com.example.to.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class SimpleResp {
    private String status;

    public SimpleResp() {
        this.status = HttpStatus.OK.getReasonPhrase();
    }
}
