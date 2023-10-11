package com.learning.shopdevjava.controller;

import com.auth0.jwt.interfaces.Claim;
import com.learning.shopdevjava.config.ShopRolesEnum;
import com.learning.shopdevjava.config.ShopStatusEnum;
import com.learning.shopdevjava.dto.LoginDTO;
import com.learning.shopdevjava.dto.ResponseObject;
import com.learning.shopdevjava.dto.ShopDTO;
import com.learning.shopdevjava.entity.APIKeyEntity;
import com.learning.shopdevjava.entity.ShopEntity;
import com.learning.shopdevjava.exception.NotFoundException;
import com.learning.shopdevjava.repository.APIKeyRepository;
import com.learning.shopdevjava.repository.ShopRepository;
import com.learning.shopdevjava.security.JsonWebTokenUtils;
import com.learning.shopdevjava.service.ShopService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/shop/")
@AllArgsConstructor
public class ShopController {

    private ShopRepository shopRepository;
    private ShopService shopService;
    private JsonWebTokenUtils jsonWebTokenUtils; // for testing only
    private APIKeyRepository apiKeyRepository; // for testing only

    @PostMapping("signup")
    public ResponseObject signUp(@RequestBody ShopDTO shopDTO){
        // note: handle case can't parse dto
        ShopEntity entity = shopService.signUp(shopDTO.getName(), shopDTO.getEmail(), shopDTO.getPassword());

        return ResponseObject.builder()
                .code(201)
                .metadata(entity)
                .build();
    }

    @PostMapping("login")
    public ResponseObject login(@RequestBody LoginDTO loginDTO){
        /**
         * return accessToken, shopInfo
         */
        if(loginDTO == null || StringUtils.isEmpty(loginDTO.getEmail()) || StringUtils.isEmpty(loginDTO.getPassword())){
            throw new IllegalArgumentException("invalid request body");
        }

        Map<String, Object> shop = shopService.login(loginDTO.getEmail(), loginDTO.getPassword());

        // response

        return ResponseObject.builder()
                .code(200)
                .metadata(shop)
                .build();
    }

    @PostMapping("logout")
    public ResponseObject logout(@RequestAttribute("userId") String userId){
        System.out.println("userId: " + userId);

        Map<String, Object> metadata = shopService.logout(userId);
        metadata.put("userId", userId);
        return ResponseObject.builder().metadata(metadata).build();
    }

    @GetMapping("verifyToken")
    public Map<String, String> verifyTokenTest(@RequestParam String token){
        Map<String, Claim> claims = jsonWebTokenUtils.verify(token);
        Map<String, String> res = new HashMap<>();
        res.put("userId", claims.get("userId").asString());
        res.put("email", claims.get("email").asString());
        res.put("type", claims.get("type").asString());
        return res;
    }

    @GetMapping("get-shop")
    public List<ShopDTO> getShop(@RequestParam String name){
        if(StringUtils.isEmpty(name)){
            throw new IllegalArgumentException("name should not be empty");
        }
        List<ShopEntity> byName = shopRepository.findByName(name);
        return ShopDTO.fromEntityWithLimitFields(byName);
    }

    @GetMapping("create-api-key")
    public APIKeyEntity createApiKey(){
        String key = RandomStringUtils.random(12, true, true);
        APIKeyEntity entity = APIKeyEntity.builder()
                .key(key)
                .status(true)
                .permissions(Arrays.asList("shop.full", "newsfeed.read"))
                .build();
        return apiKeyRepository.save(entity);
    }


}
