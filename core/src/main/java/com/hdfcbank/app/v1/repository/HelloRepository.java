package com.hdfcbank.app.v1.repository;

import org.springframework.stereotype.Component;

@Component
public class HelloRepository {

    public void call(){
        System.out.println("Call::Repository");
    }
}
