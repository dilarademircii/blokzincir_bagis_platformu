
package com.proje.blokzincirbagisplatformu.controller;

import com.proje.blokzincirbagisplatformu.service.BagisSozlesmesiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainIslemController {

    @Autowired
    private BagisSozlesmesiService bagisSozlesmesiService;

    @GetMapping("/kontrol")
    public Map<String, Object> blockchainKontrol() throws IOException {

        return Map.of(
                "agId", bagisSozlesmesiService.agIdGetir(),
                "sozlesmeAdresi", bagisSozlesmesiService.getContractAddress(),
                "sozlesmeDagitilmis",
                bagisSozlesmesiService.sozlesmeDagitilmisMi()
        );
    }

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

}
