package com.example.springjpatamerlan.repository;

import com.example.springjpatamerlan.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
