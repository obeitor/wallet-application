package com.nimisitech.wallet.lib.repositories;

import com.nimisitech.wallet.lib.models.OutwardTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OutwardTransferRepository extends JpaRepository<OutwardTransfer,Long> {
    Optional<OutwardTransfer> findByProviderReferenceAndProvider(String providerRef, String provider);
}
