
package com.proje.blokzincirbagisplatformu.controller;

import com.proje.blokzincirbagisplatformu.dto.response.KullaniciResponse;
import com.proje.blokzincirbagisplatformu.mapper.KullaniciMapper;
import com.proje.blokzincirbagisplatformu.model.Kullanici;
import com.proje.blokzincirbagisplatformu.model.enums.HesapDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.KullaniciRol;
import com.proje.blokzincirbagisplatformu.service.KullaniciService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/kullanicilar")
public class KullaniciController {

    private final KullaniciService kullaniciService;
    private final KullaniciMapper kullaniciMapper;

    public KullaniciController(
            KullaniciService kullaniciService,
            KullaniciMapper kullaniciMapper) {
        this.kullaniciService = kullaniciService;
        this.kullaniciMapper = kullaniciMapper;
    }

    // Tüm kullanıcıları listele
    @GetMapping
    public ResponseEntity<List<KullaniciResponse>> tumunuGetir() {

        List<KullaniciResponse> kullanicilar =
                kullaniciService.tumunuGetir()
                        .stream()
                        .map(kullaniciMapper::entityToResponse)
                        .toList();

        return ResponseEntity.ok(kullanicilar);
    }

    // ID ile kullanıcı getir
    @GetMapping("/{id}")
    public ResponseEntity<KullaniciResponse> idIleGetir(
            @PathVariable Long id) {

        return kullaniciService.idIleGetir(id)
                .map(kullaniciMapper::entityToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // E-posta ile kullanıcı getir
    @GetMapping("/email")
    public ResponseEntity<KullaniciResponse> emailIleGetir(
            @RequestParam String email) {

        return kullaniciService.emailIleGetir(email)
                .map(kullaniciMapper::entityToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // E-posta kayıtlı mı kontrol et
    @GetMapping("/email-kontrol")
    public ResponseEntity<Boolean> emailKayitliMi(
            @RequestParam String email) {

        return ResponseEntity.ok(
                kullaniciService.emailKayitliMi(email)
        );
    }

    // Hesap durumuna göre kullanıcıları listele
    @GetMapping("/hesap-durumu")
    public ResponseEntity<Page<KullaniciResponse>> hesapDurumunaGoreGetir(
            @RequestParam HesapDurumu hesapDurumu,
            Pageable pageable) {

        Page<KullaniciResponse> sonuc =
                kullaniciService.hesapDurumunaGoreGetir(hesapDurumu, pageable)
                        .map(kullaniciMapper::entityToResponse);

        return ResponseEntity.ok(sonuc);
    }

    // Role göre kullanıcıları listele
    @GetMapping("/rol")
    public ResponseEntity<Page<KullaniciResponse>> roleGoreGetir(
            @RequestParam KullaniciRol rol,
            Pageable pageable) {

        Page<KullaniciResponse> sonuc =
                kullaniciService.roleGoreGetir(rol, pageable)
                        .map(kullaniciMapper::entityToResponse);

        return ResponseEntity.ok(sonuc);
    }

    // Kuruma bağlı kullanıcıları listele
    @GetMapping("/kurum/{kurumId}")
    public ResponseEntity<Page<KullaniciResponse>> kurumIdIleGetir(
            @PathVariable Long kurumId,
            Pageable pageable) {

        Page<KullaniciResponse> sonuc =
                kullaniciService.kurumIdIleGetir(kurumId, pageable)
                        .map(kullaniciMapper::entityToResponse);

        return ResponseEntity.ok(sonuc);
    }

    // Kurum ve hesap durumuna göre filtrele
    @GetMapping("/kurum/{kurumId}/hesap-durumu")
    public ResponseEntity<Page<KullaniciResponse>> kurumVeHesapDurumunaGoreGetir(
            @PathVariable Long kurumId,
            @RequestParam HesapDurumu hesapDurumu,
            Pageable pageable) {

        Page<KullaniciResponse> sonuc =
                kullaniciService.kurumVeHesapDurumunaGoreGetir(
                                kurumId, hesapDurumu, pageable)
                        .map(kullaniciMapper::entityToResponse);

        return ResponseEntity.ok(sonuc);
    }

    // Ad, soyad veya e-postaya göre arama
    @GetMapping("/ara")
    public ResponseEntity<Page<KullaniciResponse>> ara(
            @RequestParam String arama,
            Pageable pageable) {

        Page<KullaniciResponse> sonuc =
                kullaniciService.ara(arama, pageable)
                        .map(kullaniciMapper::entityToResponse);

        return ResponseEntity.ok(sonuc);
    }

    // Tarih aralığına göre kullanıcıları getir
    @GetMapping("/tarih-araligi")
    public ResponseEntity<List<KullaniciResponse>> tarihAraligiIleGetir(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime baslangic,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime bitis) {

        List<KullaniciResponse> sonuc =
                kullaniciService.tarihAraligiIleGetir(baslangic, bitis)
                        .stream()
                        .map(kullaniciMapper::entityToResponse)
                        .toList();

        return ResponseEntity.ok(sonuc);
    }

    // Kurumu olmayan kullanıcıları listele
    @GetMapping("/kurumsuz")
    public ResponseEntity<Page<KullaniciResponse>> kurumsuzKullanicilar(
            Pageable pageable) {

        Page<KullaniciResponse> sonuc =
                kullaniciService.kurumsuKullanicilar(pageable)
                        .map(kullaniciMapper::entityToResponse);

        return ResponseEntity.ok(sonuc);
    }

    // Kurumdaki belirli role sahip kullanıcı sayısı
    @GetMapping("/kurum/{kurumId}/rol-sayisi")
    public ResponseEntity<Long> kurumRolluKullaniciSayisi(
            @PathVariable Long kurumId,
            @RequestParam KullaniciRol rol) {

        return ResponseEntity.ok(
                kullaniciService.kurumRolluKullaniciSayisi(kurumId, rol)
        );
    }

    // Kurumdaki belirli hesap durumuna sahip kullanıcı sayısı
    @GetMapping("/kurum/{kurumId}/hesap-sayisi")
    public ResponseEntity<Long> kurumAktifHesapSayisi(
            @PathVariable Long kurumId,
            @RequestParam HesapDurumu hesapDurumu) {

        return ResponseEntity.ok(
                kullaniciService.kurumAktifHesapSayisi(kurumId, hesapDurumu)
        );
    }

    // Kullanıcı hesap durumunu güncelle
    @PatchMapping("/{id}/hesap-durumu")
    public ResponseEntity<KullaniciResponse> hesapDurumunuGuncelle(
            @PathVariable Long id,
            @RequestParam HesapDurumu yeniDurum) {

        Kullanici kullanici =
                kullaniciService.hesapDurumunuGuncelle(id, yeniDurum);

        return ResponseEntity.ok(
                kullaniciMapper.entityToResponse(kullanici)
        );
    }

    // Kullanıcı sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> sil(@PathVariable Long id) {

        kullaniciService.sil(id);

        return ResponseEntity.noContent().build();
    }
}
