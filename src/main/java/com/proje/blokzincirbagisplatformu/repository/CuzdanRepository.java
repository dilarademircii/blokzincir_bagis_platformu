
package com.proje.blokzincirbagisplatformu.repository;

import com.proje.blokzincirbagisplatformu.model.Cuzdan;
import com.proje.blokzincirbagisplatformu.model.enums.CuzdanDurumu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CuzdanRepository extends JpaRepository<Cuzdan, Long> {

    boolean existsByCuzdanAdresiIgnoreCase(String cuzdanAdresi);

    Optional<Cuzdan> findByCuzdanAdresiIgnoreCase(String cuzdanAdresi);

    List<Cuzdan> findByKullanici_Id(Long kullaniciId);

    List<Cuzdan> findByKullanici_IdAndDurum(
            Long kullaniciId,
            CuzdanDurumu durum
    );

    Page<Cuzdan> findByDurum(
            CuzdanDurumu durum,
            Pageable pageable
    );

    Page<Cuzdan> findByAgAdiIgnoreCase(
            String agAdi,
            Pageable pageable
    );

    boolean existsByKullanici_IdAndDurum(
            Long kullaniciId,
            CuzdanDurumu durum
    );

    Page<Cuzdan> findByDogrulamaTarihiIsNotNull(
            Pageable pageable
    );

    long countByKullanici_Id(Long kullaniciId);

    long countByAgAdiIgnoreCase(String agAdi);

    @Query("""
            SELECT c FROM Cuzdan c
            WHERE LOWER(c.cuzdanAdresi) = LOWER(:adres)
              AND LOWER(c.agAdi) = LOWER(:agAdi)
            """)
    Optional<Cuzdan> adresVeAgIleBul(
            @Param("adres") String adres,
            @Param("agAdi") String agAdi
    );

    // Cüzdan adresine göre nonce bilgisiyle birlikte cüzdanı bulur.
    @Query("""
            SELECT c FROM Cuzdan c
            WHERE LOWER(c.cuzdanAdresi) = LOWER(:adres)
            """)
    Optional<Cuzdan> nonceIleDogrulanacakCuzdaniBul(
            @Param("adres") String adres
    );
}

