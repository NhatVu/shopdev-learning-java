package com.learning.shopdevjava.controller;

import com.learning.shopdevjava.config.MongoConfig;
import com.learning.shopdevjava.constant.HeaderConstant;
import com.learning.shopdevjava.entity.UserEntity;
import com.learning.shopdevjava.filter.APIKeyInterceptor;
import com.learning.shopdevjava.filter.AuthenticationInterceptor;
import com.learning.shopdevjava.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith({SpringExtension.class})
@WebMvcTest(Section3.class)
@Import({MongoConfig.class}) // main point is to include MongoConfig, and test profile for testing
@ActiveProfiles("test")
class Section3Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    APIKeyInterceptor APIKeyInterceptor;

    @MockBean
    AuthenticationInterceptor authenticationInterceptor;

    @BeforeEach
    void initTest() throws Exception {
//        doCallRealMethod().when(APIKeyInterceptor).preHandle(any(), any(), any());
//        doCallRealMethod().when(authenticationInterceptor).preHandle(any(), any(), any());

        when(APIKeyInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        when(authenticationInterceptor.preHandle(any(), any(), any())).thenReturn(true);
//        // other stuff
    }

    @Test
    public void testInsert() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .name("nhat")
                .build();
        when(userRepository.insert(any(UserEntity.class))).thenReturn(userEntity);
        MvcResult mvcResult = mockMvc.perform(get("/section3/insert")
                        .param("name", "nhat")
                        .header(HeaderConstant.API_KEY, "api-key-test"))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println("response: " + mvcResult.getResponse().getContentAsString());
    }




}