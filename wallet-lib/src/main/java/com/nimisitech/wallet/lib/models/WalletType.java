package com.nimisitech.wallet.lib.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimisitech.wallet.lib.models.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "wallet_types")
@Getter
@Setter
public class WalletType extends BaseEntity {
    @Column(length = 10,unique = true)
    private String code;
    @Column(length = 100)
    private String name;
    private Boolean active = true;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id",referencedColumnName = "id")
    private Currency currency;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id",name = "wallet_limit_id",nullable = false)
    private WalletLimit limit;

    @JsonProperty("currencyCode")
    public String getCurrencyCode(){
        return this.currency.getCode();
    }

    @JsonProperty("limitCode")
    public String getLimitCode(){
        return this.limit.getCode();
    }

    @JsonProperty("currencyName")
    public String getCurrencyName(){
        return this.currency.getName();
    }

    @JsonProperty("limitName")
    public String getLimitName(){
        return this.limit.getName();
    }
}
