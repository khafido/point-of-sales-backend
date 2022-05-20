package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.ParameterDto;
import com.mitrais.cdcpos.entity.ParameterEntity;
import com.mitrais.cdcpos.repository.ParameterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.query.Param;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParameterServiceTest {
    @Mock
    ParameterRepository parameterRepository;

    @InjectMocks
    ParameterService parameterService;

    private List<ParameterEntity> parameterList;
    @BeforeEach
    public void init(){
        ParameterEntity parameter1 = new ParameterEntity(UUID.randomUUID(), "tax_percentage", "10");
        ParameterEntity parameter2 = new ParameterEntity(UUID.randomUUID(), "profit_percentage", "5");

        parameterList = new ArrayList<>(Arrays.asList(parameter1, parameter2));
    }

    @Test
    public void createParameter(){
        // Given
        ParameterEntity parameter = new ParameterEntity();
        when(parameterRepository.save(any(ParameterEntity.class))).thenReturn(parameter);
        // When
        ParameterDto dto = new ParameterDto();
        dto.setName("tax_percentage");
        dto.setValue("10");
        ParameterEntity savedParameter = parameterService.add(dto);
        // Then
        assertNotNull(savedParameter.getCreatedAt());
        verify(parameterRepository).save(any(ParameterEntity.class));
    }

    @Test
    public void getAllParameter(){
        when(parameterRepository.findAll()).thenReturn(parameterList);
        List<ParameterEntity> result = parameterService.get();
        for(int i=0;i<=1;i++){
            assertSame(parameterList.get(i),result.get(i));
        }

        verify(parameterRepository).findAll();
    }

    @Test
    public void getParameterById(){
        Optional<ParameterEntity> parameter = Optional.of(parameterList.get(0));
        when(parameterRepository.findById(parameterList.get(0).getId())).thenReturn(parameter);

        ParameterEntity result = parameterService.getById(parameterList.get(0).getId());
        assertEquals(parameterList.get(0), result);
        verify(parameterRepository).findById(parameterList.get(0).getId());
    }

    @Test
    public void getParameterByName(){
        Optional<ParameterEntity> parameter = Optional.of(parameterList.get(0));
        when(parameterRepository.findByNameIgnoreCase(anyString())).thenReturn(parameter);

        ParameterEntity result = parameterService.getByName("tax");
        assertEquals(parameterList.get(0), result);
        verify(parameterRepository).findByNameIgnoreCase(anyString());
    }

    @Test
    public void updateParameter(){
        Optional<ParameterEntity> parameter = Optional.of(parameterList.get(0));
        when(parameterRepository.findById(parameterList.get(0).getId())).thenReturn(parameter);
        when(parameterRepository.save(any(ParameterEntity.class))).thenReturn(parameterList.get(0));

        ParameterDto dto = new ParameterDto();
        dto.setName("tax_percentage");
        dto.setValue("66");
        ParameterEntity result = parameterService.update(parameterList.get(0).getId(), dto);

        assertEquals(parameterList.get(0).getValue(), result.getValue());
        verify(parameterRepository).findById(parameterList.get(0).getId());
        verify(parameterRepository).save(any(ParameterEntity.class));
    }

}
