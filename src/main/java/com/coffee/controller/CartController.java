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

    //    @GetMapping("/list/{memberId}")// 특정 사용자의 `카트 상품` 목록을 조회합니다.
//    public ResponseEntity<List<CartItemDto>> getCartProducts(@PathVariable Long memberId) {
//        try {
//            List<CartItemDto> cartProducts = cartService.getCartItemsByMemberId(memberId);
//            System.out.println("카트 상품 개수 : " + cartProducts.size());
//            return ResponseEntity.ok(cartProducts);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
    @GetMapping("/list/{memberId}")
    public ResponseEntity<List<CartItemDto>> getCartProducts(
            @PathVariable Long memberId,
            Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<Member> loginMemberOpt = memberService.findByEmail(email);

        if (loginMemberOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Member loginMember = loginMemberOpt.get();

        if (!loginMember.getId().equals(memberId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<CartItemDto> cartProducts = cartService.getCartItemsByMemberId(memberId);

        return ResponseEntity.ok(cartProducts);
    }
}
