
package com.proje.blokzincirbagisplatformu.controller;

import com.proje.blokzincirbagisplatformu.dto.request.BagisRequest;
import com.proje.blokzincirbagisplatformu.dto.response.BagisResponse;
import com.proje.blokzincirbagisplatformu.mapper.BagisMapper;
import com.proje.blokzincirbagisplatformu.model.Bagis;
import com.proje.blokzincirbagisplatformu.model.enums.BagisDurumu;
import com.proje.blokzincirbagisplatformu.service.BagisService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/bagislar")
public class BagisController {

    private final BagisService bagisService;
    private final BagisMapper bagisMapper;

    public BagisController(
            BagisService bagisService,
            BagisMapper bagisMapper
    ) {
        this.bagisService = bagisService;
        this.bagisMapper = bagisMapper;
    }

    // Yeni bağış oluşturma
    @PostMapping
    public ResponseEntity<BagisResponse> bagisOlustur(
            @Valid @RequestBody BagisRequest request,
            Authentication authentication
    ) {
        String cuzdanAdresi = authentication.getPrincipal().toString();

        Bagis bagis = bagisService.bagisOlustur(
                cuzdanAdresi,
                request
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseOlustur(bagis));
    }

    // ID ile bağış getirme
    @GetMapping("/{id}")
    public ResponseEntity<BagisResponse> idIleGetir(
            @PathVariable Long id
    ) {
        return bagisService.idIleGetir(id)
                .map(this::responseOlustur)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Giriş yapan kullanıcının bağışlarını getirme
    @GetMapping("/benim")
    public ResponseEntity<Page<BagisResponse>> benimBagislarim(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int sayfa,
            @RequestParam(defaultValue = "10") int boyut
    ) {
        String cuzdanAdresi = authentication.getPrincipal().toString();

        Page<BagisResponse> response = bagisService
                .cuzdanAdresiIleBagiscininBagislariniGetir(
                        cuzdanAdresi,
                        PageRequest.of(sayfa, boyut)
                )
                .map(this::responseOlustur);

        return ResponseEntity.ok(response);
    }

    // Belirli kampanyanın bağışlarını getirme
    @GetMapping("/kampanya/{kampanyaId}")
    public ResponseEntity<Page<BagisResponse>> kampanyaBagislariniGetir(
            @PathVariable Long kampanyaId,
            @RequestParam(defaultValue = "0") int sayfa,
            @RequestParam(defaultValue = "10") int boyut
    ) {
        Page<BagisResponse> response = bagisService
                .kampanyaIdIleGetir(
                        kampanyaId,
                        PageRequest.of(sayfa, boyut)
                )
                .map(this::responseOlustur);

        return ResponseEntity.ok(response);
    }

    // Bağış durumuna göre listeleme
    @GetMapping("/durum")
    public ResponseEntity<Page<BagisResponse>> durumaGoreGetir(
            @RequestParam BagisDurumu durum,
            @RequestParam(defaultValue = "0") int sayfa,
            @RequestParam(defaultValue = "10") int boyut
    ) {
        Page<BagisResponse> response = bagisService
                .durumaGoreGetir(
                        durum,
                        PageRequest.of(sayfa, boyut)
                )
                .map(this::responseOlustur);

        return ResponseEntity.ok(response);
    }

    // Bağışçının belirli durumdaki toplam bağış tutarı
    @GetMapping("/bagisci/toplam-tutar")
    public ResponseEntity<BigDecimal> bagisciToplamTutarGetir(
            @RequestParam Long bagisciId,
            @RequestParam BagisDurumu durum
    ) {
        return ResponseEntity.ok(
                bagisService.bagisciToplamTutarGetir(
                        bagisciId,
                        durum
                )
        );
    }

    // Kampanyanın belirli durumdaki toplam bağış tutarı
    @GetMapping("/kampanya/{kampanyaId}/toplam-tutar")
    public ResponseEntity<BigDecimal> kampanyaToplamTutarGetir(
            @PathVariable Long kampanyaId,
            @RequestParam BagisDurumu durum
    ) {
        return ResponseEntity.ok(
                bagisService.kampanyaToplamTutarGetir(
                        kampanyaId,
                        durum
                )
        );
    }

    // Kampanyadaki bağış sayısı
    @GetMapping("/kampanya/{kampanyaId}/sayi")
    public ResponseEntity<Long> kampanyaBagisSayisi(
            @PathVariable Long kampanyaId
    ) {
        return ResponseEntity.ok(
                bagisService.kampanyaBagisSayisi(kampanyaId)
        );
    }

    // Bağışçının toplam bağış sayısı
    @GetMapping("/bagisci/{bagisciId}/sayi")
    public ResponseEntity<Long> bagisciBagisSayisi(
            @PathVariable Long bagisciId
    ) {
        return ResponseEntity.ok(
                bagisService.bagisciBagisSayisi(bagisciId)
        );
    }

    // Bağışın blockchain işlemi var mı?
    @GetMapping("/{id}/blockchain-var-mi")
    public ResponseEntity<Boolean> blockchainIslemiVarMi(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                bagisService.blockchainIslemiVarMi(id)
        );
    }

    // Blockchain transaction hash ile bağış doğrulama
    @PostMapping("/{id}/blockchain-dogrula")
    public ResponseEntity<BagisResponse> blockchainBagisiniDogrula(
            @PathVariable Long id,
            @RequestParam String transactionHash,
            Authentication authentication
    ) {
        String cuzdanAdresi = authentication.getPrincipal().toString();

        Bagis bagis = bagisService.blockchainBagisiniDogrula(
                id,
                transactionHash,
                cuzdanAdresi
        );

        return ResponseEntity.ok(responseOlustur(bagis));
    }

    // Bağış bilgilerini ve blockchain transaction hash değerini response'a ekle
    private BagisResponse responseOlustur(Bagis bagis) {

        BagisResponse response = bagisMapper.entityToResponse(bagis);

        String transactionHash = bagisService
                .transactionHashGetir(bagis.getId())
                .orElse(null);

        return new BagisResponse(
                response.id(),
                response.tutar(),
                response.paraBirimi(),
                response.durum(),
                response.kampanyaId(),
                response.kampanyaBasligi(),
                response.bagisciId(),
                response.bagisciAdSoyad(),
                response.cuzdanId(),
                response.cuzdanAdresi(),
                transactionHash,
                response.olusturmaTarihi()
        );
    }
}
