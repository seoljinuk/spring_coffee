package com.coffee.dto;

import lombok.Getter;

@Getter
public class LoginRequest { // 로그인 요청 객체
    private String email;
    private String password;
}