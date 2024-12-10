package com.nimisitech.wallet.lib.repositories;

import com.nimisitech.wallet.lib.models.Currency;
import com.nimisitech.wallet.lib.models.WalletType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletTypeRepository extends JpaRepository<WalletType,Long> {
    Boolean existsByCode(String code);
    Optional<WalletType> findByCodeAndActiveTrue(String code);
    List<WalletType> findByCurrency_CodeAndActiveTrue(String currencyCode);
    List<WalletType> findByCurrencyAndActiveTrue(Currency currency);
    List<WalletType> findByActiveFalse();
}
