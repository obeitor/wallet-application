package com.nimisitech.wallet.lib.dtos;

import com.nimisitech.wallet.lib.models.Wallet;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class WalletInsert {
    private Wallet wallet;
    private Long amount;
    private String narration;
    private Map<String,Object> data;
    private boolean isDebit;
    private String ref;
}
