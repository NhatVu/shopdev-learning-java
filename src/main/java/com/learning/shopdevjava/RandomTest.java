package com.learning.shopdevjava;

import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;

public class RandomTest {
    public static void main(String[] args) {
        System.out.println(1000000);
        String random = RandomStringUtils.random(15, true, true);
        System.out.println(random);
    }
}
