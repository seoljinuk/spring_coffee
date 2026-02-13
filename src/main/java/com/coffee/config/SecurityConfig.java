package com.coffee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean // 비밀번호는 반드시 암호화 상태여야 합니다
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
