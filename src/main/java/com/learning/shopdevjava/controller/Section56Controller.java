package com.learning.shopdevjava.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/section56")
@Slf4j
public class Section56Controller {
    private String INSERT_QUERY = "insert into learning_jpa_hibernate.message (id, text) values (:id, :message)";
    @Autowired
    @Qualifier("postgresJdbcTemplate")
    NamedParameterJdbcTemplate jdbcTemplate;

    private AtomicInteger counter = new AtomicInteger();

    @GetMapping
    public String create10mRecords() throws InterruptedException {
        /*
        insert batch
        using jdbcTemplate -> raw, not using jpa becuase it related to entityManager.
         */
        log.info("create 10m records");
        long start = System.currentTimeMillis();
        int batchSize = 1000;
        int n = 10000000/batchSize;

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i = 0; i < n; i++) {
            executorService.submit(() -> {
                Map<String, Object>[] parameters = new Map[batchSize];
                for(int k = 0; k < batchSize; k++){
                    Map<String, Object> parameter = new HashMap<>();
                    int nextVal = getNextId();

                    if(nextVal % 5000 == 0){
                        log.info("nextval: " + nextVal);
                    }

                    parameter.put("id", nextVal);
                    parameter.put("message", RandomStringUtils.random(15, true, true));

                    parameters[k] = parameter;
                }

                jdbcTemplate.batchUpdate(INSERT_QUERY, parameters);
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {

        }
        long end = System.currentTimeMillis();
        return "create10mRecords. Time: " + (end - start)/1000 + " second";
    }

    private int getNextId(){
//        String SQL = "SELECT nextval('learning_jpa_hibernate.message_id_seq')";
//        return jdbcTemplate.queryForObject(SQL, new HashMap<>(), Integer.class);
        return counter.getAndIncrement();
    }
}
