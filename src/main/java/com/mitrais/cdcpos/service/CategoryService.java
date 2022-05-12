package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.CategoryDto;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.exception.ResourceNotFoundException;
import com.mitrais.cdcpos.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryEntity add( CategoryDto req){
        CategoryEntity category = new CategoryEntity();
        category.setName(req.getName());
        return categoryRepository.save(category);
    }

    public CategoryEntity getById(UUID id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    public CategoryEntity getActiveDataById(UUID id){
        return categoryRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    public CategoryEntity getActiveDataByName(String name){
        return categoryRepository.findByNameIgnoreCaseAndDeletedAtIsNull(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", name));
    }

    public Page<CategoryEntity> getActiveCategory(Pageable pageable){
        return categoryRepository.findByDeletedAtIsNull(pageable);
    }

    public Page<CategoryEntity> getAll(boolean paginated, int page, int size,
                                       String searchVal, String sortBy,String sortDirection){
        Sort sort;
        Pageable paging;
        Page<CategoryEntity> result;

        if("DESC".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        if(paginated){
            paging = PageRequest.of(page, size,sort);
            result = categoryRepository.findAllSearch(paging, searchVal);
        }else{
            List<CategoryEntity> list = categoryRepository.findAllSearch(sort, searchVal);
            result = new PageImpl<>(list);
        }
        return result;
    }

    public CategoryEntity update(UUID id, CategoryDto req){
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

    public boolean isCategoryExist(String name){
        return categoryRepository.existsByName(name);
    }
}
