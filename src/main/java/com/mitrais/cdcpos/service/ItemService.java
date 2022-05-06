package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.ItemRequestDto;
import com.mitrais.cdcpos.dto.PaginatedDto;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.entity.item.ItemEntity;
import com.mitrais.cdcpos.exception.ResourceNotFoundException;
import com.mitrais.cdcpos.repository.CategoryRepository;
import com.mitrais.cdcpos.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryService categoryService;

    public ItemEntity add(ItemRequestDto req) {
        ItemEntity item = new ItemEntity();
        item.setName(req.getName());
        item.setImage(req.getImage());
        item.setBarcode(req.getBarcode());
        CategoryEntity category = categoryService.getActiveDataByName(req.getCategory());
        item.setCategory(category);
        item.setPackaging(req.getPackaging());

        return itemRepository.save(item);
    }

    public PaginatedDto<ItemEntity> getAll(boolean isPaginated, int page, int size) {
        Pageable paging;

        if (isPaginated) {
            paging = PageRequest.of(page, size);
        }
        else {
            paging = Pageable.unpaged();
        }

        Page<ItemEntity> items = itemRepository.findByDeletedAtIsNull(paging);
        PaginatedDto<ItemEntity> result = new PaginatedDto<>(items.getContent(), items.getNumber(), items.getTotalPages());

        return result;
    }

    public ItemEntity getById(UUID id){
        return itemRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", id));
    }

    public ItemEntity getByName(String name){
        return itemRepository.findByNameAndDeletedAtIsNull(name)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "name", name));
    }

    public ItemEntity update(UUID id, ItemRequestDto req) {
        ItemEntity item = getById(id);

        item.setName(req.getName());
        item.setImage(req.getImage());
        item.setBarcode(req.getBarcode());
        CategoryEntity category = categoryService.getActiveDataByName(req.getCategory());
        item.setCategory(category);
        item.setPackaging(req.getPackaging());

        return itemRepository.save(item);
    }

    public ItemEntity delete(UUID id) {
        ItemEntity item = getById(id);

        item.setDeletedAt(LocalDateTime.now());

        return itemRepository.save(item);
    }

}
