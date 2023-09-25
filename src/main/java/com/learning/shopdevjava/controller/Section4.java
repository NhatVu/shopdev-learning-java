package com.learning.shopdevjava.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/section4")
public class Section4 {
    @Value("${test.profile.message}")
    private String message;
    @GetMapping
    public String testProfile(){
        return message;
    }
}
