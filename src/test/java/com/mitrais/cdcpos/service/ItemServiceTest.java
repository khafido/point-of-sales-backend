//package com.mitrais.cdcpos.service;
//
//import com.mitrais.cdcpos.dto.ItemRequestDto;
//import com.mitrais.cdcpos.dto.ItemResponseDto;
//import com.mitrais.cdcpos.dto.PaginatedDto;
//import com.mitrais.cdcpos.entity.item.ItemEntity;
//import com.mitrais.cdcpos.repository.ItemRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class ItemServiceTest {
//
//    private ItemRepository itemRepository;
//    private ItemService itemService;
//
//    private List<ItemResponseDto> itemListDto = new ArrayList<>();
//    private List<ItemEntity> itemListEntity = new ArrayList<>();
//
//    @BeforeEach
//    void setUp() {
//        itemRepository = Mockito.mock(ItemRepository.class);
//        itemService = new ItemService(itemRepository);
//
//        itemListDto = Arrays.asList(
//                Mockito.mock(ItemResponseDto.class),
//                Mockito.mock(ItemResponseDto.class)
//        );
//
//        itemListEntity = Arrays.asList(
//                Mockito.mock(ItemEntity.class),
//                Mockito.mock(ItemEntity.class)
//        );
//    }
//
//
//    @Test
//    void listItemUnpaged() {
//        Sort sortAsc = Sort.by("name").ascending();
//        Sort sortDesc = Sort.by("name").descending();
//        Mockito.when(itemRepository.findAllSearch(sortAsc, "xyz")).thenReturn(itemListEntity);
//        Mockito.when(itemRepository.findAllSearch(sortDesc, "xyz")).thenReturn(itemListEntity);
//
//        PaginatedDto<ItemResponseDto> resultAsc = itemService.getAll(false, 0, 0, "xyz", "name", "asc");
//        PaginatedDto<ItemResponseDto> resultDesc = itemService.getAll(false, 0, 0, "xyz", "name", "desc");
//
//        assertArrayEquals(itemListDto.toArray(), resultAsc.getCurrentPageContent().toArray());
//        assertArrayEquals(itemListDto.toArray(), resultDesc.getCurrentPageContent().toArray());
//        Mockito.verify(itemRepository, Mockito.times(1)).findAllSearch(sortAsc, "xyz");
//        Mockito.verify(itemRepository, Mockito.times(1)).findAllSearch(sortDesc, "xyz");
//    }
//
//    @Test
//    void listItemPaged() {
//        Sort sortAsc = Sort.by("name").ascending();
//        Sort sortDesc = Sort.by("name").descending();
//        Pageable pagingAsc = PageRequest.of(0, 10, sortAsc);
//        Pageable pagingDesc = PageRequest.of(0, 10, sortDesc);
//        Page<ItemEntity> pageSuppliersAsc = new PageImpl<>(itemListEntity, pagingAsc, 2);
//        Page<ItemEntity> pageSuppliersDesc = new PageImpl<>(itemListEntity, pagingDesc, 2);
//
//        Mockito.when(itemRepository.findAllSearch(pagingAsc, "xyz")).thenReturn(pageSuppliersAsc);
//        Mockito.when(itemRepository.findAllSearch(pagingDesc, "xyz")).thenReturn(pageSuppliersDesc);
//
//        PaginatedDto<ItemResponseDto> resultAsc = itemService.getAll(true, 0, 10, "xyz", "name", "asc");
//        PaginatedDto<ItemResponseDto> resultDesc = itemService.getAll(true, 0, 10, "xyz", "name", "desc");
//
//        assertArrayEquals(itemListDto.toArray(), resultAsc.getCurrentPageContent().toArray());
//        assertArrayEquals(itemListDto.toArray(), resultDesc.getCurrentPageContent().toArray());
//        Mockito.verify(itemRepository, Mockito.times(1)).findAllSearch(pagingAsc, "xyz");
//        Mockito.verify(itemRepository, Mockito.times(1)).findAllSearch(pagingDesc, "xyz");
//    }
//
//    @Test
//    void addItem() {
//        ItemRequestDto requestDto = Mockito.mock(ItemRequestDto.class);
//        Mockito.when(itemRepository.save(Mockito.any(ItemEntity.class))).thenReturn(itemListEntity.get(0));
//
//        ItemEntity result = itemService.add(requestDto);
//
//        assertEquals(itemListEntity.get(0), result);
//        Mockito.verify(itemRepository, Mockito.times(1)).save(Mockito.any(ItemEntity.class));
//    }
//
//    @Test
//    void updateItem() {
//        UUID id = UUID.randomUUID();
//        ItemRequestDto requestDto = Mockito.mock(ItemRequestDto.class);
//
//        Mockito.when(itemRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.ofNullable(itemListEntity.get(0)));
//        Mockito.when(itemRepository.save(Mockito.any(ItemEntity.class))).thenReturn(itemListEntity.get(1));
//
//        ItemEntity result = itemService.update(id, requestDto);
//
//        assertEquals(itemListEntity.get(1), result);
//        Mockito.verify(itemRepository, Mockito.times(1)).save(Mockito.any(ItemEntity.class));
//        Mockito.verify(itemRepository, Mockito.times(1)).findByIdAndDeletedAtIsNull(id);
//    }
//
//    @Test
//    void deleteSupplier() {
//        UUID id = UUID.randomUUID();
//        Mockito.when(itemRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.ofNullable(itemListEntity.get(0)));
//        Mockito.when(itemRepository.save(Mockito.any(ItemEntity.class))).thenReturn(itemListEntity.get(1));
//
//        ItemEntity result = itemService.delete(id);
//
//        assertEquals(itemListEntity.get(1), result);
//        Mockito.verify(itemRepository, Mockito.times(1)).save(Mockito.any(ItemEntity.class));
//        Mockito.verify(itemRepository, Mockito.times(1)).findByIdAndDeletedAtIsNull(id);
//    }
//}