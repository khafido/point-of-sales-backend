package com.mitrais.cdcpos.service;


import com.mitrais.cdcpos.dto.CategoryDto;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        Page<CategoryEntity> pageCategories = new PageImpl<>(categoryList);
        when(repo.findAll(Pageable.unpaged())).thenReturn((pageCategories));

        Page<CategoryEntity> result = service.getAll(Pageable.unpaged());
        assertArrayEquals(categoryList.toArray(), result.getContent().toArray());
        verify(repo).findAll(Pageable.unpaged());
    }

    @Test
    public void getCategoryPaged(){
        Pageable paging = PageRequest.of(1,1);
        PagedListHolder<CategoryEntity> pagedListCategory = new PagedListHolder<>(categoryList);
        pagedListCategory.setPageSize(1);
        pagedListCategory.setPage(1);
        Page<CategoryEntity> pageCategory = new PageImpl<>(pagedListCategory.getPageList(),paging, 1);
        when(repo.findAll(paging)).thenReturn(pageCategory);

        Page<CategoryEntity> result = service.getAll(PageRequest.of(1,1));
        assertEquals(categoryList.get(1), result.getContent().toArray()[0]);
        verify(repo).findAll(paging);
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

}
