package com.jeongho.jpashop.controller;

import com.jeongho.jpashop.WithUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    public void index_익명() throws Exception{
        // 401은 익명의 사용자, 403는 로그인은 하였으나 권한이 없는 사용자
        // isForbidden()이 먹히지않는다. isForbidden()은 403을 반환함
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    
    @Test
    @WithUser
    public void index_유저() throws Exception{
        // 이미 로그인 한 유저라고 가정
        mockMvc.perform(get("/dashboard"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void index_관리자() throws Exception{
        // 이미 로그인 한 유저라고 가정
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}