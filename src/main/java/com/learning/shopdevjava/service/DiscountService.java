package com.learning.shopdevjava.service;

import com.learning.shopdevjava.config.ErrorCodeConstant;
import com.learning.shopdevjava.dto.DiscountDTO;
import com.learning.shopdevjava.entity.DiscountEntity;
import com.learning.shopdevjava.entity.ProductEntity;
import com.learning.shopdevjava.exception.BadRequestException;
import com.learning.shopdevjava.repository.DiscountRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepository;
    public DiscountDTO createDiscountCode(DiscountDTO dto){
        LocalDateTime currentDateTime = LocalDateTime.now();
        if(dto.getDiscountStartDate().isBefore(currentDateTime) || dto.getDiscountEndDate().isBefore(currentDateTime)){
            throw new BadRequestException(ErrorCodeConstant.BAD_REQUEST_DISCOUNT_CODE_EXPIRED);
        }

        if(dto.getDiscountStartDate().isEqual(dto.getDiscountEndDate()) || dto.getDiscountStartDate().isAfter(dto.getDiscountEndDate())){
            throw new BadRequestException(ErrorCodeConstant.BAD_REQUEST_DISCOUNT_CODE_START_DATE_HAVE_TO_SMALLER_THAN_END_DATE);
        }
        // create index for discount
        DiscountEntity getDiscount = discountRepository.findByShopIdAndDiscountCode(new ObjectId(dto.getShopId()), dto.getDiscountCode());
        if(getDiscount != null){
            throw new BadRequestException(ErrorCodeConstant.BAD_REQUEST_DISCOUNT_CODE_EXIST);
        }

        DiscountEntity save = discountRepository.save(dto.toEntity());
        return DiscountDTO.fromEntity(save);
    }

    public List<DiscountDTO> getAllDiscountCodeWithProduct(String discountCode, String shopId, String userId, int offset, int limit){

        return null;
    }
}
