package com.proje.blokzincirbagisplatformu.dto.response;

import java.time.LocalDateTime;

public record KurumResponse(

        Long id,
        String kurumAdi,
        String vergiNumarasi,
        String adres,
        String telefon,
        String email,
        String aciklama,
        Boolean aktif,
        Long kullaniciSayisi,
        Long kampanyaSayisi,
        LocalDateTime olusturmaTarihi

) {}