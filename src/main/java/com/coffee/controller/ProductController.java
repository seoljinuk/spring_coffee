package com.coffee.controller;

import com.coffee.entity.Product;
import com.coffee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService ;

    @GetMapping("/list") // 상품 목록을 List 컬렉션으로 반환해 줍니다.
    public List<Product> list(){
        List<Product> products = this.productService.getProductList() ;

        return products ;
    }

    // 클라이언트가 특정 상품 id에 대하여 "삭제" 요청을 하였습니다.
    // @PathVariable는 URL의 경로 변수를 메소드의 매개 변수로 값을 전달해 줍니다.
    @DeleteMapping("/delete/{id}") // {id}는 경로 변수라고 하며, 가변 매개 변수로 이해하면 됩니다.
    public ResponseEntity<String> delete(@PathVariable Long id){ // {id}으로 넘겨온 상품의 아이디가, 변수 id에 할당됩니다.
        try{
            boolean isDeleted = this.productService.deleteProduct(id);

            if(isDeleted){
                return ResponseEntity.ok(id + "번 상품이 삭제 되었습니다.");
            }else{
                return ResponseEntity.badRequest().body(id + "번 상품이 존재하지 않습니다.");
            }
        }catch (DataIntegrityViolationException err){
            String message = "해당 상품은 장바구니에 포함되어 있거나, 이미 매출이 발생한 상품입니다.\n확인해 주세요.";
            return ResponseEntity.badRequest().body(message);

        }catch (Exception err){
            return ResponseEntity.internalServerError().body("오류 발생 : " + err.getMessage());
        }
    }
}
