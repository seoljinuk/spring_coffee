package com.coffee.dto;

import com.coffee.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse { // 로그인 응답 객체"
    private String accessToken;
    private String name;
    private String email;
    private Role role;
}