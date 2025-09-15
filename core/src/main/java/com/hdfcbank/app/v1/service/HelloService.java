package com.hdfcbank.app.v1.service;

import com.hdfcbank.app.v1.repository.HelloRepository;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private final HelloRepository helloRepository;

    public HelloService(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    public void call(){
        System.out.println("Call::Service");
        helloRepository.call();
    }
}
