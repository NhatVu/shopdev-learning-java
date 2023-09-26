package com.learning.shopdevjava.config;

public enum ShopRolesEnum {
    SHOP("SHOP"),
    WRITER("WRITER"),
    EDITOR("EDITOR"),
    ADMIN("ADMIN");

    private final String value;

    private ShopRolesEnum(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

}
