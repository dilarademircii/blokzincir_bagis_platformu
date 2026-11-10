package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record TedarikciGuncelleRequest(

        @Size(max = 255)
        String firmaAdi,

        @Size(max = 50)
        String vergiNumarasi,

        @Email(message = "Geçerli bir e-posta adresi giriniz.")
        @Size(max = 255)
        String email,

        @Size(max = 30)
        String telefon,

        String adres,

        Boolean aktif

) {}