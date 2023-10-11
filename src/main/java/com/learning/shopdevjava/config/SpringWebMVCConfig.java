package com.learning.shopdevjava.config;

import com.learning.shopdevjava.filter.APIKeyInterceptor;
import com.learning.shopdevjava.filter.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class SpringWebMVCConfig implements WebMvcConfigurer {
    @Autowired
    APIKeyInterceptor apiKeyInterceptor;

    @Autowired
    AuthenticationInterceptor authenticationInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor);
        registry.addInterceptor(authenticationInterceptor);
    }
}
