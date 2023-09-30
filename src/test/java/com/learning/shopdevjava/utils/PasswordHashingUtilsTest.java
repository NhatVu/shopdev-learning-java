package com.learning.shopdevjava.utils;

import com.learning.shopdevjava.security.PasswordHashingUtils;
import org.junit.jupiter.api.Test;


class PasswordHashingUtilsTest {

    private PasswordHashingUtils passwordHashingUtils = new PasswordHashingUtils();
    @Test
    public void checkPassword(){
        String rawPassword = "afadwerdgw";

        String salt = passwordHashingUtils.generateSalt();
        String hashPassword = passwordHashingUtils.hashpw(rawPassword, salt);
        System.out.println("salt: " + salt);
        System.out.println("hashPassword: " + hashPassword);
        // verify
        boolean verify = passwordHashingUtils.verifypw(hashPassword, rawPassword, salt);
        assert verify == true;
    }
}