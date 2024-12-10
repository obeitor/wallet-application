package com.nimisitech.wallet.lib.models;

import com.nimisitech.wallet.lib.converters.DateTimeConverter;
import com.nimisitech.wallet.lib.converters.MapToJsonConverter;
import com.nimisitech.wallet.lib.enums.TransferStatus;
import com.nimisitech.wallet.lib.models.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "wallet_transfers")
@Getter
@Setter
public class WalletTransfer extends BaseEntity {
    private String debitWallet;
    private String creditWallet;
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime completedAt;
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime reversedAt;
    private Long amount;
    private String narration;
    private String requestRefId;
    @Column(unique = true)
    private String transferRefId;
    @Convert(converter = MapToJsonConverter.class)
    @Lob
    private Map<String,Object> data;
    private String clientId;
    private String debitRef;
    private String creditRef;
    private String reversalCreditRef;
    private String reversalDebitRef;
    @Enumerated(EnumType.STRING)
    private TransferStatus debitStatus = TransferStatus.PENDING;
    @Enumerated(EnumType.STRING)
    private TransferStatus creditStatus = TransferStatus.PENDING;

    public Long getDebitAmount(){
        return -1*amount;
    }
}
