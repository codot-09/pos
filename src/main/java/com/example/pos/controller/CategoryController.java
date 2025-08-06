package com.example.pos.controller;

import com.example.pos.dto.ApiResponse;
import com.example.pos.entity.Category;
import com.example.pos.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Category",description = "Kategoriyalar bilan ishlash")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addCategory(@RequestParam String name){
        return ResponseEntity.ok(categoryService.addCategory(name));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAll(){
        return ResponseEntity.ok(categoryService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id){
        return ResponseEntity.ok(categoryService.delete(id));
    }
}
