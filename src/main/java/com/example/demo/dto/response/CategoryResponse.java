package com.example.demo.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    private Long id;
    private String name;
    private Integer price;
    private List<String> images;
}
