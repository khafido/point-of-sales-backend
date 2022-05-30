package com.mitrais.cdcpos.service;


import com.mitrais.cdcpos.dto.CategoryDto;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository repo;

    @InjectMocks
    private CategoryService service;
    private List<CategoryEntity> categoryList;

    @BeforeEach
    public void init(){
        CategoryEntity category1 = new CategoryEntity(UUID.randomUUID(), "Category 1");
        CategoryEntity category2 = mock(CategoryEntity.class);
        CategoryEntity category3 = mock(CategoryEntity.class);

        categoryList = new ArrayList<>(Arrays.asList(
                category1,category2,category3
        ));
    }

    @Test
    public void getCategoryNotpaged(){
        when(repo.findAllSearch((Sort) any(),anyString())).thenReturn(categoryList);

        Page<CategoryEntity> result = service.getAll(false, 0, 3,"cat","name", "asc");
        for(int i=0; i<=2; i++){
            assertSame(categoryList.get(i), result.getContent().get(i));
        }

        verify(repo).findAllSearch((Sort) any(), anyString());
    }

    @Test
    public void getCategoryPaged(){
        Page<CategoryEntity> pagedCategory = new PageImpl<>(categoryList);
        when(repo.findAllSearch((Pageable) any(),anyString())).thenReturn(pagedCategory);

        var result = service.getAll(true, 0,3, "cat", "name", "asc");
        assertSame(pagedCategory, result);

        verify(repo).findAllSearch((Pageable) any(), anyString());
    }

    @Test
    public void getActiveCategory(){
        Page<CategoryEntity> pageCategories = new PageImpl<>(categoryList);
        when(repo.findByDeletedAtIsNull(Pageable.unpaged())).thenReturn((pageCategories));

        Page<CategoryEntity> result = service.getActiveCategory(Pageable.unpaged());
        assertArrayEquals(categoryList.toArray(), result.getContent().toArray());
        verify(repo).findByDeletedAtIsNull(Pageable.unpaged());
    }

    @Test
    public void getCategoryById(){
        Optional<CategoryEntity> category = Optional.of(categoryList.get(0));
        when(repo.findById(categoryList.get(0).getId())).thenReturn(category);

        CategoryEntity result = service.getById(categoryList.get(0).getId());
        assertEquals(categoryList.get(0), result);
        verify(repo).findById(categoryList.get(0).getId());
    }

    @Test
    public void createCategorySuccessful(){
        CategoryEntity category = new CategoryEntity();
        when(repo.save(any(CategoryEntity.class))).thenReturn(category);
        CategoryDto dto = new CategoryDto();
        dto.setName("Category 1");
        CategoryEntity savedCategory = service.add(dto);
        assertNotNull(savedCategory.getCreatedAt());
        verify(repo).save(any(CategoryEntity.class));
    }

    @Test
    public void updateCategorySuccessful(){
        Optional<CategoryEntity> categories = Optional.of(categoryList.get(0));
        when(repo.findById(categories.get().getId())).thenReturn(categories);
        when(repo.save(any(CategoryEntity.class))).thenReturn(categoryList.get(0));

        CategoryDto dto = new CategoryDto();
        dto.setName("Category 2");
        CategoryEntity updatedCategory = service.update(categoryList.get(0).getId(),dto);

        assertEquals(categoryList.get(0).getName(), updatedCategory.getName());
        verify(repo).findById(categoryList.get(0).getId());
        verify(repo).save(any(CategoryEntity.class));
    }

    @Test
    public void deleteCategorySuccessful(){
        Optional<CategoryEntity> categories = Optional.of(categoryList.get(0));
        when(repo.findById(categories.get().getId())).thenReturn(categories);
        when(repo.save(any(CategoryEntity.class))).thenReturn(categoryList.get(0));

        CategoryEntity deletedCategory = service.delete(categoryList.get(0).getId());
        assertNotNull(deletedCategory.getDeletedAt());
        verify(repo).findById(categoryList.get(0).getId());
        verify(repo).save(any(CategoryEntity.class));
    }

    @Test
    public void checkCategoryExists(){
        when(repo.existsByName(anyString())).thenReturn(true);
        var resultTrue = service.isCategoryExist(categoryList.get(0).getName());
        assertTrue(resultTrue);

        when(repo.existsByName(anyString())).thenReturn(false);
        var resultFalse = service.isCategoryExist("notExist");
        assertFalse(resultFalse);

        verify(repo, Mockito.times(2)).existsByName(anyString());
    }

}
