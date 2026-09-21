package com.sagar.product_service.service.impl;

import com.sagar.product_service.dto.ProductRequest;
import com.sagar.product_service.dto.ProductResponse;
import com.sagar.product_service.entity.Product;
import com.sagar.product_service.repository.ProductRepository;
import com.sagar.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    public Product getProduct(Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found: " + productId));
    }
}
