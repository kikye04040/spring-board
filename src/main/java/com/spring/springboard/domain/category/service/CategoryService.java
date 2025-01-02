package com.spring.springboard.domain.category.service;

import com.spring.springboard.domain.category.entity.Category;
import com.spring.springboard.domain.category.repository.CategoryRepository;
import com.spring.springboard.domain.common.enums.ErrorStatus;
import com.spring.springboard.domain.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Category createCategory(String name, String description) {
        if (categoryRepository.findByName(name).isPresent()) {
            throw new ApiException(ErrorStatus.CATEGORY_ALREADY_EXISTS);
        }

        Category category = new Category(name, description);

        return categoryRepository.save(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Category updateCategory(Long categoryId, String name, String description) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_CATEGORY));

        // 엔티티의 update 메서드 호출
        category.update(name, description);

        return categoryRepository.save(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_CATEGORY));

        categoryRepository.delete(category);
    }
}
