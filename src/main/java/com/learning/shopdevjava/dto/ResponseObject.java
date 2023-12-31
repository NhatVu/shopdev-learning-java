package com.learning.shopdevjava.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObject {
    private int code;
    private String message;
    private Object metadata;
}
