
package com.proje.blokzincirbagisplatformu.controller;

import com.proje.blokzincirbagisplatformu.dto.request.CuzdanEkleRequest;
import com.proje.blokzincirbagisplatformu.dto.response.CuzdanResponse;
import com.proje.blokzincirbagisplatformu.mapper.CuzdanMapper;
import com.proje.blokzincirbagisplatformu.model.Cuzdan;
import com.proje.blokzincirbagisplatformu.model.enums.CuzdanDurumu;
import com.proje.blokzincirbagisplatformu.service.CuzdanService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuzdanlar")
@Validated
public class CuzdanController {

    private final CuzdanService cuzdanService;
    private final CuzdanMapper cuzdanMapper;

    public CuzdanController(
            CuzdanService cuzdanService,
            CuzdanMapper cuzdanMapper
    ) {
        this.cuzdanService = cuzdanService;
        this.cuzdanMapper = cuzdanMapper;
    }

    // Kullanıcıya yeni cüzdan ekleme
    @PostMapping("/kullanici/{kullaniciId}")
    public ResponseEntity<CuzdanResponse> cuzdanEkle(
            @PathVariable Long kullaniciId,
            @Valid @RequestBody CuzdanEkleRequest request
    ) {
        Cuzdan cuzdan = cuzdanService.cuzdanEkle(kullaniciId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cuzdanMapper.entityToResponse(cuzdan));
    }

    // Bütün cüzdanları listeleme
    @GetMapping
    public ResponseEntity<List<CuzdanResponse>> tumCuzdanlariGetir() {

        List<CuzdanResponse> response = cuzdanService.tumunuGetir()
                .stream()
                .map(cuzdanMapper::entityToResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    // ID ile cüzdan getirme
    @GetMapping("/{id}")
    public ResponseEntity<CuzdanResponse> idIleGetir(
            @PathVariable Long id
    ) {
        return cuzdanService.idIleGetir(id)
                .map(cuzdanMapper::entityToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Cüzdan adresi kayıtlı mı kontrol etme
    @GetMapping("/adres-kontrol")
    public ResponseEntity<Boolean> cuzdanAdresiKayitliMi(
            @RequestParam String cuzdanAdresi
    ) {
        return ResponseEntity.ok(
                cuzdanService.cuzdanAdresiKayitliMi(cuzdanAdresi)
        );
    }

    // Cüzdan adresine göre arama
    @GetMapping("/adrese-gore")
    public ResponseEntity<CuzdanResponse> adreseGoreBul(
            @RequestParam String cuzdanAdresi
    ) {
        return cuzdanService.adreseGoreBul(cuzdanAdresi)
                .map(cuzdanMapper::entityToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Kullanıcının bütün cüzdanlarını getirme
    @GetMapping("/kullanici/{kullaniciId}")
    public ResponseEntity<List<CuzdanResponse>> kullaniciCuzdanlariniGetir(
            @PathVariable Long kullaniciId
    ) {
        List<CuzdanResponse> response = cuzdanService
                .kullaniciIdIleGetir(kullaniciId)
                .stream()
                .map(cuzdanMapper::entityToResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    // Kullanıcı ve cüzdan durumuna göre listeleme
    @GetMapping("/kullanici/{kullaniciId}/durum")
    public ResponseEntity<List<CuzdanResponse>> kullaniciVeDurumIleGetir(
            @PathVariable Long kullaniciId,
            @RequestParam CuzdanDurumu durum
    ) {
        List<CuzdanResponse> response = cuzdanService
                .kullaniciVeDurumIleGetir(kullaniciId, durum)
                .stream()
                .map(cuzdanMapper::entityToResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    // Duruma göre cüzdanları listeleme
    @GetMapping("/durum")
    public ResponseEntity<List<CuzdanResponse>> durumIleGetir(
            @RequestParam CuzdanDurumu durum
    ) {
        List<CuzdanResponse> response = cuzdanService
                .durumIleGetir(
                        durum,
                        org.springframework.data.domain.Pageable.unpaged()
                )
                .getContent()
                .stream()
                .map(cuzdanMapper::entityToResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    // Ağ adına göre cüzdanları listeleme
    @GetMapping("/ag")
    public ResponseEntity<List<CuzdanResponse>> agAdiIleGetir(
            @RequestParam String agAdi
    ) {
        List<CuzdanResponse> response = cuzdanService
                .agAdiIleGetir(
                        agAdi,
                        org.springframework.data.domain.Pageable.unpaged()
                )
                .getContent()
                .stream()
                .map(cuzdanMapper::entityToResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    // Kullanıcının belirli durumda cüzdanı var mı?
    @GetMapping("/kullanici/{kullaniciId}/var-mi")
    public ResponseEntity<Boolean> kullaniciDurumluCuzdaniVarMi(
            @PathVariable Long kullaniciId,
            @RequestParam CuzdanDurumu durum
    ) {
        return ResponseEntity.ok(
                cuzdanService.kullaniciDurumluCuzdaniVarMi(
                        kullaniciId,
                        durum
                )
        );
    }

    // Doğrulanmış cüzdanları listeleme
    @GetMapping("/dogrulanmis")
    public ResponseEntity<List<CuzdanResponse>> dogrulanmisCuzdanlariGetir() {

        List<CuzdanResponse> response = cuzdanService
                .dogrulanmisCuzdanlar(
                        org.springframework.data.domain.Pageable.unpaged()
                )
                .getContent()
                .stream()
                .map(cuzdanMapper::entityToResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    // Kullanıcının cüzdan sayısını getirme
    @GetMapping("/kullanici/{kullaniciId}/sayi")
    public ResponseEntity<Long> kullaniciCuzdanSayisi(
            @PathVariable Long kullaniciId
    ) {
        return ResponseEntity.ok(
                cuzdanService.kullaniciCuzdanSayisi(kullaniciId)
        );
    }

    // Belirli ağdaki cüzdan sayısını getirme
    @GetMapping("/ag/sayi")
    public ResponseEntity<Long> agCuzdanSayisi(
            @RequestParam String agAdi
    ) {
        return ResponseEntity.ok(
                cuzdanService.agCuzdanSayisi(agAdi)
        );
    }

    // Adres ve ağ bilgisiyle cüzdan arama
    @GetMapping("/adres-ag")
    public ResponseEntity<CuzdanResponse> adresVeAgIleBul(
            @RequestParam String adres,
            @RequestParam String agAdi
    ) {
        return cuzdanService.adresVeAgIleBul(adres, agAdi)
                .map(cuzdanMapper::entityToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // MetaMask doğrulaması için nonce oluşturma
    @PostMapping("/nonce")
    public ResponseEntity<NonceResponse> nonceOlustur(
            @Valid @RequestBody NonceRequest request
    ) {
        String nonce = cuzdanService.nonceOlustur(
                request.cuzdanAdresi()
        );

        return ResponseEntity.ok(
                new NonceResponse(nonce)
        );
    }

    // MetaMask imzasını doğrulama ve JWT üretme
    @PostMapping("/imza-dogrula")
    public ResponseEntity<TokenResponse> imzaDogrula(
            @Valid @RequestBody ImzaDogrulaRequest request
    ) {
        String token = cuzdanService.imzaDogrulaVeTokenUret(
                request.cuzdanAdresi(),
                request.imzalananMesaj(),
                request.imza()
        );

        return ResponseEntity.ok(
                new TokenResponse(token)
        );
    }

    // Cüzdan silme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> sil(
            @PathVariable Long id
    ) {
        cuzdanService.sil(id);

        return ResponseEntity.noContent().build();
    }

    public record NonceRequest(
            @NotBlank(message = "Cüzdan adresi boş bırakılamaz.")
            String cuzdanAdresi
    ) {
    }

    public record NonceResponse(
            String nonce
    ) {
    }

    public record ImzaDogrulaRequest(
            @NotBlank(message = "Cüzdan adresi boş bırakılamaz.")
            String cuzdanAdresi,

            @NotBlank(message = "İmzalanan mesaj boş bırakılamaz.")
            String imzalananMesaj,

            @NotBlank(message = "İmza boş bırakılamaz.")
            String imza
    ) {
    }

    public record TokenResponse(
            String token
    ) {
    }
}
