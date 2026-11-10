package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record KurumRequest(

        @NotBlank(message = "Kurum adı boş bırakılamaz.")
        @Size(max = 255)
        String kurumAdi,

        @Size(max = 50)
        String vergiNumarasi,

        String adres,

        @Size(max = 30)
        String telefon,

        @Email(message = "Geçerli bir e-posta adresi giriniz.")
        @Size(max = 255)
        String email,

        String aciklama

) {}