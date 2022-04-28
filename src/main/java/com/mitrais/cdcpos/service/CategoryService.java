package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.CategoryDto;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.exception.ResourceNotFoundException;
import com.mitrais.cdcpos.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryEntity add(@NotNull CategoryDto req){
        CategoryEntity category = new CategoryEntity();
        category.setName(req.getName());
        return categoryRepository.save(category);
    }

    public CategoryEntity getById(UUID id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    public List<CategoryEntity> getAll(){
        return categoryRepository.findAll();
    }

    public List<CategoryEntity> getActiveCategory(){
        return categoryRepository.findByDeletedAtIsNull();
    }

    public CategoryEntity update(UUID id, @NotNull CategoryDto req){
        var category = getById(id);
        category.setName(req.getName());
        return categoryRepository.save(category);
    }

    // Soft delete category
    public CategoryEntity delete(UUID id){
        var category = getById(id);
        category.setDeletedAt(LocalDateTime.now());
        return categoryRepository.save(category);
    }




}
