
package com.proje.blokzincirbagisplatformu.repository;

import com.proje.blokzincirbagisplatformu.model.BlockchainIslem;
import com.proje.blokzincirbagisplatformu.model.enums.BlockchainIslemDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.BlockchainIslemTuru;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BlockchainIslemRepository
        extends JpaRepository<BlockchainIslem, Long> {

    // Transaction hash daha önce kaydedilmiş mi?
    boolean existsByTransactionHash(String transactionHash);

    // Transaction hash üzerinden işlem bulma
    Optional<BlockchainIslem> findByTransactionHash(
            String transactionHash);

    // Ağ kimliğine göre işlemleri listeleme
    Page<BlockchainIslem> findByChainId(
            Long chainId, Pageable pageable);

    // İşlem durumuna göre listeleme
    Page<BlockchainIslem> findByDurum(
            BlockchainIslemDurumu durum, Pageable pageable);

    // İşlem türüne göre listeleme
    Page<BlockchainIslem> findByIslemTuru(
            BlockchainIslemTuru islemTuru, Pageable pageable);

    // Ağ ve durum birlikte filtreleme
    Page<BlockchainIslem> findByChainIdAndDurum(
            Long chainId,
            BlockchainIslemDurumu durum,
            Pageable pageable);

    // Bağışa ait blockchain işlemleri
    List<BlockchainIslem> findByBagis_Id(Long bagisId);

    // Harcamaya ait blockchain işlemleri
    List<BlockchainIslem> findByHarcama_Id(Long harcamaId);

    // Onaylanmış işlemleri listeleme
    Page<BlockchainIslem> findByOnaylanmaTarihiIsNotNull(
            Pageable pageable);

    // Henüz onaylanmamış işlemler
    Page<BlockchainIslem> findByOnaylanmaTarihiIsNull(
            Pageable pageable);

    // Oluşturulma tarihi aralığındaki işlemler
    Page<BlockchainIslem> findByOlusturmaTarihiBetween(
            LocalDateTime baslangic,
            LocalDateTime bitis,
            Pageable pageable);
    // Bağışın onaylanmış blockchain işlemini getir
    Optional<BlockchainIslem>
    findFirstByBagis_IdAndDurumOrderByOnaylanmaTarihiDesc(
            Long bagisId,
            BlockchainIslemDurumu durum
    );
    // Belirli ağda ve durumda bekleyen işlemleri getirme
    @Query("""
        SELECT bi FROM BlockchainIslem bi
        WHERE bi.chainId = :chainId
          AND bi.durum = :durum
        ORDER BY bi.olusturmaTarihi ASC
        """)
    List<BlockchainIslem> bekleyenIslemleriGetir(
            @Param("chainId") Long chainId,
            @Param("durum") BlockchainIslemDurumu durum);

    // Belirli bağış için belirli işlem türü mevcut mu?
    boolean existsByBagis_IdAndIslemTuru(
            Long bagisId, BlockchainIslemTuru islemTuru);

    // Belirli harcama için işlem var mı?
    boolean existsByHarcama_Id(Long harcamaId);

    // İşlemi bağış ve harcama bilgileriyle getirme
    @Query("""
        SELECT bi FROM BlockchainIslem bi
        LEFT JOIN FETCH bi.bagis
        LEFT JOIN FETCH bi.harcama
        WHERE bi.id = :islemId
        """)
    Optional<BlockchainIslem> islemDetayiniGetir(
            @Param("islemId") Long islemId);

    // Ağdaki belirli durumdaki işlem sayısı
    long countByChainIdAndDurum(
            Long chainId, BlockchainIslemDurumu durum);

}
