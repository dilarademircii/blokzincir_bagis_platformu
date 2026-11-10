package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.model.Kullanici;
import com.proje.blokzincirbagisplatformu.model.enums.HesapDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.KullaniciRol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface KullaniciService extends TemelService<Kullanici> {

    /** E-posta adresi zaten kayıtlı mı? */
    boolean emailKayitliMi(String email);

    /** E-posta ile kullanıcı bul */
    Optional<Kullanici> emailIleGetir(String email);

    /** Hesap durumuna göre listele */
    Page<Kullanici> hesapDurumunaGoreGetir(HesapDurumu hesapDurumu, Pageable pageable);

    /** Role göre kullanıcıları listele */
    Page<Kullanici> roleGoreGetir(KullaniciRol rol, Pageable pageable);

    /** Kuruma bağlı kullanıcıları listele */
    Page<Kullanici> kurumIdIleGetir(Long kurumId, Pageable pageable);

    /** Kurum ve hesap durumuna göre filtrele */
    Page<Kullanici> kurumVeHesapDurumunaGoreGetir(Long kurumId, HesapDurumu hesapDurumu, Pageable pageable);

    /** Ad, soyad veya e-postada genel arama */
    Page<Kullanici> ara(String arama, Pageable pageable);

    /** Oluşturulma tarihi aralığındaki kullanıcılar */
    List<Kullanici> tarihAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis);

    /** Kurumu olmayan kullanıcılar */
    Page<Kullanici> kurumsuKullanicilar(Pageable pageable);

    /** Belirli kurumda belirli role sahip kullanıcı sayısı */
    long kurumRolluKullaniciSayisi(Long kurumId, KullaniciRol rol);

    /** Belirli kurumda aktif hesap sayısı */
    long kurumAktifHesapSayisi(Long kurumId, HesapDurumu hesapDurumu);

    /** Kullanıcı hesap durumunu güncelle */
    Kullanici hesapDurumunuGuncelle(Long kullaniciId, HesapDurumu yeniDurum);
}
