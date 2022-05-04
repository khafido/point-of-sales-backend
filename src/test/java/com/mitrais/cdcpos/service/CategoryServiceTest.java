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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository repo;

    @InjectMocks
    private CategoryService service;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveCategorySuccessfully(){
        final CategoryEntity category = new CategoryEntity(null,"Category 1");
        given(repo.findByName(category.getName())).willReturn(Optional.empty());
        given(repo.save(category)).willAnswer(InvocationOnMock::getArguments);

        CategoryDto req = new CategoryDto();
        req.setName(category.getName());
        CategoryEntity savedCategory = service.add(req);

        assertThat(savedCategory).isNotNull();
        verify(repo).save(any(CategoryEntity.class));
    }

    @Test
    public void shouldThrowErrorWhenSaveCategoryWithExistingName(){
        final CategoryEntity category = new CategoryEntity(UUID.randomUUID(),"Category 1");
        given(repo.findByName(category.getName())).willReturn(Optional.of(category));
        assertThrows(DataIntegrityViolationException.class, () -> {
            service.add(new CategoryDto(category.getName()));
        });
        verify(repo,never()).save(any(CategoryEntity.class));
    }

    @Test
    public void testGetActiveCategory(){
        when(repo.count()).thenReturn(10L);
        long userCount = repo.count();
        Assertions.assertEquals(10L, userCount);
        verify(repo).count();
    }
}
