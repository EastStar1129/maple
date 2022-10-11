package com.nexon.maple.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping("/test")
public class Controller {

    @GetMapping("/test")
    public String test(){
        return "test/test";
    }
}
