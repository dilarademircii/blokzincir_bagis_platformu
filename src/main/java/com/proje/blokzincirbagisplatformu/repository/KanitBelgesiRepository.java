
package com.proje.blokzincirbagisplatformu.repository;

import com.proje.blokzincirbagisplatformu.model.KanitBelgesi;
import com.proje.blokzincirbagisplatformu.model.enums.BelgeTuru;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface KanitBelgesiRepository
        extends JpaRepository<KanitBelgesi, Long> {

    // Dosya hash'i daha önce yüklenmiş mi?
    boolean existsByDosyaHash(String dosyaHash);

    // Hash üzerinden belge bulma
    List<KanitBelgesi> findByDosyaHash(String dosyaHash);

    // Harcamaya ait kanıt belgeleri
    List<KanitBelgesi> findByHarcama_Id(Long harcamaId);

    // Belgeyi yükleyen kullanıcıya göre listeleme
    Page<KanitBelgesi> findByYukleyen_Id(
            Long kullaniciId, Pageable pageable);

    // Belge türüne göre listeleme
    Page<KanitBelgesi> findByBelgeTuru(
            BelgeTuru belgeTuru, Pageable pageable);

    // Harcama ve belge türüne göre listeleme
    List<KanitBelgesi> findByHarcama_IdAndBelgeTuru(
            Long harcamaId, BelgeTuru belgeTuru);

    // Yüklenme tarihi aralığı
    Page<KanitBelgesi> findByYuklenmeTarihiBetween(
            LocalDateTime baslangic,
            LocalDateTime bitis,
            Pageable pageable);

    // Harcamanın kanıt belgesi olup olmadığını kontrol etme
    boolean existsByHarcama_Id(Long harcamaId);

    // Belgeyi harcama ve yükleyen bilgisiyle getirme
    @Query("""
        SELECT kb FROM KanitBelgesi kb
        JOIN FETCH kb.harcama
        JOIN FETCH kb.yukleyen
        WHERE kb.id = :belgeId
        """)
    java.util.Optional<KanitBelgesi> belgeDetayiniGetir(
            @Param("belgeId") Long belgeId);

    // Belirli kullanıcının belirli harcamaya belge yükleyip yüklemediği
    boolean existsByHarcama_IdAndYukleyen_Id(
            Long harcamaId, Long kullaniciId);

    // Bir harcamaya yüklenen belge sayısı
    long countByHarcama_Id(Long harcamaId);
}
