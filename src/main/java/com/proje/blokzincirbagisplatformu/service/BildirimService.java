package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.model.Bildirim;
import com.proje.blokzincirbagisplatformu.model.enums.BildirimDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.BildirimTuru;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface BildirimService extends TemelService<Bildirim> {

    /** Kullanıcının tüm bildirimleri (sayfalı) */
    Page<Bildirim> kullaniciIdIleGetir(Long kullaniciId, Pageable pageable);

    /** Kullanıcının belirli durumdaki bildirimleri */
    Page<Bildirim> kullaniciVeDurumIleGetir(Long kullaniciId, BildirimDurumu durum, Pageable pageable);

    /** Kullanıcının belirli durumdaki bildirimleri — tarih azalan sırada */
    Page<Bildirim> kullaniciDurumTarihSiraliGetir(Long kullaniciId, BildirimDurumu durum, Pageable pageable);

    /** Bildirim türüne göre listele */
    Page<Bildirim> bildirimTuruIleGetir(BildirimTuru bildirimTuru, Pageable pageable);

    /** Kullanıcı ve bildirim türüne göre listele */
    Page<Bildirim> kullaniciVeTurIleGetir(Long kullaniciId, BildirimTuru bildirimTuru, Pageable pageable);

    /** Kullanıcının okunmamış bildirim sayısı */
    long okunmamisBildirimSayisi(Long kullaniciId, BildirimDurumu durum);

    /** Kullanıcının belirli tarihlerdeki bildirimleri */
    Page<Bildirim> kullaniciTarihAraligiIleGetir(Long kullaniciId, LocalDateTime baslangic, LocalDateTime bitis, Pageable pageable);

    /** Okunmuş bildirimleri belirli tarihten önce bul */
    Page<Bildirim> okunmusDurumVeTarihtenOncekiler(BildirimDurumu durum, LocalDateTime tarih, Pageable pageable);

    /** Kullanıcının en yeni bildirimleri */
    Page<Bildirim> enYeniBildirimleriGetir(Long kullaniciId, Pageable pageable);

    /** Bildirimin belirtilen kullanıcıya ait olup olmadığını doğrula */
    boolean kullaniciyaAitMi(Long bildirimId, Long kullaniciId);

    /** Bildirimi okundu olarak işaretle */
    Bildirim okunduIsaretle(Long bildirimId);
}
