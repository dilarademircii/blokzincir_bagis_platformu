
package com.proje.blokzincirbagisplatformu.repository;

import com.proje.blokzincirbagisplatformu.model.Bildirim;
import com.proje.blokzincirbagisplatformu.model.enums.BildirimDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.BildirimTuru;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface BildirimRepository extends JpaRepository<Bildirim, Long> {

    // Kullanıcının bütün bildirimleri
    Page<Bildirim> findByKullanici_Id(
            Long kullaniciId, Pageable pageable);

    // Kullanıcının belirli durumdaki bildirimleri
    Page<Bildirim> findByKullanici_IdAndDurum(
            Long kullaniciId,
            BildirimDurumu durum,
            Pageable pageable);

    // Kullanıcının okunmamış bildirimleri
    Page<Bildirim> findByKullanici_IdAndDurumOrderByOlusturmaTarihiDesc(
            Long kullaniciId,
            BildirimDurumu durum,
            Pageable pageable);

    // Bildirim türüne göre listeleme
    Page<Bildirim> findByBildirimTuru(
            BildirimTuru bildirimTuru, Pageable pageable);

    // Kullanıcı ve bildirim türüne göre listeleme
    Page<Bildirim> findByKullanici_IdAndBildirimTuru(
            Long kullaniciId,
            BildirimTuru bildirimTuru,
            Pageable pageable);

    // Kullanıcının okunmamış bildirim sayısı
    long countByKullanici_IdAndDurum(
            Long kullaniciId, BildirimDurumu durum);

    // Kullanıcının belirli tarihlerde aldığı bildirimler
    Page<Bildirim> findByKullanici_IdAndOlusturmaTarihiBetween(
            Long kullaniciId,
            LocalDateTime baslangic,
            LocalDateTime bitis,
            Pageable pageable);

    // Okunmuş bildirimleri belirli tarihten önce bulma
    Page<Bildirim> findByDurumAndOkunmaTarihiBefore(
            BildirimDurumu durum,
            LocalDateTime tarih,
            Pageable pageable);

    // Kullanıcıya ait en yeni bildirimleri getirme
    @Query("""
        SELECT b FROM Bildirim b
        WHERE b.kullanici.id = :kullaniciId
        ORDER BY b.olusturmaTarihi DESC
        """)
    Page<Bildirim> enYeniBildirimleriGetir(
            @Param("kullaniciId") Long kullaniciId,
            Pageable pageable);

    // Bildirimin ilgili kullanıcıya ait olup olmadığını kontrol etme
    boolean existsByIdAndKullanici_Id(
            Long bildirimId, Long kullaniciId);
}

