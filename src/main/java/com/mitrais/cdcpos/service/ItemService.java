package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.ItemRequestDto;
import com.mitrais.cdcpos.dto.ItemResponseDto;
import com.mitrais.cdcpos.dto.PaginatedDto;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.entity.item.ItemEntity;
import com.mitrais.cdcpos.exception.ResourceNotFoundException;
import com.mitrais.cdcpos.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
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

    public PaginatedDto<ItemResponseDto> getAll(
            boolean isPaginated,
            int page,
            int size,
            String searchValue,
            String sortBy,
            String sortDirection,
            boolean fullInformation) {

        Sort sort;
        Pageable paging;
        Page<ItemEntity> items;

        if("DESC".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        if (isPaginated) {
            paging = PageRequest.of(page, size, sort);
            items = itemRepository.findAllSearch(paging, searchValue);
        }
        else {
            List<ItemEntity> itemEntityList = itemRepository.findAllSearch(sort, searchValue);
            items = new PageImpl<>(itemEntityList);
        }

        List<ItemResponseDto> itemDtoList;
        if(fullInformation) {
            itemDtoList = items.stream()
                    .map(ItemResponseDto::toDto)
                    .collect(Collectors.toList());
        } else {
            itemDtoList = items.stream()
                    .map(ItemResponseDto::toDtoLite)
                    .collect(Collectors.toList());
        }

        return new PaginatedDto<>(itemDtoList, items.getNumber(), items.getTotalPages());
    }

    public ItemEntity getById(UUID id){
        return itemRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", id));
    }

    public ItemEntity getByName(String name){
        return itemRepository.findByNameIgnoreCaseAndDeletedAtIsNull(name)
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

    public boolean checkBarcodeOnAdd(String barcode) {
//        System.out.println("barcode = " + barcode);
        return itemRepository.existsByBarcode(barcode);
    }

    public boolean checkBarcodeOnUpdate(UUID id, String barcode) {
        ItemEntity item = getById(id);
//        System.out.println("item barcode = " + item.getBarcode() + ", barcode = " + barcode);
        if (!barcode.equals(item.getBarcode())) {
            return itemRepository.existsByBarcode(barcode);
        }
        else {
            return false;
        }
    }
}
