package com.example.springjpatamerlan.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryResponseDto {
    private Integer id;
    private String name;
    private List<AttributeDto> attributes;
}
