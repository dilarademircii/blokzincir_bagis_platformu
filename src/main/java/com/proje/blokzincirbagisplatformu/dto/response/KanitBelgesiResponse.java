package com.proje.blokzincirbagisplatformu.dto.response;

import com.proje.blokzincirbagisplatformu.model.enums.BelgeTuru;

import java.time.LocalDateTime;

public record KanitBelgesiResponse(

        Long id,
        String dosyaAdi,
        String dosyaHash,
        BelgeTuru belgeTuru,
        Long harcamaId,
        String harcamaBasligi,
        Long yukleyenId,
        String yukleyenAdSoyad,
        LocalDateTime yuklenmeTarihi

) {}