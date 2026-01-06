package com.rudyk.kitchenbar.services;

import com.rudyk.kitchenbar.api.dto.CreateProduct;
import com.rudyk.kitchenbar.api.dto.ProductResponse;
import com.rudyk.kitchenbar.domain.model.Product;
import com.rudyk.kitchenbar.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse createProduct(CreateProduct dto) {

        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setDescription(dto.getDescription());

        Product saved = productRepository.save(product);

        return new ProductResponse(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getPrice(),
                saved.getCategory()
        );
    }
    public List<ProductResponse> getProducts() {
       List<Product> products = productRepository.findAll();
       List<ProductResponse> productResponses = new ArrayList<>();
       for (Product product : products) {
           productResponses.add(new ProductResponse(product.getId(),
                   product.getName(),
                   product.getDescription(),
                   product.getPrice(),
                   product.getCategory()));
       }
        return productResponses;

    }
}