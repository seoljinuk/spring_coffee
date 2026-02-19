package com.coffee.service;

import com.coffee.entity.Product;
import com.coffee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository ;

    public List<Product> getProductList() {
        return this.productRepository.findProductByOrderByIdDesc();
    }

    public boolean deleteProduct(Long id) {
        // existsById() 메소드와 deleteById() 메소드는 CrudRepository에 포함되어 있습니다.
        if (productRepository.existsById(id)) { // 해당 항목이 존재하면
            this.productRepository.deleteById(id); // 삭제하기
            return true; // true의 의미는 "삭제 성공" 했습니다.

        } else { // 존재하지 않으면
            return false;
        }
    }
}