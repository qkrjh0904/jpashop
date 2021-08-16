package com.jeongho.jpashop.controller;

import com.jeongho.jpashop.domain.Account;
import com.jeongho.jpashop.repository.AccountRepository;
import com.jeongho.jpashop.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @GetMapping("/account/{role}/{username}/{password}")
    public Account createMember(@ModelAttribute Account account) {
        return accountService.createMember(account);
    }
}
