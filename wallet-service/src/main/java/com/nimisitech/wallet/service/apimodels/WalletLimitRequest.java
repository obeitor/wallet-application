package com.nimisitech.wallet.service.apimodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nimisitech.wallet.integration.apimodels.LimitRequest;
import com.nimisitech.wallet.lib.exceptions.ApiResponseCode;
import com.nimisitech.wallet.lib.exceptions.LimitException;
import com.nimisitech.wallet.lib.models.WalletLimit;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletLimitRequest extends LimitRequest {

    @JsonIgnore
    public WalletLimit getWalletLimit(WalletLimit limit)throws LimitException{
        if(this.getMinimumBalance()!=null){
            limit.setMinimumBalance(this.getMinimumBalance());
        }
        if(this.getMaximumBalance()!=null){
            limit.setMaximumBalance(this.getMaximumBalance());
        }
        if(this.getMinimumSingleDebit()!=null){
            limit.setMinimumSingleDebit(this.getMinimumSingleDebit());
        }
        if(this.getMaximumSingleDebit()!=null){
            limit.setMaximumSingleDebit(this.getMaximumSingleDebit());
        }
        if(this.getMinimumSingleCredit()!=null){
            limit.setMinimumSingleCredit(this.getMinimumSingleCredit());
        }
        if(this.getMaximumSingleCredit()!=null){
            limit.setMaximumSingleCredit(this.getMaximumSingleCredit());
        }
        if(this.getMinimumDailyDebit()!=null){
            limit.setMinimumDailyDebit(this.getMinimumDailyDebit());
        }
        if(this.getMaximumDailyDebit()!=null){
            limit.setMaximumDailyDebit(this.getMaximumDailyDebit());
        }
        if(this.getMinimumDailyCredit()!=null){
            limit.setMinimumDailyCredit(this.getMinimumDailyCredit());
        }
        if(this.getMaximumDailyCredit()!=null){
            limit.setMaximumDailyCredit(this.getMaximumDailyCredit());
        }
        if(this.getName()!=null){
            if(!Pattern.matches("^[a-zA-Z0-9 ]{3,100}$",this.getName())){
                throw new LimitException("Limit name must be alpha numeric with at least 3 characters", ApiResponseCode.BAD_REQUEST);
            }
            limit.setName(this.getName());
        }
        return limit;
    }

    @JsonIgnore
    public WalletLimit getWalletLimit() throws LimitException {
        return getWalletLimit(new WalletLimit());
    }
}
