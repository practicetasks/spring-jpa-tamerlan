package com.example.springjpatamerlan.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ProductFullDto {
    private Integer id;
    private String name;
    private Double price;
    private String category;
    private Map<String, String> attributes;
}
