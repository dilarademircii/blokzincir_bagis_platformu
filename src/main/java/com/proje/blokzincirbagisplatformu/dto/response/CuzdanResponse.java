package com.proje.blokzincirbagisplatformu.dto.response;

import com.proje.blokzincirbagisplatformu.model.enums.CuzdanDurumu;
import java.time.LocalDateTime;

public record CuzdanResponse(

        Long id,
        String cuzdanAdresi,
        CuzdanDurumu durum,
        Long kullaniciId,
        String agAdi,
        LocalDateTime olusturmaTarihi,
        LocalDateTime dogrulamaTarihi

) {}