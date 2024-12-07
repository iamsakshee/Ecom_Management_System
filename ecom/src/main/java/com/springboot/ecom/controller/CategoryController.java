package com.springboot.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.model.Category;
import com.springboot.ecom.service.CategoryService;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category/getAll")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/category/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(savedCategory);
    }
}
