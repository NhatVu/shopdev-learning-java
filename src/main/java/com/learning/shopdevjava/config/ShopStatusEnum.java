package com.learning.shopdevjava.config;

public enum ShopStatusEnum {

    ACTIVE("active"),
    DEACTIVE("deactive");
    private String value;

    private ShopStatusEnum(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
