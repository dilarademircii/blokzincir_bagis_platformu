package com.proje.blokzincirbagisplatformu.dto.response;

import java.time.LocalDateTime;

public record DenetimKaydiResponse(

        Long id,
        Long kullaniciId,
        String kullaniciAdSoyad,
        String islemTuru,
        String varlikTuru,
        Long varlikId,
        String ipAdresi,
        String eskiDeger,
        String yeniDeger,
        LocalDateTime islemTarihi

) {}