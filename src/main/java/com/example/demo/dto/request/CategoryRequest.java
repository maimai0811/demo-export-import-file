package com.example.demo.dto.request;

import java.util.List;

import com.example.demo.annotation.CustomValidation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    private Long id;

    @CustomValidation
    private String name;
    private Integer price;
    private List<String> images;
}
