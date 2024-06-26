package com.learning.shopdevjava.filter;

import com.learning.shopdevjava.constant.CommonConstant;
import com.learning.shopdevjava.constant.ErrorCodeConstant;
import com.learning.shopdevjava.constant.HeaderConstant;
import com.learning.shopdevjava.entity.APIKeyEntity;
import com.learning.shopdevjava.exception.InvalidAPIKeyException;
import com.learning.shopdevjava.repository.APIKeyRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class APIKeyInterceptor implements HandlerInterceptor {
    @Autowired
    private APIKeyRepository apiKeyRepository;

    @Value("${app.api_key.permission}")
    private String appPermission;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();
        for(String publicUrl: CommonConstant.PUBLIC_URL) {
            if (uri.contains(publicUrl)) {
                return true;
            }
        }

        String apiKeyHeader = request.getHeader(HeaderConstant.API_KEY);
        if(StringUtils.isEmpty(apiKeyHeader)){
            throw new InvalidAPIKeyException(ErrorCodeConstant.INVALID_API_KEY_NOT_FOUND_IN_HEADER);
        }

        isValidAPIKey(apiKeyHeader);

        return true;
    }

    private boolean isValidAPIKey(String apiKey){
        APIKeyEntity entity = apiKeyRepository.findByKey(apiKey);
        if(entity == null || entity.isStatus() == false){
            throw new InvalidAPIKeyException(ErrorCodeConstant.INVALID_API_KEY_NOT_FOUND);
        }

        if(!entity.getPermissions().contains(appPermission)){
            throw new InvalidAPIKeyException(ErrorCodeConstant.INVALID_API_KEY_NOT_SUFFICIENT_PERMISSION);
        }

        return true;
    }


}
