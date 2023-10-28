package com.learning.shopdevjava.dto;

import lombok.Data;

@Data
public class ProductSearchDTO {
    private String text;
    private int offset;
    private int limit;
}
