package com.proje.blokzincirbagisplatformu.dto.response;

import com.proje.blokzincirbagisplatformu.model.enums.BagisDurumu;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BagisResponse(

        Long id,
        BigDecimal tutar,
        String paraBirimi,
        BagisDurumu durum,
        Long kampanyaId,
        String kampanyaBasligi,
        Long bagisciId,
        String bagisciAdSoyad,
        Long cuzdanId,
        String cuzdanAdresi,
        String transactionHash,
        LocalDateTime olusturmaTarihi

) {}