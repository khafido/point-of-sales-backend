package com.mitrais.cdcpos.service;


import com.mitrais.cdcpos.dto.CategoryDto;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.repository.CategoryRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository repo;

    @InjectMocks
    private CategoryService service;

//    @BeforeEach
//    public void init(){
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    public void shouldSaveCategorySuccessfully(){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("Category 1");
        when(repo.save(any())).thenReturn(categoryEntity);

        CategoryDto dto = new CategoryDto();
        dto.setName("Category 1");
        var createdCategory = service.add(dto);

        assertEquals(categoryEntity.getName(),createdCategory.getName());
        verify(repo).save(any());
    }


}
