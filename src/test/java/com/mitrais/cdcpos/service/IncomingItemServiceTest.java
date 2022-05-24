package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.IncomingItemDto;
import com.mitrais.cdcpos.dto.IncomingItemResponseDto;
import com.mitrais.cdcpos.entity.item.IncomingItemEntity;
import com.mitrais.cdcpos.entity.item.SupplierEntity;
import com.mitrais.cdcpos.entity.store.StoreItemEntity;
import com.mitrais.cdcpos.repository.IncomingItemRepository;
import com.mitrais.cdcpos.repository.StoreItemRepository;
import com.mitrais.cdcpos.repository.SupplierRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IncomingItemServiceTest {
    @Mock
    private IncomingItemRepository incomingItemRepository;
    @Mock
    private StoreItemRepository storeItemRepository;
    @Mock
    private SupplierRepository supplierRepository;
    
    @InjectMocks
    private IncomingItemService incomingItemService;
    
    private List<IncomingItemEntity> listItem;
    @BeforeEach
    public void init(){
        IncomingItemEntity item1 = mock(IncomingItemEntity.class);
        IncomingItemEntity item2 = mock(IncomingItemEntity.class);
        IncomingItemEntity item3 = mock(IncomingItemEntity.class);
        listItem = new ArrayList<>(Arrays.asList(item1,item2,item3));
    }

    @Test
    public void addIncomingItem(){
        var storeItem = mock(StoreItemEntity.class);
        var supplier = mock(SupplierEntity.class);

        when(storeItemRepository.findById(storeItem.getId())).thenReturn(Optional.of(storeItem));
        when(supplierRepository.findByIdAndDeletedAtIsNull(supplier.getId())).thenReturn(Optional.of(supplier));
        when(storeItemRepository.save(any(StoreItemEntity.class))).thenReturn(storeItem);
        when(incomingItemRepository.save(any(IncomingItemEntity.class))).thenReturn(listItem.get(0));

        var incomingDto = new IncomingItemDto(storeItem.getId(),supplier.getId(),new BigDecimal(12000)
                ,100,LocalDateTime.now(), LocalDate.now().plusYears(5));
        var result = incomingItemService.add(incomingDto);
        assertEquals(listItem.get(0),result);

        verify(storeItemRepository).findById(storeItem.getId());
        verify(supplierRepository).findByIdAndDeletedAtIsNull(supplier.getId());
        verify(storeItemRepository).save(any(StoreItemEntity.class));
        verify(incomingItemRepository).save(any(IncomingItemEntity.class));

    }
    
//    @Test
//    public void getIncomingItemNotPaged(){
//        when(incomingItemRepository.findAllSearch((Sort) any(),anyString(),
//                any(), any())).thenReturn(listItem);
//
//        Page<IncomingItemResponseDto> result = incomingItemService.getAll(
//                false,0,3,"","storeItem.item.name", "asc", LocalDateTime.now().minusYears(10),LocalDateTime.now());
//        List<IncomingItemResponseDto> items = listItem.stream().map(IncomingItemResponseDto::toDto).collect(Collectors.toList());
//        for(int i=0; i<=2;i++){
//            assertSame(listItem.get(i).getBuyDate(),result.getContent().get(i).getBuyDate());
//        }
//
//        verify(incomingItemRepository).findAllSearch((Sort) any(),anyString(),
//                LocalDateTime.now().minusYears(10), LocalDateTime.now());
//    }
    
}
