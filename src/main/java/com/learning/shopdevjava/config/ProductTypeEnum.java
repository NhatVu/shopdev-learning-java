package com.learning.shopdevjava.config;

public enum ProductTypeEnum {
    ELECTRONICS("electronics"),
    CLOTHES("clothes"),
    FURNITURES("furnitures");

    private String value;

    private ProductTypeEnum(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
