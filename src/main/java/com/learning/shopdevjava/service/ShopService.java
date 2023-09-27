package com.learning.shopdevjava.service;

import com.learning.shopdevjava.config.ShopRolesEnum;
import com.learning.shopdevjava.config.ShopStatusEnum;
import com.learning.shopdevjava.dto.ShopDTO;
import com.learning.shopdevjava.entity.ShopEntity;
import com.learning.shopdevjava.exception.NotFoundException;
import com.learning.shopdevjava.exception.UnAuthorizedException;
import com.learning.shopdevjava.repository.ShopRepository;
import com.learning.shopdevjava.utils.PasswordHashingUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class ShopService {
    private ShopRepository shopRepository;
    private PasswordHashingUtils passwordHashingUtils;
    public ShopEntity signUp(String name, String email, String password){
        /*
        - if email exits -> return error
        - hash password, create shop
        - create + save privateKey and publicKey
        - create accessToken, refreshToken.
         */

        // use exist function, which return boolean instead of get function. This will reduce network throughput
        if(shopRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already exists");
        }

        String salt = passwordHashingUtils.generateSalt();
        String hashPassword = passwordHashingUtils.hashpw(password, salt);

        // when creating shop, there is some default value
        ShopEntity entity = ShopEntity.builder()
                .name(name)
                .email(email)
                .salt(salt)
                .password(hashPassword)
                .status(ShopStatusEnum.DEACTIVE.getValue())
                .verify(false)
                .roles(Arrays.asList(ShopRolesEnum.SHOP.getValue()))
                .build();
        return shopRepository.save(entity);
    }

    public ShopEntity findByEmail(String email){
        return shopRepository.findByEmail(email);
    }

    public Map<String, Object> login(String email, String password){
        ShopEntity entity = findByEmail(email);
        if(entity == null){
            throw new UnAuthorizedException("Email or Password is wrong");
        }

        boolean verifypw = passwordHashingUtils.verifypw(entity.getPassword(), password, entity.getSalt());
        if(!verifypw){
            throw new UnAuthorizedException("Email or Password is wrong");
        }
        // return metadata, token
        Map<String, Object> res = new HashMap<>();
        res.put("shop", ShopDTO.fromEntityWithLimitFields(entity));
        return res;
    }
}
