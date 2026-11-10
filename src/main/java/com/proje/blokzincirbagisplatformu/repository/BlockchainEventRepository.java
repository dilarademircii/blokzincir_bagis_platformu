
package com.proje.blokzincirbagisplatformu.repository;

import com.proje.blokzincirbagisplatformu.model.BlockchainEvent;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BlockchainEventRepository
        extends JpaRepository<BlockchainEvent, Long> {

    // Aynı event daha önce kaydedilmiş mi?
    boolean existsByChainIdAndTransactionHashAndLogIndex(
            Long chainId,
            String transactionHash,
            Long logIndex);

    // Event'i benzersiz alanlarıyla bulma
    Optional<BlockchainEvent> findByChainIdAndTransactionHashAndLogIndex(
            Long chainId,
            String transactionHash,
            Long logIndex);

    // Transaction hash ile event kayıtları
    List<BlockchainEvent> findByTransactionHash(
            String transactionHash);

    // Ağ kimliğine göre event kayıtları
    Page<BlockchainEvent> findByChainId(
            Long chainId, Pageable pageable);

    // İşlenmiş / işlenmemiş event kayıtları
    Page<BlockchainEvent> findByIslendi(
            Boolean islendi, Pageable pageable);

    // Event adına göre listeleme
    Page<BlockchainEvent> findByEventAdi(
            String eventAdi, Pageable pageable);

    // Blockchain işlemine bağlı event kayıtları
    List<BlockchainEvent> findByBlockchainIslem_Id(
            Long blockchainIslemId);

    // İşlenmemiş event'leri eskiden yeniye getirme
    List<BlockchainEvent> findByIslendiFalseOrderByOlusturmaTarihiAsc();

    // Belirli ağdaki işlenmemiş event'ler
    List<BlockchainEvent> findByChainIdAndIslendiFalseOrderByOlusturmaTarihiAsc(
            Long chainId);

    // İşlenecek event'i eşzamanlı işleme karşı kilitleyerek bulma
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT e FROM BlockchainEvent e
        WHERE e.id = :eventId
          AND e.islendi = false
        """)
    Optional<BlockchainEvent> islenmemisEventiKilitle(
            @Param("eventId") Long eventId);

    // Belirli işlemle ilişkilendirilmiş event var mı?
    boolean existsByBlockchainIslem_Id(Long blockchainIslemId);

    // Ağdaki event sayısı
    long countByChainId(Long chainId);

    // İşlenmemiş event sayısı
    long countByChainIdAndIslendiFalse(Long chainId);
}
