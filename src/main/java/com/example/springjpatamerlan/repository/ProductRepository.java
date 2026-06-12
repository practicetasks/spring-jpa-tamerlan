package com.example.springjpatamerlan.repository;

import com.example.springjpatamerlan.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {


    @EntityGraph(attributePaths = "category")
    List<Product> findAllByPriceBetween(double min, double max, Pageable pageable);

    @EntityGraph(attributePaths = "category")
    List<Product> findAll();

    @Query("select p from Product p join fetch p.category")
    List<Product> findAllWithCategory();
}
