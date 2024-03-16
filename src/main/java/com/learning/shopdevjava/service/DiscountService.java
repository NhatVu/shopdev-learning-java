package com.learning.shopdevjava.service;

import com.learning.shopdevjava.constant.ErrorCodeConstant;
import com.learning.shopdevjava.constant.DiscountTypeConstant;
import com.learning.shopdevjava.dto.DiscountDTO;
import com.learning.shopdevjava.dto.ProductDTO;
import com.learning.shopdevjava.entity.DiscountEntity;
import com.learning.shopdevjava.exception.BadRequestException;
import com.learning.shopdevjava.repository.DiscountRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    ProductService productService;

    public DiscountDTO createDiscountCode(DiscountDTO dto) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (dto.getDiscountStartDate().isBefore(currentDateTime) || dto.getDiscountEndDate().isBefore(currentDateTime)) {
            throw new BadRequestException(ErrorCodeConstant.BAD_REQUEST_DISCOUNT_CODE_EXPIRED);
        }

        if (dto.getDiscountStartDate().isEqual(dto.getDiscountEndDate()) || dto.getDiscountStartDate().isAfter(dto.getDiscountEndDate())) {
            throw new BadRequestException(ErrorCodeConstant.BAD_REQUEST_DISCOUNT_CODE_START_DATE_HAVE_TO_SMALLER_THAN_END_DATE);
        }
        // create index for discount
        DiscountEntity getDiscount = discountRepository.findByShopIdAndDiscountCode(new ObjectId(dto.getShopId()), dto.getDiscountCode());
        if (getDiscount != null) {
            throw new BadRequestException(ErrorCodeConstant.BAD_REQUEST_DISCOUNT_CODE_EXIST);
        }

        DiscountEntity save = discountRepository.save(dto.toEntity());
        return DiscountDTO.fromEntity(save);
    }

    public List<ProductDTO> getProductByDiscountCode(String discountCode, String shopId, int offset, int limit) {
        // find discount code
        DiscountEntity getDiscount = discountRepository.findByShopIdAndDiscountCode(new ObjectId(shopId), discountCode);
        if (getDiscount == null) {
            throw new BadRequestException(ErrorCodeConstant.BAD_REQUEST_DISCOUNT_CODE_NOT_FOUND);
        }
        if (!getDiscount.isActive()) {
            throw new BadRequestException(ErrorCodeConstant.BAD_REQUEST_DISCOUNT_CODE_INACTIVE);
        }

        if (DiscountTypeConstant.APPLY_TO_ALL.equals(getDiscount.getDiscountAppliesTo())) {
            // type all -> get all products
            return productService.getAllPublishProduct(shopId, offset, limit);
        } else if (DiscountTypeConstant.APPLY_TO_SPECIFIC.equals(getDiscount.getDiscountAppliesTo())) {
            // only getProduct that belongs to discountProductIds fields
            return productService.getProductByIds(getDiscount.getDiscountProductIds(), offset, limit);
        }
        throw new BadRequestException(ErrorCodeConstant.BAD_REQUEST_UNSUPPORTED);
    }

    public List<DiscountDTO> getDiscountByShopId(String shopId, int offset, int limit) {
        List<DiscountEntity> byShopId = discountRepository.findByShopIdAndIsActiveIsTrueOrderByUpdatedAtDesc(new ObjectId(shopId), PageRequest.of(offset, limit));
        if (CollectionUtils.isEmpty(byShopId)) {
            return new ArrayList<>();
        }

        List<DiscountDTO> res = new ArrayList<>();
        for (DiscountEntity e : byShopId) {
            res.add(DiscountDTO.fromEntity(e));
        }
        return res;
    }
}
