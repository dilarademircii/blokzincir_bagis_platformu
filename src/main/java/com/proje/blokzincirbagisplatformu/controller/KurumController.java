
package com.proje.blokzincirbagisplatformu.controller;

import com.proje.blokzincirbagisplatformu.dto.request.KurumGuncelleRequest;
import com.proje.blokzincirbagisplatformu.dto.request.KurumRequest;
import com.proje.blokzincirbagisplatformu.dto.response.KurumResponse;
import com.proje.blokzincirbagisplatformu.mapper.KurumMapper;
import com.proje.blokzincirbagisplatformu.model.Kurum;
import com.proje.blokzincirbagisplatformu.service.KurumService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/kurumlar")
public class KurumController {

    private final KurumService kurumService;
    private final KurumMapper kurumMapper;

    public KurumController(
            KurumService kurumService,
            KurumMapper kurumMapper) {
        this.kurumService = kurumService;
        this.kurumMapper = kurumMapper;
    }

    // Yeni kurum kaydet
    @PostMapping
    public ResponseEntity<KurumResponse> kaydet(
            @Valid @RequestBody KurumRequest request) {

        Kurum kurum = kurumMapper.requestToEntity(request);
        kurum.setAktif(true);

        Kurum kaydedilenKurum = kurumService.kaydet(kurum);

        return ResponseEntity.ok(responseOlustur(kaydedilenKurum));
    }

    // Tüm kurumları listele
    @GetMapping
    public ResponseEntity<List<KurumResponse>> tumunuGetir() {

        List<KurumResponse> kurumlar =
                kurumService.tumunuGetir()
                        .stream()
                        .map(this::responseOlustur)
                        .toList();

        return ResponseEntity.ok(kurumlar);
    }

    // ID ile kurum getir
    @GetMapping("/{id}")
    public ResponseEntity<KurumResponse> idIleGetir(
            @PathVariable Long id) {

        return kurumService.idIleGetir(id)
                .map(this::responseOlustur)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Vergi numarasına göre kurum getir
    @GetMapping("/vergi-numarasi")
    public ResponseEntity<KurumResponse> vergiNumarasinaGoreGetir(
            @RequestParam String vergiNumarasi) {

        return kurumService.vergiNumarasinaGoreGetir(vergiNumarasi)
                .map(this::responseOlustur)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // E-posta ile kurum getir
    @GetMapping("/email")
    public ResponseEntity<KurumResponse> emailIleGetir(
            @RequestParam String email) {

        return kurumService.emailIleGetir(email)
                .map(this::responseOlustur)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Kurum adına göre ara
    @GetMapping("/ara")
    public ResponseEntity<Page<KurumResponse>> adaGoreAra(
            @RequestParam String kurumAdi,
            Pageable pageable) {

        Page<KurumResponse> sonuc =
                kurumService.adaGoreAra(kurumAdi, pageable)
                        .map(this::responseOlustur);

        return ResponseEntity.ok(sonuc);
    }

    // Aktiflik durumuna göre kurumları listele
    @GetMapping("/aktiflik")
    public ResponseEntity<Page<KurumResponse>> aktiflikDurumunaGoreGetir(
            @RequestParam Boolean aktif,
            Pageable pageable) {

        Page<KurumResponse> sonuc =
                kurumService.aktiflikDurumunaGoreGetir(aktif, pageable)
                        .map(this::responseOlustur);

        return ResponseEntity.ok(sonuc);
    }

    // Kurum bilgilerini güncelle
    @PutMapping("/{id}")
    public ResponseEntity<KurumResponse> guncelle(
            @PathVariable Long id,
            @Valid @RequestBody KurumGuncelleRequest request) {

        Optional<Kurum> bulunanKurum = kurumService.idIleGetir(id);

        if (bulunanKurum.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Kurum kurum = bulunanKurum.get();

        kurumMapper.guncelleRequestToEntity(request, kurum);

        Kurum guncellenenKurum = kurumService.kaydet(kurum);

        return ResponseEntity.ok(responseOlustur(guncellenenKurum));
    }

    // Kurumun aktiflik durumunu güncelle
    @PatchMapping("/{id}/aktiflik")
    public ResponseEntity<KurumResponse> kurumDurumunuGuncelle(
            @PathVariable Long id,
            @RequestParam Boolean aktif) {

        Kurum kurum = kurumService.kurumDurumunuGuncelle(id, aktif);

        return ResponseEntity.ok(responseOlustur(kurum));
    }

    // Kurumun kampanya sayısını getir
    @GetMapping("/{id}/kampanya-sayisi")
    public ResponseEntity<Long> kampanyaSayisiniGetir(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                kurumService.kampanyaSayisiniGetir(id)
        );
    }

    // Kurumun kullanıcı sayısını getir
    @GetMapping("/{id}/kullanici-sayisi")
    public ResponseEntity<Long> kullaniciSayisiniGetir(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                kurumService.kullaniciSayisiniGetir(id)
        );
    }

    // Kurum sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> sil(@PathVariable Long id) {

        kurumService.sil(id);

        return ResponseEntity.noContent().build();
    }

    // Response DTO oluştur ve sayısal bilgileri doldur
    private KurumResponse responseOlustur(Kurum kurum) {

        Long kullaniciSayisi =
                kurumService.kullaniciSayisiniGetir(kurum.getId());

        Long kampanyaSayisi =
                kurumService.kampanyaSayisiniGetir(kurum.getId());

        KurumResponse response = kurumMapper.entityToResponse(kurum);

        return new KurumResponse(
                response.id(),
                response.kurumAdi(),
                response.vergiNumarasi(),
                response.adres(),
                response.telefon(),
                response.email(),
                response.aciklama(),
                response.aktif(),
                kullaniciSayisi,
                kampanyaSayisi,
                response.olusturmaTarihi()
        );
    }
}

