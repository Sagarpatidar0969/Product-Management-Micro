package com.sagar.product_service.controller;

import com.sagar.product_service.dto.ProductRequest;
import com.sagar.product_service.dto.ProductResponse;
import com.sagar.product_service.entity.Product;
import com.sagar.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;




    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {

        return productService.getProduct(id);
    }
}