
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

    boolean existsByEmailIgnoreCase(String email);

    Optional<Kullanici> findByEmailIgnoreCase(String email);

    Page<Kullanici> findByHesapDurumu(
            HesapDurumu hesapDurumu,
            Pageable pageable
    );

    Page<Kullanici> findByRol(
            KullaniciRol rol,
            Pageable pageable
    );

    Page<Kullanici> findByKurum_Id(
            Long kurumId,
            Pageable pageable
    );

    Page<Kullanici> findByKurum_IdAndHesapDurumu(
            Long kurumId,
            HesapDurumu hesapDurumu,
            Pageable pageable
    );

    @Query("""
            SELECT k FROM Kullanici k
            WHERE LOWER(k.ad) LIKE LOWER(CONCAT('%', :arama, '%'))
               OR LOWER(k.soyad) LIKE LOWER(CONCAT('%', :arama, '%'))
               OR LOWER(k.email) LIKE LOWER(CONCAT('%', :arama, '%'))
            """)
    Page<Kullanici> kullaniciAra(
            @Param("arama") String arama,
            Pageable pageable
    );

    List<Kullanici> findByOlusturmaTarihiBetween(
            LocalDateTime baslangic,
            LocalDateTime bitis
    );

    Page<Kullanici> findByKurumIsNull(Pageable pageable);

    long countByKurum_IdAndRol(
            Long kurumId,
            KullaniciRol rol
    );

    long countByKurum_IdAndHesapDurumu(
            Long kurumId,
            HesapDurumu hesapDurumu
    );
}
