package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.PaginatedDto;
import com.mitrais.cdcpos.dto.StoreDto;
import com.mitrais.cdcpos.entity.store.StoreEntity;
import com.mitrais.cdcpos.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public List<StoreEntity> fetchAll() {
        return new ArrayList<>(storeRepository.findAll());
    }

    public PaginatedDto<StoreEntity> fetchAllWithPagination(int pageOffset, int pageSize) {
        var content = storeRepository.findAll(PageRequest.of(pageOffset, pageSize));
        return new PaginatedDto<>(content.getContent(), pageOffset, content.getTotalPages());
    }

    public Optional<StoreEntity> fetchById(UUID id) {
        return storeRepository.findById(id);
    }

    public StoreEntity create(StoreDto req) {
        var newStore = new StoreEntity();
        newStore.setName(req.getName());
        newStore.setLocation(req.getLocation());
        newStore.setManager(null);
        return storeRepository.save(newStore);
    }


}
