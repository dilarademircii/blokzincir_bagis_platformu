package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DenetimKaydiRequest(

        @NotNull(message = "Kullanıcı ID zorunludur.")
        Long kullaniciId,

        @NotBlank(message = "İşlem türü belirtilmelidir.")
        @Size(max = 100)
        String islemTuru,

        @NotBlank(message = "Varlık türü belirtilmelidir.")
        @Size(max = 100)
        String varlikTuru,

        Long varlikId,

        @Size(max = 50)
        String ipAdresi,

        String eskiDeger,

        String yeniDeger

) {}
