package com.hdfcbank.app.controller;

import com.hdfcbank.app.v1.controller.HelloController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/common")
public class CommonController {

    private final HelloController controller;

    public CommonController(HelloController controller) {
        this.controller = controller;
    }

    @GetMapping("/call")
    public String call(){
        controller.call();
        return "Core called successfully !!";
    }
}
