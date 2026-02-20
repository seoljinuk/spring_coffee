package com.coffee.controller;

import com.coffee.dto.CartItemDto;
import com.coffee.dto.CartProductDto;
import com.coffee.entity.Member;
import com.coffee.service.CartService;
import com.coffee.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final MemberService memberService ;

    @PostMapping("/insert")
    public ResponseEntity<String> addToCart(
            @RequestBody CartProductDto dto,
            Authentication authentication
    ) {
        String email = authentication.getName(); // JWT에서 꺼낸 사용자
        String message = cartService.addProductToCart(dto, email);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CartItemDto>> getCartProducts(Authentication authentication) {

        String email = authentication.getName();

        Member member = memberService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        return ResponseEntity.ok(
                cartService.getCartItemsByMemberId(member.getId())
        );
    }
}
