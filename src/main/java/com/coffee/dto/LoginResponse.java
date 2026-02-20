package com.coffee.dto;

import com.coffee.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse { // 로그인 응답 객체"
    //private Long id; // 필수는 아님
    private String accessToken;
    private String name;

    // 네, 보통 JWT 안에는 email(또는 username)이 들어갑니다.
    private String email;
    private Role role;
}