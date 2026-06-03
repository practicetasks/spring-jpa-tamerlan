package com.example.springjpatamerlan.controller;

import com.example.springjpatamerlan.dto.AttributeDto;
import com.example.springjpatamerlan.dto.CategoryCreateDto;
import com.example.springjpatamerlan.dto.CategoryResponseDto;
import com.example.springjpatamerlan.model.Attribute;
import com.example.springjpatamerlan.model.Category;
import com.example.springjpatamerlan.repository.AttributeRepository;
import com.example.springjpatamerlan.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final AttributeRepository attributeRepository;

    @GetMapping
    public List<Category> findAll() {
        return categoryRepository.findAll(); // select * from categories
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable int id) {
//        Optional<Category> optional = categoryRepository.findById(id);
//
//        if (optional.isPresent()) {
//            Category category = optional.get();
//            return category;
//        }
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public CategoryResponseDto create(@RequestBody CategoryCreateDto createDto) {
        Category category = new Category();
        category.setName(createDto.getName());

        categoryRepository.save(category);

        for (String attributeName : createDto.getAttributes()) {
            Attribute attribute = new Attribute();
            attribute.setName(attributeName);
            attribute.setCategory(category);
            attributeRepository.save(attribute);
            category.getAttributes().add(attribute);
        }

        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setId(category.getId());
        responseDto.setName(category.getName());

        List<AttributeDto> attributeDtos = new ArrayList<>();
        for (Attribute attribute : category.getAttributes()) {
            AttributeDto attributeDto = new AttributeDto();
            attributeDto.setId(attribute.getId());
            attributeDto.setName(attribute.getName());
            attributeDtos.add(attributeDto);
        }

        responseDto.setAttributes(attributeDtos);
        return responseDto;
    }

    @PutMapping
    public Category update(@RequestBody Category updatingCategory, @RequestParam int id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        existingCategory.setName(updatingCategory.getName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        categoryRepository.deleteById(id); // delete from categories where id = ?
    }

    @GetMapping("/test")
    public List<Category> test(@RequestParam String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }
}
