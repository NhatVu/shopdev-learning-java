package com.learning.shopdevjava.filter;

import com.learning.shopdevjava.constant.HeaderConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class MDCFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String correlationId = request.getHeader(HeaderConstant.CORRELATION_ID);
        if(StringUtils.isBlank(correlationId)){
            correlationId = UUID.randomUUID().toString();
        }
        MDC.put(HeaderConstant.CORRELATION_ID, correlationId);
        try {
            filterChain.doFilter(request, response);
        }finally {
            MDC.clear();
        }
    }
}
