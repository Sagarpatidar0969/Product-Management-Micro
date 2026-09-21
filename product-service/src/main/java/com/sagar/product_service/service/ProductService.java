package com.sagar.product_service.service;


import com.sagar.product_service.dto.ProductRequest;
import com.sagar.product_service.dto.ProductResponse;
import com.sagar.product_service.entity.Product;

import java.util.List;

public interface ProductService {

    Product getProduct(Long productId);
}
