package com.learning.shopdevjava.controller;

import com.learning.shopdevjava.helper.MDCRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {
    @GetMapping
    public Map<String, Object> hello(){
        log.info("call hello controller");
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", "hello world");
        return res;
    }

    @GetMapping("/log")
    public String createLoopLog(){
        for(int i = 0; i < 100000; i++){
            log.info("hello logger");
        }
        return "Success";
    }

    @GetMapping("/multithread")
    public String multithread(){
        log.info("outside multithread");
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for(int i = 0; i < 10; i++) {
            executorService.submit(MDCRunnable.wrap(() -> {
                log.info("call multithread");
            }));
        }
        return "multithread";
    }
}
