package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.SupplierDto;
import com.mitrais.cdcpos.entity.item.SupplierEntity;
import com.mitrais.cdcpos.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {

    private SupplierRepository supplierRepository;
    private SupplierService supplierService;

    private List<SupplierEntity> supplierList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        supplierRepository = Mockito.mock(SupplierRepository.class);
        supplierService = new SupplierService(supplierRepository);

        supplierList = Arrays.asList(
                Mockito.mock(SupplierEntity.class),
                Mockito.mock(SupplierEntity.class)
        );
    }


    @Test
    void listSuppliersUnpaged() {
        Sort sortAsc = Sort.by("name").ascending();
        Sort sortDesc = Sort.by("name").descending();
        Mockito.when(supplierRepository.findAllSearch(sortAsc, "xyz")).thenReturn(supplierList);
        Mockito.when(supplierRepository.findAllSearch(sortDesc, "xyz")).thenReturn(supplierList);

        Page<SupplierEntity> resultAsc = supplierService.listSuppliers(false, 0, 0, "xyz", "name", "asc");
        Page<SupplierEntity> resultDesc = supplierService.listSuppliers(false, 0, 0, "xyz", "name", "desc");

        assertArrayEquals(supplierList.toArray(), resultAsc.getContent().toArray());
        assertArrayEquals(supplierList.toArray(), resultDesc.getContent().toArray());
        Mockito.verify(supplierRepository, Mockito.times(1)).findAllSearch(sortAsc, "xyz");
        Mockito.verify(supplierRepository, Mockito.times(1)).findAllSearch(sortDesc, "xyz");
    }

    @Test
    void listSupplierPaged() {
        Sort sortAsc = Sort.by("name").ascending();
        Sort sortDesc = Sort.by("name").descending();
        Pageable pagingAsc = PageRequest.of(0, 10, sortAsc);
        Pageable pagingDesc = PageRequest.of(0, 10, sortDesc);
        Page<SupplierEntity> pageSuppliersAsc = new PageImpl<>(supplierList, pagingAsc, 2);
        Page<SupplierEntity> pageSuppliersDesc = new PageImpl<>(supplierList, pagingDesc, 2);

        Mockito.when(supplierRepository.findAllSearch(pagingAsc, "xyz")).thenReturn(pageSuppliersAsc);
        Mockito.when(supplierRepository.findAllSearch(pagingDesc, "xyz")).thenReturn(pageSuppliersDesc);

        Page<SupplierEntity> resultAsc = supplierService.listSuppliers(true, 0, 10, "xyz", "name", "asc");
        Page<SupplierEntity> resultDesc = supplierService.listSuppliers(true, 0, 10, "xyz", "name", "desc");

        assertArrayEquals(supplierList.toArray(), resultAsc.getContent().toArray());
        assertArrayEquals(supplierList.toArray(), resultDesc.getContent().toArray());
        Mockito.verify(supplierRepository, Mockito.times(1)).findAllSearch(pagingAsc, "xyz");
        Mockito.verify(supplierRepository, Mockito.times(1)).findAllSearch(pagingDesc, "xyz");
    }

    @Test
    void addSupplier() {
        SupplierDto requestDto = Mockito.mock(SupplierDto.class);
        Mockito.when(supplierRepository.save(Mockito.any(SupplierEntity.class))).thenReturn(supplierList.get(0));

        SupplierEntity result = supplierService.addSupplier(requestDto);

        assertEquals(supplierList.get(0), result);
        Mockito.verify(supplierRepository, Mockito.times(1)).save(Mockito.any(SupplierEntity.class));
    }

    @Test
    void updateSupplier() {
        UUID id = UUID.randomUUID();
        SupplierDto requestDto = Mockito.mock(SupplierDto.class);
        Mockito.when(supplierRepository.findById(id)).thenReturn(Optional.ofNullable(supplierList.get(0)));
        Mockito.when(supplierRepository.save(Mockito.any(SupplierEntity.class))).thenReturn(supplierList.get(1));

        SupplierEntity result = supplierService.updateSupplier(id, requestDto);

        assertEquals(supplierList.get(1), result);
        Mockito.verify(supplierRepository, Mockito.times(1)).save(Mockito.any(SupplierEntity.class));
        Mockito.verify(supplierRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void deleteSupplier() {
        UUID id = UUID.randomUUID();
        Mockito.when(supplierRepository.findById(id)).thenReturn(Optional.ofNullable(supplierList.get(0)));
        Mockito.when(supplierRepository.save(Mockito.any(SupplierEntity.class))).thenReturn(supplierList.get(1));

        SupplierEntity result = supplierService.deleteSupplier(id);

        assertEquals(supplierList.get(1), result);
        Mockito.verify(supplierRepository, Mockito.times(1)).save(Mockito.any(SupplierEntity.class));
        Mockito.verify(supplierRepository, Mockito.times(1)).findById(id);
    }
}