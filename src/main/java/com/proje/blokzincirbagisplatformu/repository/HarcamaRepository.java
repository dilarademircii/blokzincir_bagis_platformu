
package com.proje.blokzincirbagisplatformu.repository;

import com.proje.blokzincirbagisplatformu.model.Harcama;
import com.proje.blokzincirbagisplatformu.model.enums.HarcamaDurumu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HarcamaRepository extends JpaRepository<Harcama, Long> {

    // Kampanyanın harcamaları
    Page<Harcama> findByKampanya_Id(
            Long kampanyaId, Pageable pageable);

    // Harcamayı oluşturan kullanıcıya göre listeleme
    Page<Harcama> findByOlusturan_Id(
            Long kullaniciId, Pageable pageable);

    // Tedarikçiye ait harcamalar
    Page<Harcama> findByTedarikci_Id(
            Long tedarikciId, Pageable pageable);

    // Harcama durumuna göre listeleme
    Page<Harcama> findByDurum(
            HarcamaDurumu durum, Pageable pageable);

    // Kampanya ve durum birlikte filtreleme
    Page<Harcama> findByKampanya_IdAndDurum(
            Long kampanyaId,
            HarcamaDurumu durum,
            Pageable pageable);

    // Belirli tutarın üzerindeki harcamalar
    Page<Harcama> findByTutarGreaterThanEqual(
            BigDecimal tutar, Pageable pageable);

    // Tarih aralığındaki harcamalar
    Page<Harcama> findByOlusturmaTarihiBetween(
            LocalDateTime baslangic,
            LocalDateTime bitis,
            Pageable pageable);

    // Gerçekleşmiş harcamalar
    Page<Harcama> findByGerceklesmeTarihiIsNotNull(
            Pageable pageable);

    // Gerçekleşme tarihi olmayan harcamalar
    Page<Harcama> findByGerceklesmeTarihiIsNull(
            Pageable pageable);

    // Kampanyanın toplam harcama tutarı
    @Query("""
        SELECT COALESCE(SUM(h.tutar), 0)
        FROM Harcama h
        WHERE h.kampanya.id = :kampanyaId
          AND h.durum = :durum
          AND h.paraBirimi = :paraBirimi
        """)
    BigDecimal kampanyaToplamHarcamaTutari(
            @Param("kampanyaId") Long kampanyaId,
            @Param("durum") HarcamaDurumu durum,
            @Param("paraBirimi") String paraBirimi);

    // Harcama detayını ilişkileriyle getirme
    @Query("""
        SELECT h FROM Harcama h
        JOIN FETCH h.kampanya
        JOIN FETCH h.olusturan
        LEFT JOIN FETCH h.tedarikci
        WHERE h.id = :harcamaId
        """)
    Optional<Harcama> harcamaDetayiniGetir(
            @Param("harcamaId") Long harcamaId);

    // Kampanyada belirli durumdaki harcama sayısı
    long countByKampanya_IdAndDurum(
            Long kampanyaId, HarcamaDurumu durum);

    // Harcama arama
    @Query("""
        SELECT h FROM Harcama h
        WHERE LOWER(h.baslik) LIKE LOWER(CONCAT('%', :arama, '%'))
           OR LOWER(h.aciklama) LIKE LOWER(CONCAT('%', :arama, '%'))
        """)
    Page<Harcama> harcamaAra(
            @Param("arama") String arama,
            Pageable pageable);

    // Kanıt belgesi bulunan harcamalar
    @Query("""
        SELECT DISTINCT h FROM Harcama h
        JOIN h.kanitBelgeleri kb
        """)
    Page<Harcama> kanitBelgeliHarcamalariGetir(
            Pageable pageable);

    // Kanıt belgesi bulunmayan harcamalar
    @Query("""
        SELECT h FROM Harcama h
        WHERE h.kanitBelgeleri IS EMPTY
        """)
    Page<Harcama> kanitBelgesizHarcamalariGetir(
            Pageable pageable);
}
