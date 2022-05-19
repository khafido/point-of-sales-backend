package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.store.StoreAddItemRequestDto;
import com.mitrais.cdcpos.dto.store.StoreAssignManagerRequestDto;
import com.mitrais.cdcpos.dto.store.StoreDto;
import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.store.StoreEntity;
import com.mitrais.cdcpos.entity.store.StoreItemEntity;
import com.mitrais.cdcpos.exception.ManualValidationFailException;
import com.mitrais.cdcpos.repository.ItemRepository;
import com.mitrais.cdcpos.repository.StoreItemRepository;
import com.mitrais.cdcpos.repository.StoreRepository;
import com.mitrais.cdcpos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final StoreItemRepository storeItemRepository;

    public Page<StoreEntity> getAll(boolean paginated,int page, int size, String searchValue, String sortBy, String sortDirection) {
        Sort sort;
        Pageable paging;
        Page<StoreEntity> result;

        if ("DESC".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        if(paginated) {
            paging = PageRequest.of(page, size, sort);
            result = storeRepository.search(searchValue, paging);
        } else {
            List<StoreEntity> storeEntities = storeRepository.search(searchValue, sort);
            result = new PageImpl<>(storeEntities);
        }
        return result;
    }

    public Optional<StoreEntity> getById(UUID id){
        return storeRepository.findByIdEqualsAndDeletedAtIsNull(id);
    }

    public StoreEntity create(StoreDto storeDto){
        var newStore = new StoreEntity();
        newStore.setName(storeDto.getName());
        newStore.setLocation(storeDto.getLocation());
        newStore.setManager(null);
        return storeRepository.save(newStore);
    }

    public StoreEntity update(UUID id,StoreDto storeDto){
        var store = getById(id);
        if(store.isPresent()){
            var updateStore = store.get();
            updateStore.setName(storeDto.getName());
            updateStore.setLocation(storeDto.getLocation());
            updateStore.setManager(null);
            return storeRepository.save(updateStore);
        }else{
            return null;
        }
    }

    public StoreEntity delete(UUID id){
        var store = getById(id);
        if(store.isPresent()){
            var deleteStore = store.get();
            deleteStore.setDeletedAt(LocalDateTime.now());
            return storeRepository.save(deleteStore);
        }else{
            return null;
        }
    }

    public StoreEntity assignManager(StoreAssignManagerRequestDto request) throws ManualValidationFailException {
        var user = userRepository.findByIdAndDeletedAtIsNull(UUID.fromString(request.getUserId()));
        var optionalStore = storeRepository.findByIdEqualsAndDeletedAtIsNull(UUID.fromString(request.getStoreId()));

        if(user!=null && optionalStore.isPresent()) {
            boolean manager = false;
            for(var role : user.getRoles()) {
                if(role.getName().equals(ERole.ROLE_MANAGER)) {
                    manager = true;
                    break;
                }
            }

            if(manager) {
                var store = optionalStore.get();
                store.setManager(user);
                return storeRepository.save(store);
            } else {
                throw new ManualValidationFailException("User ID" + user.getId() + " is not a manager");
            }
        }
        return null;
    }

    public StoreItemEntity addItemToStore(UUID id, StoreAddItemRequestDto request) {
        var optionalStore = storeRepository.findByIdEqualsAndDeletedAtIsNull(id);
        var optionalItem = itemRepository.findByIdAndDeletedAtIsNull(UUID.fromString(request.getItemId()));

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
            }
            return storeItemRepository.save(storeItem);
        }
        return null;
    }
}
