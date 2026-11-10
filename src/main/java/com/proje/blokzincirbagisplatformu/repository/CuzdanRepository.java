
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

    // Cüzdan adresi daha önce kayıt edilmiş mi?
    boolean existsByCuzdanAdresiIgnoreCase(String cuzdanAdresi);

    Optional<Cuzdan> findByCuzdanAdresiIgnoreCase(String cuzdanAdresi);

    // Kullanıcıya ait cüzdanlar
    List<Cuzdan> findByKullanici_Id(Long kullaniciId);

    // Kullanıcının belirli durumdaki cüzdanları
    List<Cuzdan> findByKullanici_IdAndDurum(
            Long kullaniciId, CuzdanDurumu durum);

    // Doğrulanmış cüzdanları sayfalı listeleme
    Page<Cuzdan> findByDurum(
            CuzdanDurumu durum, Pageable pageable);

    // Belirli ağdaki cüzdanlar
    Page<Cuzdan> findByAgAdiIgnoreCase(
            String agAdi, Pageable pageable);

    // Kullanıcının doğrulanmış cüzdanı var mı?
    boolean existsByKullanici_IdAndDurum(
            Long kullaniciId, CuzdanDurumu durum);

    // Doğrulanma tarihi bulunan cüzdanlar
    Page<Cuzdan> findByDogrulamaTarihiIsNotNull(
            Pageable pageable);

    // Kullanıcının cüzdan sayısı
    long countByKullanici_Id(Long kullaniciId);

    // Bir ağdaki cüzdan sayısı
    long countByAgAdiIgnoreCase(String agAdi);

    // Adres ve ağ bilgisiyle arama
    @Query("""
        SELECT c FROM Cuzdan c
        WHERE LOWER(c.cuzdanAdresi) = LOWER(:adres)
          AND LOWER(c.agAdi) = LOWER(:agAdi)
        """)
    Optional<Cuzdan> adresVeAgIleBul(
            @Param("adres") String adres,
            @Param("agAdi") String agAdi);
}
