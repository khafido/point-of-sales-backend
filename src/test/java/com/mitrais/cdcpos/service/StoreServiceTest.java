package com.mitrais.cdcpos.service;


import com.mitrais.cdcpos.dto.IncomingItemResponseDto;
import com.mitrais.cdcpos.dto.store.*;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.entity.ParameterEntity;
import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.RoleEntity;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.entity.item.IncomingItemEntity;
import com.mitrais.cdcpos.entity.item.ItemEntity;
import com.mitrais.cdcpos.entity.item.SupplierEntity;
import com.mitrais.cdcpos.entity.store.StoreEmployeeEntity;
import com.mitrais.cdcpos.entity.store.StoreEntity;
import com.mitrais.cdcpos.entity.store.StoreItemEntity;

import com.mitrais.cdcpos.exception.ManualValidationFailException;

import com.mitrais.cdcpos.repository.IncomingItemRepository;
import com.mitrais.cdcpos.repository.StoreRepository;
import com.mitrais.cdcpos.repository.UserRepository;

import com.mitrais.cdcpos.repository.*;
import io.swagger.v3.core.util.Json;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
    private static final List<StoreEntity> storeList = new ArrayList<>();
    private static final List<ItemEntity> itemList = new ArrayList<>();
    private static final List<ParameterEntity> parameterList = new ArrayList<>();
    private static final List<CategoryEntity> categoryList = new ArrayList<>();
    private static final List<StoreItemEntity> storeItemList = new ArrayList<>();
    private static final List<IncomingItemEntity> incomingItemList = new ArrayList<>();
    private static final List<SupplierEntity> supplierList = new ArrayList<>();
    @Mock
    private StoreEmployeeRepository storeEmployeeRepository;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ParameterRepository parameterRepository;
    @Mock
    private StoreItemRepository storeItemRepository;
    @Mock
    private IncomingItemRepository incomingItemRepository;
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private StoreService storeService;

    @BeforeAll
    static void beforeAll() {
        for (int i = 0; i <= 4; i++) {
            StoreEntity storeEntity = new StoreEntity();
            storeEntity.setName("Store ".concat(String.valueOf(i)));
            storeEntity.setLocation("yogya");
            storeEntity.setManager(null);
            storeList.add(storeEntity);
        }
        supplierList.add(new SupplierEntity(UUID.randomUUID(), "sup 1", "sup 1", "0812138132", "sup@gmail.com", "jalan raya"));

        parameterList.add(new ParameterEntity(UUID.randomUUID(), "tax_percentage", "10"));
        parameterList.add(new ParameterEntity(UUID.randomUUID(), "profit_percentage", "12"));

        categoryList.add(new CategoryEntity(UUID.randomUUID(), "Makanan"));
        categoryList.add(new CategoryEntity(UUID.randomUUID(), "Minuman"));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setName("Makanan R");
        itemEntity.setImage("aaaaa");
        itemEntity.setBarcode("3802183");
        itemEntity.setCategory(categoryList.get(0));
        itemEntity.setPackaging("Plastic");

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
        incomingItem.setStoreItem(storeItem);
        incomingItem.setPricePerItem(new BigDecimal(15000));
        incomingItemList.add(incomingItem);
    }

    @Test
    public void getById() {
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setName("Store");
        storeEntity.setLocation("Loc");
        storeEntity.setManager(null);
        Optional<StoreEntity> optionalResult = Optional.of(storeEntity);

        UUID id = UUID.randomUUID();
        when(storeRepository.findByIdEqualsAndDeletedAtIsNull(any())).thenReturn(optionalResult);

        assertSame(storeEntity, storeService.getById(id).get());
        verify(storeRepository, times(1)).findByIdEqualsAndDeletedAtIsNull(any());
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
    public void update() {

        StoreEntity oldStore = new StoreEntity();
        oldStore.setLocation("Yogya");
        oldStore.setManager(null);
        oldStore.setName("Yogya Store");
        Optional<StoreEntity> optionalOldStore = Optional.of(oldStore);

        StoreEntity updatedStore = new StoreEntity();
        updatedStore.setLocation("Jakarta");
        updatedStore.setName("Jakarta Store");
        updatedStore.setManager(null);

        when(storeRepository.save(any())).thenReturn(updatedStore);
        when(storeRepository.findByIdEqualsAndDeletedAtIsNull(any())).thenReturn(optionalOldStore);
        UUID id = UUID.randomUUID();

        StoreDto storeDto = new StoreDto();
        storeDto.setLocation("Jakarta");
        storeDto.setName("Jakarta Store");
        assertSame(updatedStore, storeService.update(id, storeDto));
        verify(storeRepository, times(1)).save(any());
        verify(storeRepository, times(1)).findByIdEqualsAndDeletedAtIsNull(any());
    }

    @Test
    public void delete() {
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
        when(storeRepository.findByIdEqualsAndDeletedAtIsNull(any())).thenReturn(optionalStore);
        when(storeRepository.save(any())).thenReturn(deletedStore);

        assertSame(deletedStore, storeService.delete(id));
        verify(storeRepository, times(1)).save(any());
        verify(storeRepository, times(1)).findByIdEqualsAndDeletedAtIsNull(any());
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
    public void getAllExpiredStoreItem() {
        when(incomingItemRepository.findAllExpired((Pageable) any(), any(), anyString(), any(), any())).thenReturn(new PageImpl<>(incomingItemList));
        when(incomingItemRepository.findAllExpired((Sort) any(), any(), anyString(), any(), any())).thenReturn(incomingItemList);

        Page<IncomingItemResponseDto> result = storeService.storeListOfExpiredItems(storeList.get(0).getId(), false, 0, 3, "", "supplier", "asc", LocalDateTime.now().minusYears(10), LocalDateTime.now());
        Page<IncomingItemResponseDto> resultPaged = storeService.storeListOfExpiredItems(storeList.get(0).getId(), true, 0, 3, "", "supplier", "asc", LocalDateTime.now().minusYears(10), LocalDateTime.now());

        List<IncomingItemResponseDto> items = incomingItemList.stream().map(IncomingItemResponseDto::toDto).collect(Collectors.toList());
        assertTrue(resultPaged.equals(result));
        assertEquals(items.get(0), result.getContent().get(0));

        verify(incomingItemRepository).findAllExpired((Pageable) any(), any(), anyString(), any(), any());
        verify(incomingItemRepository).findAllExpired((Sort) any(), any(), anyString(), any(), any());
    }

    @Test
    public void storeListOfItems() {
        var tax = parameterList.get(0);
        var profit = parameterList.get(1);
        var store = storeList.get(0);
        var item = itemList.get(0);
        var storeItem = storeItemList.get(0);
        var incomingItem = incomingItemList.get(0);

        when(parameterRepository.findByNameIgnoreCase("tax_percentage")).thenReturn(Optional.of(tax));
        when(parameterRepository.findByNameIgnoreCase("profit_percentage")).thenReturn(Optional.of(profit));
        when(storeItemRepository.findByStoreIdWithSearch(any(Pageable.class), any(UUID.class), anyString())).thenReturn(new PageImpl<>(List.of(storeItem)));
        when(storeItemRepository.findByStoreIdWithSearch((Sort) any(), any(UUID.class), anyString())).thenReturn(List.of(storeItem));
        when(incomingItemRepository.findByStoreIdAndItemId(any(), any(UUID.class), any(UUID.class))).thenReturn(List.of(incomingItem));

        Page<StoreListOfItemsResponseDto> resultPaginated = storeService.storeListOfItems(store.getId(), true, 0, 10, "", "name", "ASC");
        Page<StoreListOfItemsResponseDto> resultNonPaginated = storeService.storeListOfItems(store.getId(), false, 0, 10, "", "name", "ASC");

        Json.prettyPrint(resultPaginated);
        Json.prettyPrint(resultNonPaginated);

        assertTrue(resultPaginated.equals(resultNonPaginated));

        assertEquals(1, resultNonPaginated.getContent().size());

        //test the formula: f(pricePerItem + (profit% * pricePerItem)) + (f() * tax%)
        assertTrue(resultNonPaginated.getContent().get(0).getBySystemPrice().compareTo(new BigDecimal(18480)) == 0);

        assertEquals("Makanan R", resultNonPaginated.getContent().get(0).getName());
    }

    @Test
    public void getStoreEmployee() {
        RoleEntity roleCashier = new RoleEntity();
        roleCashier.setName(ERole.ROLE_CASHIER);
        RoleEntity roleStockist = new RoleEntity();
        roleStockist.setName(ERole.ROLE_STOCKIST);

        UserEntity cashier = new UserEntity();
        cashier.setRoles(Set.of(roleCashier));

        UserEntity stockist = new UserEntity();
        stockist.setRoles(Set.of(roleStockist));

        StoreEntity store = new StoreEntity();

        StoreEmployeeEntity storeEmployee = new StoreEmployeeEntity();
        storeEmployee.setStore(store);
        storeEmployee.setUser(cashier);

        StoreEmployeeEntity storeEmployee1 = new StoreEmployeeEntity();
        storeEmployee.setStore(store);
        storeEmployee.setUser(stockist);

        Optional<StoreEntity> optionalStore = Optional.of(store);
        when(this.storeRepository.findByIdEqualsAndDeletedAtIsNull(any())).thenReturn(optionalStore);

        PageImpl<StoreEmployeeEntity> pageImpl = new PageImpl<>(List.of(storeEmployee1, storeEmployee1));
        when(this.storeEmployeeRepository.search(any(), any(), (Pageable) any())).thenReturn(pageImpl);

        Page<StoreEmployeeEntity> actualStoreEmployee = this.storeService.getStoreEmployee(UUID.randomUUID(), true, 1, 3, "", "id", "ASC");

        assertSame(pageImpl, actualStoreEmployee);
        verify(this.storeRepository).findByIdEqualsAndDeletedAtIsNull(any());
        verify(this.storeEmployeeRepository).search(any(), any(), (Pageable) any());
    }

    @Test
    public void addEmployeeToStoreTest() throws ManualValidationFailException {
        RoleEntity roleCashier = new RoleEntity();
        roleCashier.setName(ERole.ROLE_CASHIER);

        UserEntity cashier = new UserEntity();
        cashier.setRoles(Set.of(roleCashier));

        StoreEntity store = new StoreEntity();
        StoreEmployeeEntity storeEmployee = new StoreEmployeeEntity();
        storeEmployee.setStore(store);
        storeEmployee.setUser(cashier);

        Optional<StoreEntity> optionalStore = Optional.of(store);
        when(this.storeRepository.findByIdEqualsAndDeletedAtIsNull(any())).thenReturn(optionalStore);
        when(this.userRepository.findByIdAndDeletedAtIsNull(any())).thenReturn(cashier);
        when(this.storeEmployeeRepository.existsByUser_IdEqualsAndStore_IdEquals(any(), any())).thenReturn(false);
//        when(this.storeEmployeeRepository.save(storeEmployee)).thenReturn(storeEmployee);
        doReturn(storeEmployee).when(this.storeEmployeeRepository).save(any());


        StoreEmployeeEntity res = this.storeService.addEmployee(UUID.randomUUID(), UUID.randomUUID());
        assertSame(storeEmployee.getStore(), res.getStore());

        verify(storeRepository, times(1)).findByIdEqualsAndDeletedAtIsNull(any());
        verify(userRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(storeEmployeeRepository, times(1)).existsByUser_IdEqualsAndStore_IdEquals(any(), any());
        verify(storeEmployeeRepository, times(1)).save(any());
    }

    @Test
    public void addItemToStore() {
        var tax = parameterList.get(0);
        var profit = parameterList.get(1);
        var store = storeList.get(0);
        var item = itemList.get(0);
        var storeItem = storeItemList.get(0);

        when(parameterRepository.findByNameIgnoreCase("tax_percentage")).thenReturn(Optional.of(tax));
        when(parameterRepository.findByNameIgnoreCase("profit_percentage")).thenReturn(Optional.of(profit));
        when(storeRepository.findByIdEqualsAndDeletedAtIsNull(store.getId())).thenReturn(Optional.of(store));
        when(itemRepository.findByIdAndDeletedAtIsNull(item.getId())).thenReturn(Optional.of(item));
        when(storeItemRepository.findByStoreIdAndItemId(store.getId(), item.getId())).thenReturn(Optional.empty());
        when(storeItemRepository.save(any())).thenReturn(storeItem);
        when(incomingItemRepository.findByStoreIdAndItemId(PageRequest.of(0, 1), store.getId(), item.getId())).thenReturn(List.of());

        StoreAddItemRequestDto requestDto = new StoreAddItemRequestDto();
        requestDto.setItemIdList(List.of(item.getId().toString()));
        List<StoreListOfItemsResponseDto> result = storeService.addItemToStore(store.getId(), requestDto);

        Json.prettyPrint(result);

        assertEquals(1, result.size());
        assertTrue(result.get(0).getBySystemPrice().compareTo(new BigDecimal(0)) == 0);
        assertEquals("Makanan R", result.get(0).getName());

    }

    @SneakyThrows
    @Test
    public void updateStoreItemPrice() {
        var tax = parameterList.get(0);
        var profit = parameterList.get(1);
        var store = storeList.get(0);
        var item = itemList.get(0);
        var storeItem = storeItemList.get(0);

        when(parameterRepository.findByNameIgnoreCase("tax_percentage")).thenReturn(Optional.of(tax));
        when(parameterRepository.findByNameIgnoreCase("profit_percentage")).thenReturn(Optional.of(profit));
        when(storeRepository.findByIdEqualsAndDeletedAtIsNull(store.getId())).thenReturn(Optional.of(store));
        when(itemRepository.findByIdAndDeletedAtIsNull(item.getId())).thenReturn(Optional.of(item));
        when(storeItemRepository.findByStoreIdAndItemId(store.getId(), item.getId())).thenReturn(Optional.of(storeItem));
        when(storeItemRepository.save(any())).thenReturn(storeItem);

        StoreUpdateItemPriceRequestDto requestDto = new StoreUpdateItemPriceRequestDto(StoreItemEntity.PriceMode.FIXED.toString(), new BigDecimal(10000));

        StoreListOfItemsResponseDto result = storeService.updateStoreItemPrice(store.getId(), item.getId(), requestDto);

        Json.prettyPrint(result);

        assertTrue(result.getBySystemPrice().compareTo(new BigDecimal(0)) == 0);
        assertTrue(result.getFixedPrice().compareTo(new BigDecimal(10000)) == 0);
        assertEquals(StoreItemEntity.PriceMode.FIXED.toString(), result.getPriceMode());
    }
}

