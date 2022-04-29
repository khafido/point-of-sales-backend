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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CategoryServiceTest {
    @InjectMocks
    CategoryService service;

    @Mock
    CategoryRepository repo;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createCategory(){
        when(repo.count()).thenReturn(1L);
        long userCount = repo.count();
        Assertions.assertEquals(1L, userCount);
        verify(repo).count();
    }
}
