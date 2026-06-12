package com.example.springjpatamerlan.repository;

import com.example.springjpatamerlan.model.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select c from Category c left join fetch c.attributes")
    List<Category> findAllWithAttributes();

    @EntityGraph(attributePaths = "attributes")
    List<Category> findAll();

    @EntityGraph(attributePaths = "attributes")
    @Query("select c from Category c")
    List<Category> findAllWithAttributesEntityGraph();

    Category findByName(String name); // select * from categories where name = ?

    Category findByNameIgnoreCase(String name); // select * from categories where lower(name) = lower(?)

    Category findByNameContaining(String name); // select * from categories where name like '%науш%'

    List<Category> findByNameContainingIgnoreCase(String name); // select * from categories where lower(name) like lower('%науш%')


}


// GET /products/test?from=100000&to=200000 - получить товары от 100 000 до 200 000