package com.coffee.service;

import com.coffee.entity.Product;
import com.coffee.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
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

    // import org.springframework.beans.factory.annotation.Value;
    // 상품 등록하기
    @Value("${productImageLocation}")
    private String productImageLocation; // 기본 값 : null

    // Base64 인코딩 문자열을 변환하여 이미지로 만들고, 저장해주는 메소드입니다.
    private String saveProductImage(String base64Image) {
        // 데이터 베이스와 이미지 경로에 저장될 이미지의 이름
        // 현재 시각을 '년월일시분' 포맷으로 변환 (예: 202510171430)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String formattedNow = LocalDateTime.now().format(formatter);

        // 데이터 베이스와 이미지 경로에 저장될 이미지의 이름
        String imageFileName = "product_" + formattedNow + ".jpg";

        // String 클래스 공부 : endsWith(), split() 메소드

        File imageFile = new File(productImageLocation  + imageFileName);

        // base64Image : JavaScript FileReader API에 만들어진 이미지입니다.
        // 메소드 체이닝 : 점을 연속적으로 찍어서 메소드를 계속 호출하는 것
        byte[] decodedImage = Base64.getDecoder().decode(base64Image.split(",")[1]);

        // FileOutputStream는 바이트 파일을 처리해주는 자바의 Stream 클래스
        // 파일 정보를 byte 단위로 변환하여 이미지를 복사합니다.
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            fos.write(decodedImage);
            return imageFileName;
        } catch (Exception e) {
            throw new IllegalStateException("이미지 파일 저장 중 오류가 발생했습니다.");
        }
    }

    public Product insertProduct(Product product) {
        if (product.getImage() != null && product.getImage().startsWith("data:image")) {
            String imageFileName = saveProductImage(product.getImage());
            product.setImage(imageFileName);
        }

        product.setInputdate(LocalDate.now());
        System.out.println("서비스)상품 등록 정보");
        System.out.println(product);

        // save() 메소드는 CrudRepository에 포함되어 있습니다.
        return productRepository.save(product);
    }
}