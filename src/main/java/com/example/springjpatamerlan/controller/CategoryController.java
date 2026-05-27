package com.example.springjpatamerlan.controller;

import com.example.springjpatamerlan.model.Category;
import com.example.springjpatamerlan.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

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
    public Category create(@RequestBody Category category) {
        return categoryRepository.save(category); // id == null ? insert : update
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
