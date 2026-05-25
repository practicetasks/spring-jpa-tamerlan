package com.example.springjpatamerlan.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
//@ToString

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

//    @OneToMany(mappedBy = "category")
//    private List<Product> productList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
//    private List<Attribute> attributes = new ArrayList<>();
}
