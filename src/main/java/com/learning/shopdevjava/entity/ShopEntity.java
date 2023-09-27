package com.learning.shopdevjava.entity;

import com.learning.shopdevjava.config.ShopRolesEnum;
import com.learning.shopdevjava.config.ShopStatusEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Shops")
@Data
@Builder
public class ShopEntity {
    /*
    - String; name, email, password,
- enum: status: [active, inactive]
- boolean: verify
- array: roles [SHOP, WRITER, EDITOR, ADMIN]
     */
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String salt;
    private String status;

    private boolean verify;

    private List<String> roles;
}
