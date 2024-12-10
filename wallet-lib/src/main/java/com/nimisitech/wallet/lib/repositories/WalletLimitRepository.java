package com.nimisitech.wallet.lib.repositories;

import com.nimisitech.wallet.lib.models.WalletLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletLimitRepository extends JpaRepository<WalletLimit,Long> {
    Optional<WalletLimit> findByCode(String code);
    Optional<WalletLimit> findFirstByName(String name);
}
