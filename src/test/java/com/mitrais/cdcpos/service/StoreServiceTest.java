package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.StoreAssignManagerDto;
import com.mitrais.cdcpos.dto.StoreDto;
import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.RoleEntity;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.entity.store.StoreEntity;
import com.mitrais.cdcpos.repository.StoreRepository;
import com.mitrais.cdcpos.repository.UserRepository;
import lombok.SneakyThrows;
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
class StoreServiceTest {
    @Mock
    private StoreRepository storeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StoreService storeService;

    private static final List<StoreEntity> storeList = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        for (int i = 0; i <= 4; i++) {
            StoreEntity storeEntity = new StoreEntity();
            storeEntity.setName("Store ".concat(String.valueOf(i)));
            storeEntity.setLocation("yogya");
            storeEntity.setManager(null);
            storeList.add(storeEntity);
        }
    }

    @Test
    public void getById(){
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setName("Store");
        storeEntity.setLocation("Loc");
        storeEntity.setManager(null);
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
        storeEntity.setManager(null);

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
        oldStore.setManager(null);
        oldStore.setName("Yogya Store");
        Optional<StoreEntity> optionalOldStore = Optional.of(oldStore);

        StoreEntity updatedStore = new StoreEntity();
        updatedStore.setLocation("Jakarta");
        updatedStore.setName("Jakarta Store");
        updatedStore.setManager(null);

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
        store.setManager(null);
        store.setName("Yogya Store");
        Optional<StoreEntity> optionalStore = Optional.of(store);

        StoreEntity deletedStore = new StoreEntity();
        deletedStore.setLocation("Yogya");
        deletedStore.setManager(null);
        deletedStore.setName("Yogya Store");
        deletedStore.setDeletedAt(LocalDateTime.now());

        UUID id = UUID.randomUUID();
        when(storeRepository.findByIdEqualsAndDeletedAtIsNull((UUID) any())).thenReturn(optionalStore);
        when(storeRepository.save((StoreEntity) any())).thenReturn(deletedStore);

        assertSame(deletedStore, storeService.delete(id));
        verify(storeRepository, times(1)).save((StoreEntity) any());
        verify(storeRepository, times(1)).findByIdEqualsAndDeletedAtIsNull((UUID) any());
    }

    @SneakyThrows
    @Test
    public void assignManager() {
        RoleEntity role = new RoleEntity();
        role.setName(ERole.ROLE_MANAGER);

        UserEntity user = new UserEntity();
        user.setRoles(Set.of(role));

        StoreEntity store = new StoreEntity();
        store.setManager(user);

        when(userRepository.findByIdAndDeletedAtIsNull(user.getId())).thenReturn(user);
        when(storeRepository.findByIdEqualsAndDeletedAtIsNull(store.getId())).thenReturn(Optional.of(store));
        when(storeRepository.save(store)).thenReturn(store);

        StoreAssignManagerDto request = new StoreAssignManagerDto();
        request.setStoreId(store.getId().toString());
        request.setUserId(user.getId().toString());

        StoreEntity result = storeService.assignManager(request);

        assertEquals(store, result);
        verify(userRepository, times(1)).findByIdAndDeletedAtIsNull(user.getId());
        verify(storeRepository, times(1)).findByIdEqualsAndDeletedAtIsNull(store.getId());
        verify(storeRepository, times(1)).save(store);
    }
}

