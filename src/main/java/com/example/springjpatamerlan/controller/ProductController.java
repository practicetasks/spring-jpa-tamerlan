package com.example.springjpatamerlan.controller;

import com.example.springjpatamerlan.dto.ProductDto;
import com.example.springjpatamerlan.dto.ProductFullDto;
import com.example.springjpatamerlan.dto.ProductMapper;
import com.example.springjpatamerlan.model.Category;
import com.example.springjpatamerlan.model.Product;
import com.example.springjpatamerlan.model.ProductAttribute;
import com.example.springjpatamerlan.repository.CategoryRepository;
import com.example.springjpatamerlan.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    private final Set<String> fields = Set.of("id", "name", "price");

    @GetMapping
    public List<ProductDto> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam String sortField,
                                    @RequestParam String sortDirection,
                                    @RequestParam double min,
                                    @RequestParam double max) {

        if (!fields.contains(sortField)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Sort sort = sortDirection.equals("desc")
                ? Sort.by(Sort.Direction.DESC, sortField)
                : Sort.by(sortField);

        Pageable pageable = PageRequest.of(page, size, sort);
        List<Product> products = productRepository.findAllByPriceBetween(min, max, pageable);
        List<ProductDto> dtoList = new ArrayList<>();

        for (Product product : products) {
            dtoList.add(productMapper.toDto(product));
        }
        return dtoList;
    }

    @GetMapping("/{id}")
    public ProductFullDto findById(@PathVariable int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ProductFullDto productFullDto = new ProductFullDto();
        productFullDto.setId(product.getId());
        productFullDto.setName(product.getName());
        productFullDto.setPrice(product.getPrice());
        productFullDto.setCategory(product.getCategory().getName());

        Map<String, String> attributes = new HashMap<>();

        for (ProductAttribute p : product.getProductAttributes()) {
            attributes.put(p.getAttribute().getName(), p.getValue());
        }
        productFullDto.setAttributes(attributes);
        return productFullDto;
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
