package com.proje.blokzincirbagisplatformu.dto.response;

import com.proje.blokzincirbagisplatformu.model.enums.BildirimDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.BildirimTuru;

import java.time.LocalDateTime;

public record BildirimResponse(

        Long id,
        String baslik,
        String mesaj,
        BildirimTuru bildirimTuru,
        BildirimDurumu durum,
        Long kullaniciId,
        LocalDateTime olusturmaTarihi,
        LocalDateTime okunmaTarihi

) {}