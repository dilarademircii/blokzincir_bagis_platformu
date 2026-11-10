
package com.proje.blokzincirbagisplatformu.repository;

import com.proje.blokzincirbagisplatformu.model.Kampanya;
import com.proje.blokzincirbagisplatformu.model.enums.KampanyaDurumu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface KampanyaRepository extends JpaRepository<Kampanya, Long> {

    // Kurumun kampanyaları
    Page<Kampanya> findByKurum_Id(
            Long kurumId, Pageable pageable);

    // Duruma göre kampanyalar
    Page<Kampanya> findByDurum(
            KampanyaDurumu durum, Pageable pageable);

    // Kurum ve duruma göre kampanyalar
    Page<Kampanya> findByKurum_IdAndDurum(
            Long kurumId,
            KampanyaDurumu durum,
            Pageable pageable);

    // Başlığa göre arama
    Page<Kampanya> findByBaslikContainingIgnoreCase(
            String baslik, Pageable pageable);

    // Belirli hedef tutarın üzerindeki kampanyalar
    Page<Kampanya> findByHedefTutarGreaterThanEqual(
            BigDecimal hedefTutar, Pageable pageable);

    // Başlangıç tarihi belirli aralıkta olan kampanyalar
    List<Kampanya> findByBaslangicTarihiBetween(
            LocalDateTime baslangic,
            LocalDateTime bitis);

    // Bitiş tarihi geçmiş kampanyalar
    List<Kampanya> findByBitisTarihiBefore(
            LocalDateTime tarih);

    // Tarihi henüz dolmamış ve aktif durumdaki kampanyalar
    @Query("""
        SELECT k FROM Kampanya k
        WHERE k.durum = :durum
          AND (k.baslangicTarihi IS NULL OR k.baslangicTarihi <= :simdi)
          AND (k.bitisTarihi IS NULL OR k.bitisTarihi >= :simdi)
        """)
    Page<Kampanya> devamEdenKampanyalariGetir(
            @Param("durum") KampanyaDurumu durum,
            @Param("simdi") LocalDateTime simdi,
            Pageable pageable);

    // Kampanyanın bağlı olduğu kurumla birlikte getirilmesi
    @Query("""
        SELECT k FROM Kampanya k
        JOIN FETCH k.kurum
        WHERE k.id = :kampanyaId
        """)
    Optional<Kampanya> kurumuylaBirlikteBul(
            @Param("kampanyaId") Long kampanyaId);

    // Kurumun kampanya sayısı
    long countByKurum_Id(Long kurumId);

    // Belirli durumdaki kampanya sayısı
    long countByKurum_IdAndDurum(
            Long kurumId, KampanyaDurumu durum);

    // Kampanya arama: başlık ve açıklama
    @Query("""
        SELECT k FROM Kampanya k
        WHERE LOWER(k.baslik) LIKE LOWER(CONCAT('%', :arama, '%'))
           OR LOWER(k.aciklama) LIKE LOWER(CONCAT('%', :arama, '%'))
        """)
    Page<Kampanya> kampanyaAra(
            @Param("arama") String arama,
            Pageable pageable);
}
