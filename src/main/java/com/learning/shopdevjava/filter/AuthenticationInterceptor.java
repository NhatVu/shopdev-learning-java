package com.learning.shopdevjava.filter;

import com.auth0.jwt.interfaces.Claim;
import com.learning.shopdevjava.config.HeaderConstant;
import com.learning.shopdevjava.config.StringConstant;
import com.learning.shopdevjava.security.JsonWebTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * required for protected endpoint.
 * signup, login, testing doesn't required
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private JsonWebTokenUtils jsonWebTokenUtils;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String url = request.getRequestURI();
        if(url.contains("shop/login") || url.contains("shop/signup")
                || url.contains("verifyToken")){
            return true;
        }

        String accessTokenHeader = request.getHeader(HeaderConstant.AUTHORIZATION);
        Map<String, Claim> claims = jsonWebTokenUtils.verify(accessTokenHeader);
        String userId = claims.get(StringConstant.USER_ID).asString();

        request.setAttribute(StringConstant.USER_ID, userId);
        return true;
    }
}
