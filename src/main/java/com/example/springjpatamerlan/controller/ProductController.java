package com.example.springjpatamerlan.controller;

import com.example.springjpatamerlan.dto.ProductDto;
import com.example.springjpatamerlan.dto.ProductMapper;
import com.example.springjpatamerlan.model.Category;
import com.example.springjpatamerlan.model.Product;
import com.example.springjpatamerlan.repository.CategoryRepository;
import com.example.springjpatamerlan.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> dtoList = new ArrayList<>();

        for (Product product : products) {
            dtoList.add(productMapper.toDto(product));
        }
        return dtoList;
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return productMapper.toDto(product);
    }

    @PostMapping
    public Product create(@RequestBody Product product, @RequestParam int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        product.setCategory(category);
        productRepository.save(product);
        return product;
    }
}
