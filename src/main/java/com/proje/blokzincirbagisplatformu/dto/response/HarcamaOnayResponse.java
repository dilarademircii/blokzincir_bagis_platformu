package com.proje.blokzincirbagisplatformu.dto.response;

import com.proje.blokzincirbagisplatformu.model.enums.OnayKarari;

import java.time.LocalDateTime;

public record HarcamaOnayResponse(

        Long id,
        Long harcamaId,
        String harcamaBasligi,
        Long onaylayanId,
        String onaylayanAdSoyad,
        OnayKarari karar,
        String aciklama,
        LocalDateTime onayTarihi

) {}
