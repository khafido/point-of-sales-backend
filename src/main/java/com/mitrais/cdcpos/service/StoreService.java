package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.entity.item.SupplierEntity;
import com.mitrais.cdcpos.entity.store.StoreEntity;
import com.mitrais.cdcpos.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

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
}
