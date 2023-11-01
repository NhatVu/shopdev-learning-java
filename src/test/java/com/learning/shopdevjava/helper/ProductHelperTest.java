package com.learning.shopdevjava.helper;

import com.learning.shopdevjava.entity.ClothEntity;
import com.learning.shopdevjava.entity.ProductEntity;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


class ProductHelperTest {

    @Test
    void patchUpdate_Normal() {
        ProductEntity entity = ProductEntity.builder()
                .id(new ObjectId("6537d0fa932f3a1f4f1550be"))
                .productName("product_name")
                .isDraft(true)
                .isPublished(false)
                .productPrice(100)
                .ratingAverage(5)
                .build();

        Map<String, Object> inputData = new HashMap<>();
        inputData.put("productName", "update_product_name");
        inputData.put("id", "update-id"); // not update
        inputData.put("isPublished", true); // not update

        ProductHelper.patchUpdateProduct(entity, inputData);

        // expected
        ProductEntity expectedEntity = ProductEntity.builder()
                .id(new ObjectId("6537d0fa932f3a1f4f1550be"))
                .productName("update_product_name")
                .isDraft(true)
                .isPublished(false)
                .productPrice(100)
                .ratingAverage(5)
                .build();

        Assertions.assertEquals(entity, expectedEntity);
    }

    @Test
    void patchUpdate_Normal_TypeCast() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("manufacture", "Philips");
        attributes.put("color", "RED");

        ProductEntity entity = ProductEntity.builder()
                .id(new ObjectId("6537d0fa932f3a1f4f1550be"))
                .productName("product_name")
                .isDraft(true)
                .isPublished(false)
                .productPrice(100)
                .productQuantity(10)
                .ratingAverage(5.0f)
                .productAttributes(attributes)
                .build();


        Map<String, Object> inputData = new HashMap<>();
        inputData.put("productName", "update_product_name");
        inputData.put("id", "update-id"); // not update
        inputData.put("ratingAverage", 4.3); // jackson will map to Double, but in ProductEntity, it's float type
        inputData.put("productQuantity", "10"); // should be integer, but we support parsing from String to int

        Map<String, Object> attributes_udpate = new HashMap<>();
        attributes_udpate.put("manufacture", "Philips-update");
        inputData.put("productAttributes", attributes_udpate);

        ProductHelper.patchUpdateProduct(entity, inputData);

        // expected
        Map<String, Object> expectedAttributes = new HashMap<>();
        expectedAttributes.put("manufacture", "Philips-update");
        expectedAttributes.put("color", "RED");
        ProductEntity expectedEntity = ProductEntity.builder()
                .id(new ObjectId("6537d0fa932f3a1f4f1550be"))
                .productName("update_product_name")
                .isDraft(true)
                .isPublished(false)
                .productPrice(100)
                .productQuantity(10)
                .ratingAverage(4.3f)
                .productAttributes(expectedAttributes)
                .build();

        Assertions.assertEquals(entity, expectedEntity);
    }

    @Test
    void patchUpdate_WrongType() {
        ProductEntity entity = ProductEntity.builder()
                .id(new ObjectId("6537d0fa932f3a1f4f1550be"))
                .productName("product_name")
                .isDraft(true)
                .isPublished(false)
                .productPrice(100)
                .ratingAverage(5)
                .build();

        Map<String, Object> inputData = new HashMap<>();
        inputData.put("productName", "update_product_name");
        inputData.put("id", "update-id"); // not update
        inputData.put("ratingAverage", "true"); // wrong type

        Assertions.assertThrows(IllegalArgumentException.class, () -> ProductHelper.patchUpdateProduct(entity, inputData));
    }

    @Test
    public void patchUpdateChild_Cloth(){
        ClothEntity entity = ClothEntity.builder()
                .id(new ObjectId("6537d0fa932f3a1f4f1550be"))
                .material("material")
                .size("100")
                .brand("brand")
                .build();

        Map<String, Object> inputData = new HashMap<>();
        inputData.put("id", "update-id"); // not update
        inputData.put("material", "update-branch"); // wrong type

        ProductHelper.patchUpdateProductChild(entity, ClothEntity.class, inputData);

        // expected
        ClothEntity expectedEntity = ClothEntity.builder()
                .id(new ObjectId("6537d0fa932f3a1f4f1550be"))
                .material("update-branch")
                .size("100")
                .brand("brand")
                .build();

        Assertions.assertEquals(entity, expectedEntity);
    }
}