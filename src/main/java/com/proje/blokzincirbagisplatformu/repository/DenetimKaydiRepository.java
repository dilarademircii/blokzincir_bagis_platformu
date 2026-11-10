
package com.proje.blokzincirbagisplatformu.repository;

import com.proje.blokzincirbagisplatformu.model.DenetimKaydi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DenetimKaydiRepository
        extends JpaRepository<DenetimKaydi, Long> {

    // Kullanıcının işlem geçmişi
    Page<DenetimKaydi> findByKullanici_Id(
            Long kullaniciId, Pageable pageable);

    // İşlem türüne göre kayıtlar
    Page<DenetimKaydi> findByIslemTuru(
            String islemTuru, Pageable pageable);

    // Varlık türüne göre kayıtlar
    Page<DenetimKaydi> findByVarlikTuru(
            String varlikTuru, Pageable pageable);

    // Belirli varlığın işlem geçmişi
    Page<DenetimKaydi> findByVarlikTuruAndVarlikId(
            String varlikTuru,
            Long varlikId,
            Pageable pageable);

    // Tarih aralığındaki denetim kayıtları
    Page<DenetimKaydi> findByIslemTarihiBetween(
            LocalDateTime baslangic,
            LocalDateTime bitis,
            Pageable pageable);

    // Kullanıcının tarih aralığındaki işlemleri
    Page<DenetimKaydi> findByKullanici_IdAndIslemTarihiBetween(
            Long kullaniciId,
            LocalDateTime baslangic,
            LocalDateTime bitis,
            Pageable pageable);

    // IP adresine göre işlem geçmişi
    Page<DenetimKaydi> findByIpAdresi(
            String ipAdresi, Pageable pageable);

    // Sistem işlemlerini kullanıcı bilgisiyle getirme
    @Query("""
        SELECT d FROM DenetimKaydi d
        LEFT JOIN FETCH d.kullanici
        WHERE d.id = :kayitId
        """)
    java.util.Optional<DenetimKaydi> denetimKaydiniDetayliGetir(
            @Param("kayitId") Long kayitId);

    // Belirli kullanıcının belirli varlık üzerindeki işlemleri
    @Query("""
        SELECT d FROM DenetimKaydi d
        WHERE d.kullanici.id = :kullaniciId
          AND d.varlikTuru = :varlikTuru
          AND d.varlikId = :varlikId
        ORDER BY d.islemTarihi DESC
        """)
    List<DenetimKaydi> kullaniciVarlikGecmisiniGetir(
            @Param("kullaniciId") Long kullaniciId,
            @Param("varlikTuru") String varlikTuru,
            @Param("varlikId") Long varlikId);

    // Kullanıcıya bağlı olmayan sistem kayıtları
    Page<DenetimKaydi> findByKullaniciIsNull(
            Pageable pageable);

    // Kullanıcının toplam işlem sayısı
    long countByKullanici_Id(Long kullaniciId);
}
