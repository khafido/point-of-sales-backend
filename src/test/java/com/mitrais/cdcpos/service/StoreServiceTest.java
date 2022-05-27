package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.IncomingItemResponseDto;
import com.mitrais.cdcpos.dto.store.StoreAssignManagerRequestDto;
import com.mitrais.cdcpos.dto.store.StoreDto;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.RoleEntity;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.entity.item.IncomingItemEntity;
import com.mitrais.cdcpos.entity.item.ItemEntity;
import com.mitrais.cdcpos.entity.item.SupplierEntity;
import com.mitrais.cdcpos.entity.store.StoreEntity;
import com.mitrais.cdcpos.entity.store.StoreItemEntity;
import com.mitrais.cdcpos.repository.IncomingItemRepository;
import com.mitrais.cdcpos.repository.StoreRepository;
import com.mitrais.cdcpos.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
    @Mock
    private StoreRepository storeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    IncomingItemRepository incomingItemRepository;

    @InjectMocks
    private StoreService storeService;

    private static final List<StoreEntity> storeList = new ArrayList<>();
    private static final List<ItemEntity> itemList = new ArrayList<>();
    private static final List<CategoryEntity> categoryList = new ArrayList<>();
    private static final List<StoreItemEntity> storeItemList = new ArrayList<>();
    private static final List<IncomingItemEntity> incomingItemList = new ArrayList<>();
    private static final List<SupplierEntity> supplierList = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        for (int i = 0; i <= 4; i++) {
            StoreEntity storeEntity = new StoreEntity();
            storeEntity.setName("Store ".concat(String.valueOf(i)));
            storeEntity.setLocation("yogya");
            storeEntity.setManager(null);
            storeList.add(storeEntity);
        }

        /*
        * init for Incoming item
        * */
        supplierList.add(new SupplierEntity(UUID.randomUUID(),"sup 1", "sup 1", "0812138132","sup@gmail.com",
                "jalan raya"));
        categoryList.add(new CategoryEntity(UUID.randomUUID(), "makanan"));
        categoryList.add(new CategoryEntity(UUID.randomUUID(), "minuman"));
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

        StoreAssignManagerRequestDto request = new StoreAssignManagerRequestDto();
        request.setUserId(user.getId().toString());

        StoreEntity result = storeService.assignManager(store.getId(), request);

        assertEquals(store, result);
        verify(userRepository, times(1)).findByIdAndDeletedAtIsNull(user.getId());
        verify(storeRepository, times(1)).findByIdEqualsAndDeletedAtIsNull(store.getId());
        verify(storeRepository, times(1)).save(store);
    }

    @Test
    public void getAllExpiredStoreItem(){
        when(incomingItemRepository.findAllExpired((Pageable) any(),  any(), anyString(),any(),any())).thenReturn(new PageImpl<>(incomingItemList));
        when(incomingItemRepository.findAllExpired((Sort) any(),  any(), anyString(),any(),any())).thenReturn(incomingItemList);

        Page<IncomingItemResponseDto> result = storeService.storeListOfExpiredItems(storeList.get(0).getId(),false,0,3,"",
                "supplier","asc",LocalDateTime.now().minusYears(10),LocalDateTime.now());
        Page<IncomingItemResponseDto> resultPaged = storeService.storeListOfExpiredItems(storeList.get(0).getId(),true,0,3,"",
                "supplier","asc",LocalDateTime.now().minusYears(10),LocalDateTime.now());

        List<IncomingItemResponseDto> items = incomingItemList.stream().map(IncomingItemResponseDto::toDto).collect(Collectors.toList());
        assertTrue(resultPaged.equals(result));
        assertEquals(items.get(0), result.getContent().get(0));

        verify(incomingItemRepository).findAllExpired((Pageable) any(),  any(), anyString(),any(),any());
        verify(incomingItemRepository).findAllExpired((Sort) any(),  any(), anyString(),any(),any());
    }
}

