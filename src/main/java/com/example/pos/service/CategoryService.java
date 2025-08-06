package com.example.pos.service;

import com.example.pos.dto.ApiResponse;
import com.example.pos.entity.Category;
import com.example.pos.exception.DataNotFoundException;
import com.example.pos.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ApiResponse<String> addCategory(String name){
        categoryRepository.save(
                Category.builder()
                        .name(name)
                        .build()
        );

        return ApiResponse.success("Kategoriya saqlandi");
    }

    public ApiResponse<List<Category>> getAll(){
        return ApiResponse.success(categoryRepository.findAll());
    }

    public ApiResponse<String> delete(UUID id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Kategoriya topilmadi"));

        category.setActive(false);
        categoryRepository.save(category);

        return ApiResponse.success("Kategorya o'chirildi");
    }
}
