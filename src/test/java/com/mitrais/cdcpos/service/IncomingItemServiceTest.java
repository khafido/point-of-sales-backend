package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.IncomingItemDto;
import com.mitrais.cdcpos.dto.IncomingItemResponseDto;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.entity.item.IncomingItemEntity;
import com.mitrais.cdcpos.entity.item.ItemEntity;
import com.mitrais.cdcpos.entity.item.SupplierEntity;
import com.mitrais.cdcpos.entity.store.StoreEntity;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
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
    
    private final List<StoreEntity> storeList = new ArrayList<>();
    private final List<ItemEntity> itemList = new ArrayList<>();
    private final List<CategoryEntity> categoryList = new ArrayList<>();
    private final List<StoreItemEntity> storeItemList = new ArrayList<>();
    private final List<IncomingItemEntity> incomingItemList = new ArrayList<>();
    private final List<SupplierEntity> supplierList = new ArrayList<>();

    @BeforeEach
    public void init(){
        supplierList.add(new SupplierEntity(UUID.randomUUID(),"sup 1", "sup 1", "0812138132","sup@gmail.com",
                "jalan raya"));
        categoryList.add(new CategoryEntity(UUID.randomUUID(), "makanan"));
        categoryList.add(new CategoryEntity(UUID.randomUUID(), "minuman"));
        storeList.add(new StoreEntity(UUID.randomUUID(),"store", "yogya", null,null,null));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setName("Makanan 1");
        itemEntity.setImage("image");
        itemEntity.setBarcode("091212");
        itemEntity.setCategory(categoryList.get(0));
        itemEntity.setPackaging("wrap");
        itemList.add(itemEntity);

        StoreItemEntity storeItem = new StoreItemEntity();
        storeItem.setStore(storeList.get(0));
        storeItem.setItem(itemList.get(0));
        storeItem.setStock(20);
        storeItem.setFixedPrice(new BigDecimal(20000));
        storeItem.setPriceMode(StoreItemEntity.PriceMode.BY_SYSTEM);
        storeItemList.add(storeItem);

        IncomingItemEntity incomingItem = new IncomingItemEntity();
        incomingItem.setSupplier(supplierList.get(0));
        incomingItem.setBuyQty(120L);
        incomingItem.setBuyPrice(new BigDecimal(1200000));
        incomingItem.setStoreItem(storeItemList.get(0));
        incomingItem.setPricePerItem(new BigDecimal(40000));
        incomingItem.setBuyDate(LocalDateTime.now());
        incomingItem.setExpiryDate(LocalDate.now().plusYears(3));
        incomingItemList.add(incomingItem);

        supplierList.add(new SupplierEntity(UUID.randomUUID(),"sup 1", "sup 1", "0812138132","sup@gmail.com",
                "jalan raya"));
    }

    @Test
    public void addIncomingItem(){
        when(storeItemRepository.findById(storeItemList.get(0).getId())).thenReturn(Optional.of(storeItemList.get(0)));
        when(supplierRepository.findByIdAndDeletedAtIsNull(supplierList.get(0).getId())).thenReturn(Optional.of(supplierList.get(0)));
        when(storeItemRepository.save(any(StoreItemEntity.class))).thenReturn(storeItemList.get(0));
        when(incomingItemRepository.save(any(IncomingItemEntity.class))).thenReturn(incomingItemList.get(0));

        var incomingDto = new IncomingItemDto(storeItemList.get(0).getId(),supplierList.get(0).getId(),new BigDecimal(12000)
                ,100,LocalDateTime.now(), LocalDate.now().plusYears(5));
        var result = incomingItemService.add(incomingDto);
        assertEquals(incomingItemList.get(0),result);

        verify(storeItemRepository).findById(storeItemList.get(0).getId());
        verify(supplierRepository).findByIdAndDeletedAtIsNull(supplierList.get(0).getId());
        verify(storeItemRepository).save(any(StoreItemEntity.class));
        verify(incomingItemRepository).save(any(IncomingItemEntity.class));
    }

    @Test
    public void getIncomingItem(){
        when(incomingItemRepository.findAllSearch((Pageable) any(), anyString(), any(),any()))
                .thenReturn(new PageImpl<>(incomingItemList));
        when(incomingItemRepository.findAllSearch((Sort) any(), anyString(),any(), any()))
                .thenReturn(incomingItemList);

        Page<IncomingItemResponseDto> result = incomingItemService.getAll(
                false,0,3,"","storeItem.item.name", "asc", LocalDateTime.now().minusYears(10), LocalDateTime.now());
        Page<IncomingItemResponseDto> resultPaged = incomingItemService.getAll(
                true,0,3,"","storeItem.item.name", "asc", LocalDateTime.now().minusYears(10), LocalDateTime.now());

        List<IncomingItemResponseDto> items = incomingItemList.stream().map(IncomingItemResponseDto::toDto).collect(Collectors.toList());
        assertTrue(resultPaged.equals(result));
        assertEquals(items.get(0), result.getContent().get(0));

        verify(incomingItemRepository).findAllSearch((Pageable) any(), anyString(), any(),any());
        verify(incomingItemRepository).findAllSearch((Sort) any(), anyString(),any(), any());
    }
}
