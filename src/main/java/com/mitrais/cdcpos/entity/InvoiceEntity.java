package com.mitrais.cdcpos.entity;

import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.entity.store.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice", schema = "public")
public class InvoiceEntity extends EntityAudit {
    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "payment_option")
    private String paymentOption;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "change_amount")
    private BigDecimal changeAmount;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    private VoucherEntity voucher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashier_id", referencedColumnName = "id")
    private UserEntity cashierId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private StoreEntity store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;
}
