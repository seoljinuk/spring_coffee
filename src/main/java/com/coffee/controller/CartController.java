package com.coffee.controller;

import com.coffee.dto.CartProductDto;
import com.coffee.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService ;

    @PostMapping("/insert")
    public ResponseEntity<String> addToCart(@RequestBody CartProductDto dto) {
        String message = cartService.addProductToCart(dto);
        return ResponseEntity.ok(message);
    }

}
