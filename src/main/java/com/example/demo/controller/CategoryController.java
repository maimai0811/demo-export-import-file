package com.example.demo.controller;

import com.example.demo.dto.request.CategoryRequest;
import com.example.demo.dto.response.CategoryResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @PostMapping()
    public ResponseEntity<CategoryResponse> addCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(new CategoryResponse());
    }
}
