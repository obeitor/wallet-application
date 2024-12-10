package com.nimisitech.wallet.lib.models;

import com.nimisitech.wallet.lib.models.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "wallet_limits")
@Getter
@Setter
public class WalletLimit extends BaseEntity {
    private Long minimumBalance = 0L;
    private Long maximumBalance = 99999999999999L;
    private Long minimumSingleDebit = 1L;
    private Long maximumSingleDebit = 999999999L;
    private Long minimumSingleCredit = 1L;
    private Long maximumSingleCredit = 999999999L;
    private Long minimumDailyDebit = 1L;
    private Long maximumDailyDebit = 99999999999999L;
    private Long minimumDailyCredit = 1L;
    private Long maximumDailyCredit = 99999999999999L;
    private String name;
    @Column(unique = true)
    private String code;
}
