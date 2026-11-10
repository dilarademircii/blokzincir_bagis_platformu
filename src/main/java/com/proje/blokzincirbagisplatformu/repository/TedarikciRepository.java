
package com.proje.blokzincirbagisplatformu.repository;

import com.proje.blokzincirbagisplatformu.model.Tedarikci;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TedarikciRepository extends JpaRepository<Tedarikci, Long> {

    // Vergi numarasıyla tedarikçi bulma
    Optional<Tedarikci> findByVergiNumarasi(String vergiNumarasi);

    boolean existsByVergiNumarasi(String vergiNumarasi);

    // E-posta kontrolü
    boolean existsByEmailIgnoreCase(String email);

    Optional<Tedarikci> findByEmailIgnoreCase(String email);

    // Aktif tedarikçiler
    Page<Tedarikci> findByAktifTrue(Pageable pageable);

    // Pasif tedarikçiler
    Page<Tedarikci> findByAktifFalse(Pageable pageable);

    // Firma adına göre arama
    Page<Tedarikci> findByFirmaAdiContainingIgnoreCase(
            String firmaAdi, Pageable pageable);

    // Harcaması bulunan tedarikçiler
    @Query("""
        SELECT DISTINCT t FROM Tedarikci t
        JOIN t.harcamalar h
        """)
    Page<Tedarikci> harcamasiBulunanTedarikcileriGetir(
            Pageable pageable);

    // Tedarikçinin harcama sayısı
    @Query("""
        SELECT COUNT(h) FROM Harcama h
        WHERE h.tedarikci.id = :tedarikciId
        """)
    long harcamaSayisiniGetir(
            @Param("tedarikciId") Long tedarikciId);

    // Firma adı, e-posta veya vergi numarasına göre arama
    @Query("""
        SELECT t FROM Tedarikci t
        WHERE LOWER(t.firmaAdi) LIKE LOWER(CONCAT('%', :arama, '%'))
           OR LOWER(t.email) LIKE LOWER(CONCAT('%', :arama, '%'))
           OR t.vergiNumarasi LIKE CONCAT('%', :arama, '%')
        """)
    Page<Tedarikci> tedarikciAra(
            @Param("arama") String arama,
            Pageable pageable);
}

