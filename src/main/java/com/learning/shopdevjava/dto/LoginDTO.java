package com.learning.shopdevjava.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO{
    private String email;
    private String password;

    public static void main(String[] args) {
        List<Integer> numList = Arrays.asList(10,21,31,40,59,60);
        numList.forEach( x-> {
            if( x%2 == 0) {
                return; // only skips this iteration.
            }
            System.out.println(x);
        });

    }
}