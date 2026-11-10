package com.proje.blokzincirbagisplatformu.dto.response;

import com.proje.blokzincirbagisplatformu.model.enums.BlockchainIslemDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.BlockchainIslemTuru;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BlockchainIslemResponse(

        Long id,
        BlockchainIslemTuru islemTuru,
        BlockchainIslemDurumu durum,
        String transactionHash,
        Long chainId,
        BigDecimal tutar,
        String gonderenAdres,
        String aliciAdres,
        Long bagisId,
        Long harcamaId,
        Long blokNumarasi,
        LocalDateTime olusturmaTarihi,
        LocalDateTime onaylanmaTarihi

) {}