package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.VoucherDto;
import com.mitrais.cdcpos.entity.VoucherEntity;
import com.mitrais.cdcpos.repository.VoucherRepository;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {
    @Mock
    private VoucherRepository voucherRepository;
    @InjectMocks
    private VoucherService voucherService;

    private List<VoucherEntity> voucherList;
    @BeforeEach
    public void init(){
        VoucherEntity voucher1 = new VoucherEntity(UUID.randomUUID(),"voucher 1", UUID.randomUUID().toString(),
                new BigDecimal(10000), LocalDateTime.now(),LocalDateTime.now(),
                new BigDecimal(250000),"description");
        VoucherEntity voucher2 = mock(VoucherEntity.class);
        VoucherEntity voucher3 = mock(VoucherEntity.class);

        voucherList = new ArrayList<>(Arrays.asList(voucher1,voucher2,voucher3));
    }

    @Test
    public void getVoucherNotPaged(){
        when(voucherRepository.findAllSearch((Sort) any(),anyString())).thenReturn(voucherList);
        var resultAsc = voucherService.getAll(false, 0,3,"","name", "asc");
        var resultDesc = voucherService.getAll(false, 0,3,"","name", "desc");
        for(int i = 0;i<=2;i++){
            assertSame(resultAsc.getContent().get(i), resultDesc.getContent().get(i));
        }
        verify(voucherRepository, Mockito.times(2)).findAllSearch((Sort) any(),anyString());
    }

    @Test
    public void getVoucherPaged(){
        var pagedVoucher = new PageImpl<>(voucherList);
        when(voucherRepository.findAllSearch((Pageable) any(),anyString())).thenReturn(pagedVoucher);

        var result = voucherService.getAll(true, 0, 3,"", "name", "asc");
        assertSame(pagedVoucher,result);
        verify(voucherRepository).findAllSearch((Pageable) any(),anyString());
    }

    @Test
    public void createVoucherSuccessful(){
        var voucher = new VoucherEntity();
        when(voucherRepository.save(any(VoucherEntity.class))).thenReturn(voucher);
        var dto = new VoucherDto();
        dto.setName("voucher 1");
        dto.setCode(UUID.randomUUID().toString());
        dto.setValue(new BigDecimal(50000));
        dto.setMinimumPurchase(new BigDecimal(150000));
        dto.setEndDate(LocalDateTime.now().plusDays(1));
        dto.setStartDate(LocalDateTime.now().plusDays(20));
        dto.setDescription("description");
        var result = voucherService.add(dto);
        assertNotNull(result.getCreatedAt());
        verify(voucherRepository).save(any(VoucherEntity.class));
    }

    @Test
    public void updateVoucherSuccessful(){
        when(voucherRepository.getById(voucherList.get(0).getId())).thenReturn(voucherList.get(0));
        when(voucherRepository.save(any(VoucherEntity.class))).thenReturn(voucherList.get(0));

        var dto = new VoucherDto();
        dto.setName("voucher 1");
        dto.setCode(UUID.randomUUID().toString());
        dto.setValue(new BigDecimal(50000));
        dto.setMinimumPurchase(new BigDecimal(150000));
        dto.setEndDate(LocalDateTime.now().plusDays(1));
        dto.setStartDate(LocalDateTime.now().plusDays(20));
        dto.setDescription("description");
        var result = voucherService.update(voucherList.get(0).getId(),dto);

        assertEquals(voucherList.get(0).getLastModifiedAt(),result.getLastModifiedAt());
        verify(voucherRepository).getById(voucherList.get(0).getId());
        verify(voucherRepository).save(any(VoucherEntity.class));

    }


    @Test
    public void deleteVoucher(){
        var voucher = Optional.of(voucherList.get(0));
        when(voucherRepository.findById(voucher.get().getId())).thenReturn(voucher);
        when(voucherRepository.save(any(VoucherEntity.class))).thenReturn(voucherList.get(0));

        var result = voucherService.delete(voucherList.get(0).getId());
        assertNotNull(result.getDeletedAt());
        verify(voucherRepository).findById(voucherList.get(0).getId());
        verify(voucherRepository).save(any(VoucherEntity.class));
    }

    @Test
    public void checkVoucherExists(){
        when(voucherRepository.existsByCode(anyString())).thenReturn(true);
        var resultTrue = voucherService.isVoucherExist(voucherList.get(0).getName());
        assertTrue(resultTrue);

        when(voucherRepository.existsByCode(anyString())).thenReturn(false);
        var resultFalse = voucherService.isVoucherExist("notExist");
        assertFalse(resultFalse);

        verify(voucherRepository, Mockito.times(2)).existsByCode(anyString());
    }

}
