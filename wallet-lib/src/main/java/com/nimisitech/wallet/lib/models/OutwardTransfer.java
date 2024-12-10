package com.nimisitech.wallet.lib.models;

import com.nimisitech.wallet.lib.converters.DateTimeConverter;
import com.nimisitech.wallet.lib.models.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "outward_transfers")
@Data
public class OutwardTransfer extends BaseEntity {
    private String providerReference;
    private String provider;
    private String reference;
    private String narration;
    @Column(length = 512)
    private String requestData;
    @Column(length = 512)
    private String responseData;
    private boolean successful;
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime completedAt;
    private String walletId;
    private String providerWallet;
}
