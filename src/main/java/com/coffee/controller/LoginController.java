package com.coffee.controller;

import com.coffee.config.JwtTokenProvider;
import com.coffee.constant.Role;
import com.coffee.dto.LoginRequest;
import com.coffee.dto.LoginResponse;
import com.coffee.entity.Member;
import com.coffee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        // request 로그인 요청 객체
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // DB에서 회원 조회
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("회원 정보가 없습니다."));

        String token = jwtTokenProvider.createToken(request.getEmail());

        // 실제로는 DB에서 Member 조회하는 것이 더 정확함
        Role role = Role.USER ;//.toString();

        System.out.println("로그인 성공");
        System.out.println("토큰 : " + token);
        String email = request.getEmail() ;
        System.out.println("이메일 : " + email);

        return new LoginResponse(
                token, member.getName(), email, role
        );
    }
}