package com.mitrais.cdcpos.service;


import com.mitrais.cdcpos.dto.IncomingItemResponseDto;
import com.mitrais.cdcpos.dto.store.*;
import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.item.IncomingItemEntity;
import com.mitrais.cdcpos.dto.AddEmployeeDto;

import com.mitrais.cdcpos.entity.store.StoreEmployeeEntity;
import com.mitrais.cdcpos.entity.store.StoreEntity;
import com.mitrais.cdcpos.entity.store.StoreItemEntity;
import com.mitrais.cdcpos.exception.ManualValidationFailException;
import com.mitrais.cdcpos.repository.*;
import com.mitrais.cdcpos.repository.StoreEmployeeRepository;
import com.mitrais.cdcpos.repository.StoreRepository;
import com.mitrais.cdcpos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final StoreItemRepository storeItemRepository;
    private final ParameterRepository parameterRepository;
    private final IncomingItemRepository incomingItemRepository;
    private final StoreEmployeeRepository storeEmployeeRepository;

    public Page<StoreEntity> getAll(boolean paginated, int page, int size, String searchValue, String sortBy, String sortDirection) {
        Sort sort;
        Pageable paging;
        Page<StoreEntity> result;

        if ("DESC".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        if (paginated) {
            paging = PageRequest.of(page, size, sort);
            result = storeRepository.search(searchValue, paging);
        } else {
            List<StoreEntity> storeEntities = storeRepository.search(searchValue, sort);
            result = new PageImpl<>(storeEntities);
        }
        return result;
    }

    public Optional<StoreEntity> getById(UUID id) {
        return storeRepository.findByIdEqualsAndDeletedAtIsNull(id);
    }

    public StoreEntity create(StoreDto storeDto) {
        var newStore = new StoreEntity();
        newStore.setName(storeDto.getName());
        newStore.setLocation(storeDto.getLocation());
        newStore.setManager(null);
        return storeRepository.save(newStore);
    }

    public StoreEntity update(UUID id, StoreDto storeDto) {
        var store = getById(id);
        if (store.isPresent()) {
            var updateStore = store.get();
            updateStore.setName(storeDto.getName());
            updateStore.setLocation(storeDto.getLocation());
            updateStore.setManager(null);
            return storeRepository.save(updateStore);
        } else {
            return null;
        }
    }

    public StoreEntity delete(UUID id) {
        var store = getById(id);
        if (store.isPresent()) {
            var deleteStore = store.get();
            deleteStore.setDeletedAt(LocalDateTime.now());
            return storeRepository.save(deleteStore);
        } else {
            return null;
        }
    }

    public StoreEntity assignManager(UUID id, StoreAssignManagerRequestDto request) throws ManualValidationFailException {
        var user = userRepository.findByIdAndDeletedAtIsNull(UUID.fromString(request.getUserId()));
        var optionalStore = storeRepository.findByIdEqualsAndDeletedAtIsNull(id);

        if (user != null && optionalStore.isPresent()) {
            boolean manager = false;
            for (var role : user.getRoles()) {
                if (role.getName().equals(ERole.ROLE_MANAGER)) {
                    manager = true;
                    break;
                }
            }

            if (manager) {
                var store = optionalStore.get();
                store.setManager(user);
                return storeRepository.save(store);
            } else {
                throw new ManualValidationFailException("User ID" + user.getId() + " is not a manager");
            }
        }
        return null;
    }

    public List<StoreListOfItemsResponseDto> addItemToStore(UUID id, StoreAddItemRequestDto request) {
        var optionalStore = storeRepository.findByIdEqualsAndDeletedAtIsNull(id);
        var taxParameter = parameterRepository.findByNameIgnoreCase("tax_percentage").get();
        var profitParameter = parameterRepository.findByNameIgnoreCase("profit_percentage").get();

        List<StoreListOfItemsResponseDto> addedStoreItem = new ArrayList<>();
        for(String itemId : request.getItemIdList()) {
            var optionalItem = itemRepository.findByIdAndDeletedAtIsNull(UUID.fromString(itemId));

            if(optionalStore.isPresent() && optionalItem.isPresent()) {
                var store = optionalStore.get();
                var item = optionalItem.get();

                var optionalStoreItem = storeItemRepository.findByStoreIdAndItemId(store.getId(), item.getId());

                StoreItemEntity storeItem;
                if(optionalStoreItem.isPresent()) {
                    storeItem = optionalStoreItem.get();
                    storeItem.setDeletedAt(null);
                } else {
                    storeItem = new StoreItemEntity();
                    storeItem.setStore(store);
                    storeItem.setItem(item);
                    storeItem.setStock(0);
                    storeItem.setFixedPrice(new BigDecimal(0));
                    storeItem.setPriceMode(StoreItemEntity.PriceMode.BY_SYSTEM);
                }
                storeItemRepository.save(storeItem);
                addedStoreItem.add(convertAndCalculateStoreItem(storeItem, Integer.parseInt(taxParameter.getValue()), Integer.parseInt(profitParameter.getValue())));
            }
        }
        return addedStoreItem;
    }

    public Page<IncomingItemResponseDto> storeListOfExpiredItems(UUID id, boolean paginated, int page, int size,
                                                                 String search, String sortBy, String sortDirection,
                                                                 LocalDateTime start, LocalDateTime end){

        Sort sort;
        Pageable paging;
        Page<IncomingItemEntity> storeExpiredItems;

        if(sortBy.equalsIgnoreCase("supplier")){
            sortBy = sortBy.concat(".name");
        }else if(sortBy.equalsIgnoreCase("buyDate")){
            sortBy = "buyDate";
        }else if(sortBy.equalsIgnoreCase("expiryDate")){
            sortBy = "expiryDate";
        }
        else{
            sortBy = "storeItem.item.name";
        }

        if("DESC".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        if(paginated){
            paging = PageRequest.of(page,size,sort);
            storeExpiredItems = incomingItemRepository.findAllExpired(paging,id,search,start,end);
            return storeExpiredItems.map(IncomingItemResponseDto::toDto);
        }else{
            var list = incomingItemRepository.findAllExpired(sort,id,search,start,end);
            var result =  list.stream().map(IncomingItemResponseDto::toDto).collect(Collectors.toList());
            return new PageImpl<>(result);
        }

    }

    public Page<StoreListOfItemsResponseDto> storeListOfItems(UUID id, Boolean paginated, Integer page, Integer size, String searchValue, String sortBy, String sortDirection) {
        Sort sort;
        Pageable paging;
        Page<StoreItemEntity> storeItemEntities;

        String[] itemColumns = {"name", "category", "packaging"};
        if(Arrays.stream(itemColumns).anyMatch(sortBy::equalsIgnoreCase)) {
            sortBy = "item.".concat(sortBy);
        }

        if ("DESC".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        var taxParameter = parameterRepository.findByNameIgnoreCase("tax_percentage").get();
        var profitParameter = parameterRepository.findByNameIgnoreCase("profit_percentage").get();

        if(paginated) {
            paging = PageRequest.of(page, size, sort);
            storeItemEntities = storeItemRepository.findByStoreIdWithSearch(paging, id, searchValue);

            return storeItemEntities.map(entity ->
                    convertAndCalculateStoreItem(entity, Integer.parseInt(taxParameter.getValue()), Integer.parseInt(profitParameter.getValue())));
        } else {
            List<StoreItemEntity> storeEntities = storeItemRepository.findByStoreIdWithSearch(sort, id, searchValue);
            List<StoreListOfItemsResponseDto> result = storeEntities.stream().map(entity ->
                    convertAndCalculateStoreItem(entity, Integer.parseInt(taxParameter.getValue()), Integer.parseInt(profitParameter.getValue()))
            ).collect(Collectors.toList());
            return new PageImpl<>(result);
        }
        
    }

    private StoreListOfItemsResponseDto convertAndCalculateStoreItem(StoreItemEntity entity, int taxPercent, int profitPercent) {
        List<IncomingItemEntity> latestIncomingItem = incomingItemRepository.latestIncomingByStoreIdAndItemId(PageRequest.of(0, 1), entity.getStore().getId(), entity.getItem().getId());

        BigDecimal bySystemPrice;
        // bySystemPrice Formula: f(pricePerItem + (profit% * pricePerItem)) + (f() * tax%)
        if (latestIncomingItem.size() > 0) {
            BigDecimal pricePerItem = latestIncomingItem.get(0).getPricePerItem();
            BigDecimal profitValue = pricePerItem.multiply(new BigDecimal((double) taxPercent / 100));
            BigDecimal buyPlusProfit = pricePerItem.add(profitValue);
            bySystemPrice = buyPlusProfit.add(buyPlusProfit.multiply(new BigDecimal((double) profitPercent / 100)));
            bySystemPrice = bySystemPrice.setScale(3, RoundingMode.HALF_UP);
        } else {
            bySystemPrice = new BigDecimal(0);
        }

        return StoreListOfItemsResponseDto.toDto(entity, bySystemPrice);
    }

    public Page<StoreEmployeeEntity> getStoreEmployee(UUID storeId, boolean paginated, int page, int size, String searchValue, String sortBy, String sortDirection) {
        Sort sort;
        Pageable paging;
        Page<StoreEmployeeEntity> result;

        if(this.getById(storeId).isEmpty()){
            return null;
        }

        if ("DESC".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        if (paginated) {
            paging = PageRequest.of(page, size, sort);
            result = storeEmployeeRepository.search(storeId, searchValue, paging);
        } else {
            List<StoreEmployeeEntity> storeEntities = storeEmployeeRepository.search(storeId, searchValue, sort);
            result = new PageImpl<>(storeEntities);
        }
        return result;
    }

    public StoreEmployeeEntity addEmployee(AddEmployeeDto request) throws ManualValidationFailException {
        var user = userRepository.findByIdAndDeletedAtIsNull(UUID.fromString(request.getUserId()));
        var optionalStore = storeRepository.findByIdEqualsAndDeletedAtIsNull(UUID.fromString(request.getStoreId()));

        if (user != null && optionalStore.isPresent()) {
            var isWorkingAtStore = storeEmployeeRepository.existsByUser_IdEqualsAndStore_IdEquals(UUID.fromString(request.getUserId()), UUID.fromString(request.getStoreId()));
            if(isWorkingAtStore) {
                throw new ManualValidationFailException(user.getFirstName() + " Is Already Working In A Store");
//                return null;
            }

            boolean validRole = false;
            for (var role : user.getRoles()) {
                if (role.getName().equals(ERole.ROLE_CASHIER) || role.getName().equals(ERole.ROLE_STOCKIST)) {
                    validRole = true;
                    break;
                }
            }

            if (validRole) {
                var storeEmployee = new StoreEmployeeEntity();
                storeEmployee.setStore(optionalStore.get());
                storeEmployee.setUser(user);
                return storeEmployeeRepository.save(storeEmployee);
            } else {
                throw new ManualValidationFailException(user.getFirstName() + " Is Neither A Cashier Nor Stockist");
            }
        }
        return null;
    }

    public StoreListOfItemsResponseDto updateStoreItemPrice(UUID id, UUID itemId, StoreUpdateItemPriceRequestDto request) throws ManualValidationFailException {
        Optional<StoreItemEntity.PriceMode> optionalPriceMode =
                Arrays.stream(StoreItemEntity.PriceMode.values())
                        .filter(priceMode -> priceMode.toString().equalsIgnoreCase(request.getPriceMode()))
                        .findAny();

        if(optionalPriceMode.isEmpty()) {
            throw new ManualValidationFailException("Invalid Price Mode");
        }

        var optionalStore = storeRepository.findByIdEqualsAndDeletedAtIsNull(id);
        var optionalItem = itemRepository.findByIdAndDeletedAtIsNull(itemId);
        var optionalStoreItem = storeItemRepository.findByStoreIdAndItemId(id, itemId);

        if(optionalStore.isPresent() && optionalItem.isPresent() && optionalStoreItem.isPresent()) {
            var storeItem = optionalStoreItem.get();
            storeItem.setFixedPrice(request.getFixedPrice());
            storeItem.setPriceMode(optionalPriceMode.get());

            storeItemRepository.save(storeItem);

            var taxParameter = parameterRepository.findByNameIgnoreCase("tax_percentage").get();
            var profitParameter = parameterRepository.findByNameIgnoreCase("profit_percentage").get();
            return convertAndCalculateStoreItem(storeItem, Integer.parseInt(taxParameter.getValue()), Integer.parseInt(profitParameter.getValue()));
        }
        return null;
    }
}
