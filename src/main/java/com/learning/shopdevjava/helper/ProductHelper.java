package com.learning.shopdevjava.helper;

import com.learning.shopdevjava.entity.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ProductHelper {
    private final static Set<String> NOT_ALLOWED_UPDATED_FIELDS = Set.of("id", "isDraft", "isPublished", "createdAt");

    /**
     * patch Update, but only for properties in first level
     *
     * @param entity
     * @param data
     * @return
     */
    public static ProductEntity patchUpdateProduct(ProductEntity entity, Map<String, Object> data) {
            /*
            check case: default value of primitive type of inputDTO
            check case: if using map and input value has different type than entity
             */
        data.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(ProductEntity.class, key);
            if (field == null || NOT_ALLOWED_UPDATED_FIELDS.contains(key)) {
                return;
            }

            if (value instanceof Map) {
                // productAttribute
                ReflectionUtils.makeAccessible(field);
                Map<String, Object> mapOrigin = (Map<String, Object>) ReflectionUtils.getField(field, entity);
                Map<String, Object> mapInput = (Map<String, Object>) value;
                mapOrigin.putAll(mapInput);
                ReflectionUtils.setField(field, entity, mapOrigin);
            } else {
                ReflectionUtils.makeAccessible(field);
                Object castValue = Utils.cast(value, field.getType().getTypeName());
                ReflectionUtils.setField(field, entity, castValue);
            }

            if ("productName".equals(key)) {
                Field productSlugField = ReflectionUtils.findField(ProductEntity.class, "productSlug");
                ReflectionUtils.makeAccessible(productSlugField);
                ReflectionUtils.setField(productSlugField, entity, Utils.toSlug(value.toString()));
            }
        });
        return entity;
    }

    public static <T> T patchUpdateProductChild(T entity, Class<T> typeParameterClass, Map<String, Object> data) {
        // update for child product
        data.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(typeParameterClass, key);
            if (field == null || NOT_ALLOWED_UPDATED_FIELDS.contains(key)) {
                return;
            }

            ReflectionUtils.makeAccessible(field);
            Object castValue = Utils.cast(value, field.getType().getTypeName());
            ReflectionUtils.setField(field, entity, castValue);
        });
        return entity;
    }
}
