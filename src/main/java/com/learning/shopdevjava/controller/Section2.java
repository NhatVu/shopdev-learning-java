package com.learning.shopdevjava.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/section2")
public class Section2 {

    @GetMapping
    public Map<String, Object> compression(){
        String text = "asfsdfasdfasdfasf";
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i <100000 ; i++){
            builder.append(text);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", builder.toString());

        return res;
    }
}
