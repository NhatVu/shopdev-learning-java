package com.learning.shopdevjava.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HelloController {
    @GetMapping
    public Map<String, Object> hello(){
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", "hello world");
        return res;
    }
}
