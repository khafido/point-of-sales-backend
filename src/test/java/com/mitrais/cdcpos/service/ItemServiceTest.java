package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.ItemRequestDto;
import com.mitrais.cdcpos.dto.ItemResponseDto;
import com.mitrais.cdcpos.dto.PaginatedDto;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.entity.item.ItemEntity;
import com.mitrais.cdcpos.repository.CategoryRepository;
import com.mitrais.cdcpos.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ItemService itemService;

    @InjectMocks
    private CategoryService categoryService;

    private List<ItemResponseDto> itemListDto = new ArrayList<>();
    private List<ItemEntity> itemListEntity = new ArrayList<>();
    private List<CategoryEntity> categoryList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        itemRepository = Mockito.mock(ItemRepository.class, Mockito.RETURNS_DEEP_STUBS);
        categoryRepository = Mockito.mock(CategoryRepository.class, Mockito.RETURNS_DEEP_STUBS);

        categoryService = new CategoryService(categoryRepository);
        itemService = new ItemService(itemRepository, categoryService);

        categoryList = Arrays.asList(
                new CategoryEntity(UUID.randomUUID(), "makanan"),
                new CategoryEntity(UUID.randomUUID(), "minuman")
        );

        itemListDto = Arrays.asList(
                Mockito.mock(ItemResponseDto.class),
                Mockito.mock(ItemResponseDto.class)
        );

        itemListEntity = Arrays.asList(
                new ItemEntity(UUID.randomUUID(), "makanan 1", "aaa", "1233455", categoryList.get(0), "plastic", null),
                new ItemEntity(UUID.randomUUID(), "minuman 1", "bbb", "3213211", categoryList.get(1), "plastic", null)
        );

    }


    @Test
    void listItemUnpaged() {
        Sort sortAsc = Sort.by("name").ascending();
        Sort sortDesc = Sort.by("name").descending();

        Mockito.when(itemRepository.findAllSearch(sortAsc, "")).thenReturn(itemListEntity);
        Mockito.when(itemRepository.findAllSearch(sortDesc, "")).thenReturn(itemListEntity);

        PaginatedDto<ItemResponseDto> resultAsc = itemService.getAll(false, 0, 10, "", "name", "ASC", false);
        PaginatedDto<ItemResponseDto> resultDesc = itemService.getAll(false, 0, 10, "", "name", "DESC", true);

        assertEquals(itemListEntity.size(), resultAsc.getCurrentPageContent().size());
        assertEquals(itemListEntity.size(), resultDesc.getCurrentPageContent().size());
        Mockito.verify(itemRepository, Mockito.times(1)).findAllSearch(sortAsc, "");
        Mockito.verify(itemRepository, Mockito.times(1)).findAllSearch(sortDesc, "");
    }

    @Test
    void listItemPaged() {
        Sort sortAsc = Sort.by("name").ascending();
        Sort sortDesc = Sort.by("name").descending();
        Pageable pagingAsc = PageRequest.of(0, 10, sortAsc);
        Pageable pagingDesc = PageRequest.of(0, 10, sortDesc);
        Page<ItemEntity> pageItemAsc = new PageImpl<>(itemListEntity, pagingAsc, 2);
        Page<ItemEntity> pageItemDesc = new PageImpl<>(itemListEntity, pagingDesc, 2);

        Mockito.when(itemRepository.findAllSearch(pagingAsc, "xyz")).thenReturn(pageItemAsc);
        Mockito.when(itemRepository.findAllSearch(pagingDesc, "xyz")).thenReturn(pageItemDesc);

        PaginatedDto<ItemResponseDto> resultAsc = itemService.getAll(true, 0, 10, "xyz", "name", "asc", false);
        PaginatedDto<ItemResponseDto> resultDesc = itemService.getAll(true, 0, 10, "xyz", "name", "desc", true);

        assertEquals(itemListEntity.size(), resultAsc.getCurrentPageContent().size());
        assertEquals(itemListEntity.size(), resultDesc.getCurrentPageContent().size());
        Mockito.verify(itemRepository, Mockito.times(1)).findAllSearch(pagingAsc, "xyz");
        Mockito.verify(itemRepository, Mockito.times(1)).findAllSearch(pagingDesc, "xyz");
    }

    @Test
    void addItem() {
        CategoryEntity category1 = new CategoryEntity(UUID.randomUUID(), "Category 1");

        ItemRequestDto requestDto = new ItemRequestDto("item", "", "abc", "Category 1", "packaging");
        Mockito.when(categoryRepository.findByNameIgnoreCaseAndDeletedAtIsNull(requestDto.getCategory())).thenReturn(Optional.of(category1));
        Mockito.when(itemRepository.save(Mockito.any(ItemEntity.class))).thenReturn(itemListEntity.get(0));

        ItemEntity result = itemService.add(requestDto);
        assertEquals(itemListEntity.get(0), result);
        Mockito.verify(itemRepository, Mockito.times(1)).save(Mockito.any(ItemEntity.class));
    }

    @Test
    void updateItem() {
        CategoryEntity category1 = new CategoryEntity(UUID.randomUUID(), "Category 1");

        UUID id = UUID.randomUUID();
        Mockito.when(itemRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(itemListEntity.get(0)));

        ItemRequestDto requestDto = new ItemRequestDto("item", "", "abc", "Category 1", "packaging");
        Mockito.when(categoryRepository.findByNameIgnoreCaseAndDeletedAtIsNull(requestDto.getCategory())).thenReturn(Optional.of(category1));
        Mockito.when(itemRepository.save(Mockito.any(ItemEntity.class))).thenReturn(itemListEntity.get(0));

        ItemEntity result = itemService.update(id, requestDto);
        assertEquals(itemListEntity.get(0), result);
        Mockito.verify(itemRepository, Mockito.times(1)).save(Mockito.any(ItemEntity.class));
        Mockito.verify(itemRepository, Mockito.times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    void deleteItem() {
        UUID id = UUID.randomUUID();
        Mockito.when(itemRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.ofNullable(itemListEntity.get(0)));
        Mockito.when(itemRepository.save(Mockito.any(ItemEntity.class))).thenReturn(itemListEntity.get(1));

        ItemEntity result = itemService.delete(id);

        assertEquals(itemListEntity.get(1), result);
        Mockito.verify(itemRepository, Mockito.times(1)).save(Mockito.any(ItemEntity.class));
        Mockito.verify(itemRepository, Mockito.times(1)).findByIdAndDeletedAtIsNull(id);
    }
}