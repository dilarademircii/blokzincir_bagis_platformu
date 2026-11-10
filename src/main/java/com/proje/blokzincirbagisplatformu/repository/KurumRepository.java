
package com.proje.blokzincirbagisplatformu.repository;

import com.proje.blokzincirbagisplatformu.model.Kurum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KurumRepository extends JpaRepository<Kurum, Long> {

    // Vergi numarası daha önce kayıt edilmiş mi?
    boolean existsByVergiNumarasi(String vergiNumarasi);

    // E-posta adresine göre kurum bulma
    Optional<Kurum> findByEmail(String email);

    // Kurum adına göre arama
    Page<Kurum> findByKurumAdiContainingIgnoreCase(
            String kurumAdi, Pageable pageable);

    // Aktif veya pasif kurumları listeleme
    Page<Kurum> findByAktif(Boolean aktif, Pageable pageable);

    // Vergi numarasına göre kurum bulma
    Optional<Kurum> findByVergiNumarasi(String vergiNumarasi);

    // Kurumun kampanyalarını kontrol etmek için kampanya sayısı
    @Query("""
        SELECT COUNT(k)
        FROM Kampanya k
        WHERE k.kurum.id = :kurumId
        """)
    long kampanyaSayisiniGetir(@Param("kurumId") Long kurumId);

    // Kurumun kullanıcı sayısı
    @Query("""
        SELECT COUNT(k)
        FROM Kullanici k
        WHERE k.kurum.id = :kurumId
        """)
    long kullaniciSayisiniGetir(@Param("kurumId") Long kurumId);
}
