
package com.proje.blokzincirbagisplatformu.controller;

import com.proje.blokzincirbagisplatformu.dto.response.BlockchainIslemResponse;
import com.proje.blokzincirbagisplatformu.mapper.BlockchainIslemMapper;
import com.proje.blokzincirbagisplatformu.model.BlockchainIslem;
import com.proje.blokzincirbagisplatformu.model.enums.BlockchainIslemDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.BlockchainIslemTuru;
import com.proje.blokzincirbagisplatformu.service.BagisSozlesmesiService;
import com.proje.blokzincirbagisplatformu.service.BlockchainIslemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainIslemController {

    @Autowired
    private BagisSozlesmesiService bagisSozlesmesiService;

    @Autowired
    private BlockchainIslemService blockchainIslemService;

    @Autowired
    private BlockchainIslemMapper blockchainIslemMapper;

    // Blockchain ağ ve sözleşme bilgilerini getirir
    @GetMapping("/kontrol")
    public Map<String, Object> blockchainKontrol() throws IOException {

        return Map.of(
                "agId", bagisSozlesmesiService.agIdGetir(),
                "sozlesmeAdresi", bagisSozlesmesiService.getContractAddress(),
                "sozlesmeDagitilmis",
                bagisSozlesmesiService.sozlesmeDagitilmisMi()
        );
    }

    // Akıllı sözleşmenin toplam ETH bakiyesini getirir
    @GetMapping("/toplam-bakiye")
    public Map<String, Object> toplamBakiye() throws IOException {

        BigInteger bakiyeWei =
                bagisSozlesmesiService.toplamBakiyeGetir();

        BigDecimal bakiyeEth = new BigDecimal(bakiyeWei)
                .divide(new BigDecimal("1000000000000000000"));

        return Map.of(
                "bakiyeWei", bakiyeWei,
                "bakiyeEth", bakiyeEth
        );
    }

    // Kampanyanın blockchain üzerindeki toplam bağışını getirir
    @GetMapping("/kampanya/{kampanyaId}")
    public Map<String, Object> kampanyaToplamBagis(
            @PathVariable Long kampanyaId
    ) throws IOException {

        BigInteger bakiyeWei =
                bagisSozlesmesiService.kampanyaToplamBagisGetir(
                        BigInteger.valueOf(kampanyaId)
                );

        BigDecimal bakiyeEth = new BigDecimal(bakiyeWei)
                .divide(new BigDecimal("1000000000000000000"));

        return Map.of(
                "kampanyaId", kampanyaId,
                "toplamBagisWei", bakiyeWei,
                "toplamBagisEth", bakiyeEth
        );
    }

    // Blockchain işlemlerini ağ kimliğine göre sayfalı listele
    @GetMapping("/islemler/ag/{chainId}")
    public ResponseEntity<Page<BlockchainIslemResponse>> agIleIslemleriGetir(
            @PathVariable Long chainId,
            @RequestParam(defaultValue = "0") int sayfa,
            @RequestParam(defaultValue = "10") int boyut
    ) {

        Page<BlockchainIslemResponse> response =
                blockchainIslemService.chainIdIleGetir(
                        chainId,
                        PageRequest.of(sayfa, boyut)
                ).map(blockchainIslemMapper::entityToResponse);

        return ResponseEntity.ok(response);
    }

    // İşlem durumuna göre listele
    @GetMapping("/islemler/durum")
    public ResponseEntity<Page<BlockchainIslemResponse>> durumIleIslemleriGetir(
            @RequestParam BlockchainIslemDurumu durum,
            @RequestParam(defaultValue = "0") int sayfa,
            @RequestParam(defaultValue = "10") int boyut
    ) {

        Page<BlockchainIslemResponse> response =
                blockchainIslemService.durumIleGetir(
                        durum,
                        PageRequest.of(sayfa, boyut)
                ).map(blockchainIslemMapper::entityToResponse);

        return ResponseEntity.ok(response);
    }

    // İşlem türüne göre listele
    @GetMapping("/islemler/tur")
    public ResponseEntity<Page<BlockchainIslemResponse>> turIleIslemleriGetir(
            @RequestParam BlockchainIslemTuru islemTuru,
            @RequestParam(defaultValue = "0") int sayfa,
            @RequestParam(defaultValue = "10") int boyut
    ) {

        Page<BlockchainIslemResponse> response =
                blockchainIslemService.islemTuruIleGetir(
                        islemTuru,
                        PageRequest.of(sayfa, boyut)
                ).map(blockchainIslemMapper::entityToResponse);

        return ResponseEntity.ok(response);
    }

    // İşlem hash bilgisiyle kayıt getir
    @GetMapping("/islem/hash/{transactionHash}")
    public ResponseEntity<BlockchainIslemResponse> hashIleIslemGetir(
            @PathVariable String transactionHash
    ) {

        return blockchainIslemService
                .transactionHashIleGetir(transactionHash)
                .map(blockchainIslemMapper::entityToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // İşlem ID ile detay getir
    @GetMapping("/islem/{islemId}")
    public ResponseEntity<BlockchainIslemResponse> islemDetayGetir(
            @PathVariable Long islemId
    ) {

        return blockchainIslemService.detayliGetir(islemId)
                .map(blockchainIslemMapper::entityToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Bir bağışa ait blockchain işlemlerini getir
    @GetMapping("/islemler/bagis/{bagisId}")
    public ResponseEntity<List<BlockchainIslemResponse>> bagisIslemleriniGetir(
            @PathVariable Long bagisId
    ) {

        List<BlockchainIslemResponse> response =
                blockchainIslemService.bagisIdIleGetir(bagisId)
                        .stream()
                        .map(blockchainIslemMapper::entityToResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }

    // Bir harcamaya ait blockchain işlemlerini getir
    @GetMapping("/islemler/harcama/{harcamaId}")
    public ResponseEntity<List<BlockchainIslemResponse>> harcamaIslemleriniGetir(
            @PathVariable Long harcamaId
    ) {

        List<BlockchainIslemResponse> response =
                blockchainIslemService.harcamaIdIleGetir(harcamaId)
                        .stream()
                        .map(blockchainIslemMapper::entityToResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }

    // Onaylanmış işlemleri listele
    @GetMapping("/islemler/onaylanmis")
    public ResponseEntity<Page<BlockchainIslemResponse>> onaylanmisIslemleriGetir(
            @RequestParam(defaultValue = "0") int sayfa,
            @RequestParam(defaultValue = "10") int boyut
    ) {

        Page<BlockchainIslemResponse> response =
                blockchainIslemService.onaylanmisIslemler(
                        PageRequest.of(sayfa, boyut)
                ).map(blockchainIslemMapper::entityToResponse);

        return ResponseEntity.ok(response);
    }

    // Henüz onaylanmamış işlemleri listele
    @GetMapping("/islemler/onaylanmamis")
    public ResponseEntity<Page<BlockchainIslemResponse>> onaylanmamisIslemleriGetir(
            @RequestParam(defaultValue = "0") int sayfa,
            @RequestParam(defaultValue = "10") int boyut
    ) {

        Page<BlockchainIslemResponse> response =
                blockchainIslemService.onaylanmamisIslemler(
                        PageRequest.of(sayfa, boyut)
                ).map(blockchainIslemMapper::entityToResponse);

        return ResponseEntity.ok(response);
    }
    // Belirli tarih aralığındaki blockchain işlemlerini getir
    @GetMapping("/islemler/tarih")
    public ResponseEntity<Page<BlockchainIslemResponse>> tarihAraligiIleIslemleriGetir(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime baslangic,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime bitis,

            @RequestParam(defaultValue = "0") int sayfa,
            @RequestParam(defaultValue = "10") int boyut
    ) {

        Page<BlockchainIslemResponse> response =
                blockchainIslemService.tarihAraligiIleGetir(
                        baslangic,
                        bitis,
                        PageRequest.of(sayfa, boyut)
                ).map(blockchainIslemMapper::entityToResponse);

        return ResponseEntity.ok(response);
    }
    // Belirli ağdaki bekleyen işlemleri getir
    @GetMapping("/islemler/bekleyen")
    public ResponseEntity<List<BlockchainIslemResponse>> bekleyenIslemleriGetir(
            @RequestParam Long chainId
    ) {

        List<BlockchainIslemResponse> response =
                blockchainIslemService.bekleyenIslemleriGetir(
                                chainId,
                                BlockchainIslemDurumu.BEKLEMEDE
                        ).stream()
                        .map(blockchainIslemMapper::entityToResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }
    // Ağ ve duruma göre işlem sayısını getir
    @GetMapping("/islemler/sayi")
    public ResponseEntity<Long> islemSayisiGetir(
            @RequestParam Long chainId,
            @RequestParam BlockchainIslemDurumu durum
    ) {

        return ResponseEntity.ok(
                blockchainIslemService.chainIdVeDurumSayisi(
                        chainId,
                        durum
                )
        );
    }
}
