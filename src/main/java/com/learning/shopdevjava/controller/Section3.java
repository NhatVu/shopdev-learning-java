package com.learning.shopdevjava.controller;

import com.learning.shopdevjava.entity.UserEntity;
import com.learning.shopdevjava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/section3")
public class Section3 {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/insert")
    public UserEntity insertHardCode(@RequestParam String name){
        UserEntity userEntity = UserEntity.builder()
                .name(name)
                .build();

        UserEntity insert = userRepository.insert(userEntity);
        return insert;

    }
}
