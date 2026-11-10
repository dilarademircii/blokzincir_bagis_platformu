
package com.proje.blokzincirbagisplatformu.repository;

import com.proje.blokzincirbagisplatformu.model.Kullanici;
import com.proje.blokzincirbagisplatformu.model.enums.HesapDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.KullaniciRol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {

    // E-posta benzersizlik kontrolü
    boolean existsByEmailIgnoreCase(String email);
    Optional<Kullanici> findByWalletAddress(String walletAddress);
    // E-posta ile kullanıcı bulma
    Optional<Kullanici> findByEmailIgnoreCase(String email);

    // Hesap durumuna göre kullanıcıları listeleme
    Page<Kullanici> findByHesapDurumu(
            HesapDurumu hesapDurumu, Pageable pageable);

    // Belirli role sahip kullanıcıları listeleme
    Page<Kullanici> findByRol(
            KullaniciRol rol, Pageable pageable);

    // Kuruma bağlı kullanıcıları listeleme
    Page<Kullanici> findByKurum_Id(
            Long kurumId, Pageable pageable);

    // Kurumun belirli durumdaki kullanıcıları
    Page<Kullanici> findByKurum_IdAndHesapDurumu(
            Long kurumId,
            HesapDurumu hesapDurumu,
            Pageable pageable);

    // Ad veya soyada göre arama
    @Query("""
        SELECT k FROM Kullanici k
        WHERE LOWER(k.ad) LIKE LOWER(CONCAT('%', :arama, '%'))
           OR LOWER(k.soyad) LIKE LOWER(CONCAT('%', :arama, '%'))
           OR LOWER(k.email) LIKE LOWER(CONCAT('%', :arama, '%'))
        """)
    Page<Kullanici> kullaniciAra(
            @Param("arama") String arama,
            Pageable pageable);

    // Oluşturulma tarihine göre kullanıcıları getirme
    List<Kullanici> findByOlusturmaTarihiBetween(
            LocalDateTime baslangic,
            LocalDateTime bitis);

    // Kurumu olmayan kullanıcıları bulma
    Page<Kullanici> findByKurumIsNull(Pageable pageable);

    // Belirli kurumda belirli role sahip kullanıcı sayısı
    long countByKurum_IdAndRol(
            Long kurumId, KullaniciRol rol);

    // Belirli kurumda aktif hesap sayısı
    long countByKurum_IdAndHesapDurumu(
            Long kurumId, HesapDurumu hesapDurumu);
}
