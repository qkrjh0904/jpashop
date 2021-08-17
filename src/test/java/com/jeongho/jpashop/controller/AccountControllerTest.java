package com.jeongho.jpashop.controller;

import com.jeongho.jpashop.WithUser;
import com.jeongho.jpashop.domain.Account;
import com.jeongho.jpashop.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountService accountService;

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

    @Test
    @Transactional
    public void 로그인() throws Exception{
        // given
        String userName = "pjh0904";
        String password = "123";
        String role = "USER";
        Account account = this.createUser(userName, password, role);

        // when & result
        mockMvc.perform(formLogin().user(userName).password(password))
                .andExpect(authenticated());
    }

    @Test
    @Transactional      // 테스트간 독립적으로
    public void 로그인2() throws Exception{
        // given
        String userName = "pjh0904";
        String password = "123";
        String role = "USER";
        Account account = this.createUser(userName, password, role);

        // when & result
        mockMvc.perform(formLogin().user(userName).password(password))
                .andExpect(authenticated());
    }

    private Account createUser(String username, String password, String role) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole(role);
        return accountService.createAccount(account);
    }

}