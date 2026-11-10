package com.proje.blokzincirbagisplatformu.dto.response;

import java.time.LocalDateTime;

public record TedarikciResponse(

        Long id,
        String firmaAdi,
        String vergiNumarasi,
        String email,
        String telefon,
        String adres,
        Boolean aktif,
        LocalDateTime olusturmaTarihi

) {}