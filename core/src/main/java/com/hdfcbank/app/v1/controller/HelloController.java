package com.hdfcbank.app.v1.controller;

import com.hdfcbank.app.v1.service.HelloService;
import org.springframework.stereotype.Component;

@Component
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    public void call(){
        System.out.println("Call::Controller");
        helloService.call();
    }
}
