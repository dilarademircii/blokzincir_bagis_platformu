
package com.proje.blokzincirbagisplatformu.dto.response;

public record GirisResponse(

        String erisimTokeni,
        String tokenTuru,
        Long kullaniciId,
        String email,
        String adSoyad,
        String rol

) {
}
