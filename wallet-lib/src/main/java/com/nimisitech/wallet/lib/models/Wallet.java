package com.nimisitech.wallet.lib.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimisitech.wallet.lib.enums.WalletStatus;
import com.nimisitech.wallet.lib.models.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "wallets")
@Getter
@Setter
public class Wallet extends BaseEntity {
    private String name;
    @Column(unique = true)
    @JsonProperty("id")
    private String walletKey;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wallet_type_id", referencedColumnName = "id")
    private WalletType walletType;
    private Boolean canDebit = true;
    private Boolean canCredit = true;
    @Enumerated(EnumType.STRING)
    private WalletStatus status = WalletStatus.ACTIVE;
    private Long bookBalance = 0L;
    private Long availableBalance = 0L;

    @JsonProperty("currencyCode")
    public String getCurrencyCode(){
        return this.walletType.getCurrency().getCode();
    }

    @JsonProperty("limitCode")
    public String getLimitCode(){
        return this.walletType.getLimit().getCode();
    }

    @JsonProperty("currencyName")
    public String getCurrencyName(){
        return this.walletType.getCurrency().getName();
    }

    @JsonProperty("limitName")
    public String getLimitName(){
        return this.walletType.getLimit().getName();
    }

    @JsonProperty("typeCode")
    public String getTypeCode(){
        return this.walletType.getCode();
    }

    @JsonProperty("typeName")
    public String getTypeName(){
        return this.walletType.getName();
    }
}
