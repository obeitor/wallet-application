package com.nimisitech.wallet.lib.service.impl;

import com.nimisitech.wallet.lib.exceptions.ApiResponseCode;
import com.nimisitech.wallet.lib.exceptions.LimitException;
import com.nimisitech.wallet.lib.models.WalletLimit;
import com.nimisitech.wallet.lib.repositories.WalletLimitRepository;
import com.nimisitech.wallet.lib.service.WalletLimitService;
import com.nimisitech.wallet.lib.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WalletLimitServiceImpl implements WalletLimitService {

    @Autowired
    private WalletLimitRepository repo;

    private String getNewCode(){
        return IdGenerator.getUuidBasedId().substring(0,4).toUpperCase();
    }

    @Override
    public WalletLimit saveLimit(WalletLimit limit) {
        limit.setCode(getNewCode());
        if(Strings.isNullOrEmpty(limit.getName())){
            limit.setName(limit.getCode());
        }
        return repo.save(limit);
    }

    @Override
    public WalletLimit saveLimit(WalletLimit limit, String code) throws LimitException {
        if(Strings.isNullOrEmpty(limit.getCode()) || !limit.getCode().equalsIgnoreCase(code)){
            throw new LimitException("Limit code not valid", ApiResponseCode.BAD_REQUEST);
        }
        return repo.save(limit);
    }

    @Override
    public WalletLimit getWalletWithName(String name) throws LimitException {
        return repo.findFirstByName(name).orElseThrow(()-> new LimitException(String.format("Limit with name '%s' was not found",name),ApiResponseCode.UNKNOWN_LIMIT_TYPE));
    }

    @Override
    public WalletLimit getWallet(String code) throws LimitException {
        return repo.findByCode(code).orElseThrow(()-> new LimitException(String.format("Limit with code '%s' was not found",code),ApiResponseCode.UNKNOWN_LIMIT_TYPE));
    }

    @Override
    public Page<WalletLimit> getWallets(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public List<WalletLimit> getWallets() {
        return repo.findAll();
    }
}
