package com.nimisitech.wallet.lib.models;

import com.nimisitech.wallet.lib.converters.DateTimeConverter;
import com.nimisitech.wallet.lib.converters.MapToJsonConverter;
import com.nimisitech.wallet.lib.enums.TransactionStatus;
import com.nimisitech.wallet.lib.enums.TransactionType;
import com.nimisitech.wallet.lib.models.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "wallet_transactions")
@Getter
@Setter
public class WalletTransaction extends BaseEntity {
    private String walletKey;
    private Long amount;
    private String reference;
    private String externalReference;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime valueTime;
    private String narration;
    @Convert(converter = MapToJsonConverter.class)
    private Map<String,Object> data;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private Long balanceBefore;
    private Long balanceAfter;
}
