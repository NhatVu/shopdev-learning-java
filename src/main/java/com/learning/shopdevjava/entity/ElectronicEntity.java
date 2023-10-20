package com.learning.shopdevjava.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Electrics")
@Data
@Builder
public class ElectronicEntity {
    @Id
    private ObjectId id;
    private String manufacture;
    private String model;
    private String color;
}
