package com.jeongho.jpashop;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "pjh0904", roles = "USER", password = "123")
public @interface WithUser {
}
