package com.coffee.controller;

import com.coffee.config.JwtTokenProvider;
import com.coffee.constant.Role;
import com.coffee.dto.LoginRequest;
import com.coffee.dto.LoginResponse;
import com.coffee.entity.Member;
import com.coffee.repository.MemberRepository;
import com.coffee.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

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
        //Role role = Role.USER ;//.toString();

        Role role = member.getRole();

        System.out.println("로그인 성공");
        System.out.println("토큰 : " + token);
        String email = request.getEmail() ;
        System.out.println("이메일 : " + email);

        return new LoginResponse(
                token, member.getName(), email, role
        );
    }

    private final MemberService memberService ;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody Member bean, BindingResult bindingResult) {
        // 1) 유효성 검사 결과 확인
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        // 2) 이메일 중복 체크
        Optional<Member> member = memberService.findByEmail(bean.getEmail());
        System.out.println("회원 가입 정보 : " + member);
        if (member.isPresent()) {
            return new ResponseEntity<>(Map.of("email", "이미 존재하는 이메일 주소입니다."), HttpStatus.BAD_REQUEST);
        }

        // 3) 회원가입 처리
        memberService.insert(bean);
        return new ResponseEntity<>("회원 가입 성공", HttpStatus.OK);
    }

}