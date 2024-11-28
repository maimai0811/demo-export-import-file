package com.example.demo.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String name;
    private Integer price;
    private List<String> images;
}
