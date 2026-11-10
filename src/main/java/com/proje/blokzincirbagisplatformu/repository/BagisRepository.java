
package com.proje.blokzincirbagisplatformu.repository;

import com.proje.blokzincirbagisplatformu.model.Bagis;
import com.proje.blokzincirbagisplatformu.model.enums.BagisDurumu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BagisRepository extends JpaRepository<Bagis, Long> {

    // Kullanıcının yaptığı bağışları listeleme
    Page<Bagis> findByBagisci_Id(Long bagisciId, Pageable pageable);

    // Kampanyaya ait bağışları listeleme
    Page<Bagis> findByKampanya_Id(Long kampanyaId, Pageable pageable);

    // Bağış durumuna göre listeleme
    Page<Bagis> findByDurum(BagisDurumu durum, Pageable pageable);

    // Kullanıcı ve bağış durumuna göre filtreleme
    Page<Bagis> findByBagisci_IdAndDurum(
            Long bagisciId,
            BagisDurumu durum,
            Pageable pageable);

    // Kampanya ve bağış durumuna göre filtreleme
    List<Bagis> findByKampanya_IdAndDurum(
            Long kampanyaId,
            BagisDurumu durum);

    // Belirli cüzdandan yapılan bağışlar
    Page<Bagis> findByCuzdan_Id(Long cuzdanId, Pageable pageable);

    // Tarih aralığına göre bağışları listeleme
    Page<Bagis> findByOlusturmaTarihiBetween(
            LocalDateTime baslangic,
            LocalDateTime bitis,
            Pageable pageable);

    // Kullanıcının toplam bağış tutarı
    @Query("""
        SELECT COALESCE(SUM(b.tutar), 0)
        FROM Bagis b
        WHERE b.bagisci.id = :bagisciId
          AND b.durum = :durum
        """)
    BigDecimal toplamBagisTutariGetir(
            @Param("bagisciId") Long bagisciId,
            @Param("durum") BagisDurumu durum);

    // Kampanyanın belirli durumdaki toplam bağış tutarı
    @Query("""
        SELECT COALESCE(SUM(b.tutar), 0)
        FROM Bagis b
        WHERE b.kampanya.id = :kampanyaId
          AND b.durum = :durum
        """)
    BigDecimal kampanyaBagisTutariGetir(
            @Param("kampanyaId") Long kampanyaId,
            @Param("durum") BagisDurumu durum);

    // Bağışın blockchain işlemi var mı?
    @Query("""
        SELECT CASE
            WHEN COUNT(bi) > 0 THEN true
            ELSE false
        END
        FROM BlockchainIslem bi
        WHERE bi.bagis.id = :bagisId
        """)
    boolean blockchainIslemiVarMi(@Param("bagisId") Long bagisId);

    // Kullanıcının belirli kampanyaya bağış yapıp yapmadığı
    boolean existsByBagisci_IdAndKampanya_Id(
            Long bagisciId, Long kampanyaId);

    // Belirli kampanyadaki bağış sayısı
    long countByKampanya_Id(Long kampanyaId);

    // Belirli kullanıcının bağış sayısı
    long countByBagisci_Id(Long bagisciId);
}

