package com.springboot.ecom.controller;

import com.springboot.ecom.model.Category;
import com.springboot.ecom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category/getAll")
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/category/create")
    public ResponseEntity<?> createCategory(@RequestBody Category category) throws Exception {
        Category savedCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(savedCategory);
    }
}
