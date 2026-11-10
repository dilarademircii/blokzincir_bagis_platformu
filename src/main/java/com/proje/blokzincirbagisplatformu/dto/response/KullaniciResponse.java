
package com.proje.blokzincirbagisplatformu.dto.response;

import com.proje.blokzincirbagisplatformu.model.enums.HesapDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.KullaniciRol;

import java.time.LocalDateTime;

public record KullaniciResponse(

        Long id,
        String ad,
        String soyad,
        String email,
        String telefon,
        KullaniciRol rol,
        HesapDurumu hesapDurumu,
        Long kurumId,
        String kurumAdi,
        LocalDateTime olusturmaTarihi,
        LocalDateTime guncellemeTarihi

) {
}

