package com.example.springjpatamerlan.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryCreateDto {
    private String name;
    private List<String> attributes;
}
