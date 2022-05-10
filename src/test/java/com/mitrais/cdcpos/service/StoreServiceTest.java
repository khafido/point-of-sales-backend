package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.StoreDto;
import com.mitrais.cdcpos.entity.store.StoreEntity;
import com.mitrais.cdcpos.repository.StoreRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceWriteTest {
    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    private static final List<StoreEntity> storeList = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        for (int i = 0; i <= 4; i++) {
            StoreEntity storeEntity = new StoreEntity();
            storeEntity.setName("Store ".concat(String.valueOf(i)));
            storeEntity.setLocation("yogya");
            storeList.add(storeEntity);
        }
    }

    @Test
    public void getById(){
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setName("Store");
        storeEntity.setLocation("Loc");
        Optional<StoreEntity> optionalResult = Optional.of(storeEntity);

        UUID id = UUID.randomUUID();
        when(storeRepository.findByIdEqualsAndDeletedAtIsNull((UUID) any())).thenReturn(optionalResult);

        assertSame(storeEntity,storeService.getById(id).get());
        verify(storeRepository, times(1)).findByIdEqualsAndDeletedAtIsNull((UUID) any());
    }

    @Test
    public void create() {
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setName("konbini");
        storeEntity.setLocation("japan");

        when(storeRepository.save(any())).thenReturn(storeEntity);

        StoreDto storeDto = new StoreDto();
        storeDto.setName("konbini");
        storeDto.setLocation("japan");

        StoreEntity createdStore = storeService.create(storeDto);

        assertEquals(storeEntity.getName(), createdStore.getName(), "Store name should correct");
        assertEquals(storeEntity.getLocation(), createdStore.getLocation(), "Store location should correct");
        assertSame(storeEntity, createdStore, "Should refer to the same object");

        verify(storeRepository, times(1)).save(any());
    }

    @Test
    public void getStoreWithPagination() {
        var pagedStore = new PageImpl<>(storeList);
        when(storeRepository.search(anyString(), (Pageable) any())).thenReturn(pagedStore);

        var result = storeService.getAll(true, 0, 2, "sto", "name", "asc");

        assertSame(pagedStore, result);

        verify(storeRepository, times(1)).search(anyString(), (Pageable) any());
    }

    @Test
    public void getStoreWithoutPagination() {
        var pagedStore = new PageImpl<>(storeList);
        when(storeRepository.search(anyString(), (Sort) any())).thenReturn(storeList);

        var result = storeService.getAll(false, 0, 2, "sto", "name", "asc");

        for (int i = 0; i <= 4; i++) {
            assertSame(storeList.get(i), result.getContent().get(i));
        }

        verify(storeRepository, times(1)).search(anyString(), (Sort) any());
    }

    @Test
    public void update(){

        StoreEntity oldStore = new StoreEntity();
        oldStore.setLocation("Yogya");
        oldStore.setName("Yogya Store");
        Optional<StoreEntity> optionalOldStore = Optional.of(oldStore);

        StoreEntity updatedStore = new StoreEntity();
        updatedStore.setLocation("Jakarta");
        updatedStore.setName("Jakarta Store");

        when(storeRepository.save((StoreEntity) any())).thenReturn(updatedStore);
        when(storeRepository.findByIdEqualsAndDeletedAtIsNull((UUID) any())).thenReturn(optionalOldStore);
        UUID id = UUID.randomUUID();

        StoreDto storeDto = new StoreDto();
        storeDto.setLocation("Jakarta");
        storeDto.setName("Jakarta Store");
        assertSame(updatedStore, storeService.update(id, storeDto));
        verify(storeRepository, times(1)).save((StoreEntity) any());
        verify(storeRepository, times(1)).findByIdEqualsAndDeletedAtIsNull((UUID) any());
    }

    @Test
    public void delete(){
        StoreEntity store = new StoreEntity();
        store.setLocation("Yogya");
        store.setName("Yogya Store");
        Optional<StoreEntity> optionalStore = Optional.of(store);

        StoreEntity deletedStore = new StoreEntity();
        deletedStore.setLocation("Yogya");
        deletedStore.setName("Yogya Store");
        deletedStore.setDeletedAt(LocalDateTime.now());

        UUID id = UUID.randomUUID();
        when(storeRepository.findByIdEqualsAndDeletedAtIsNull((UUID) any())).thenReturn(optionalStore);
        when(storeRepository.save((StoreEntity) any())).thenReturn(deletedStore);

        assertSame(deletedStore, storeService.delete(id));
        verify(storeRepository, times(1)).save((StoreEntity) any());
        verify(storeRepository, times(1)).findByIdEqualsAndDeletedAtIsNull((UUID) any());
    }
}

