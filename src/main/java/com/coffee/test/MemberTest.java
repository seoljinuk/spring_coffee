package com.coffee.test;

import com.coffee.constant.Role;
import com.coffee.entity.Member;
import com.coffee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@RequiredArgsConstructor
public class MemberTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void insertMember() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Member member = new Member();
        member.setEmail("bluesky@naver.com");
        member.setPassword(passwordEncoder.encode("bluesky@456")); // 암호화
        member.setRole(Role.USER); // enum 타입이라 가정
        member.setName("aa");
        member.setAddress("bb");

        memberRepository.save(member);

        System.out.println("테스트 회원 생성 완료");

        System.out.println(encoder.encode("1234"));
        // [$2a$10$utrireEWqMN0hyEmPBas5e/OmfUNYnHjwWIwMufoFz7ZrxKV1IDDi]
    }
}
