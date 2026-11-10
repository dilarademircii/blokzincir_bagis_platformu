package com.proje.blokzincirbagisplatformu.repository;

import com.proje.blokzincirbagisplatformu.model.ZincirIslemi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZincirIslemiRepository extends JpaRepository<ZincirIslemi, Long> {

    // Aynı olay mükerrer işlenmesin diye txHash ve logIndex kontrolü
    boolean existsByTxHashAndLogIndex(String txHash, Integer logIndex);

    Optional<ZincirIslemi> findByTxHash(String txHash);
}