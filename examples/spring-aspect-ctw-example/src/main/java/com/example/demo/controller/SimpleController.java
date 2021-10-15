package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SimpleController {

    @GetMapping("/hello")
    public String hello(){
        log.info("SimpleController#hello called");
        return "hello world";
    }
}
