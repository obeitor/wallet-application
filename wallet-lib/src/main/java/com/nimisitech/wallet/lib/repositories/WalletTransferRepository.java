package com.nimisitech.wallet.lib.repositories;

import com.nimisitech.wallet.lib.models.WalletTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletTransferRepository extends JpaRepository<WalletTransfer, Long> {
    Optional<WalletTransfer> findFirstByRequestRefIdAndClientId(String requestRefId, String clientId);
    Optional<WalletTransfer> findByTransferRefId(String transferRefId);
}
