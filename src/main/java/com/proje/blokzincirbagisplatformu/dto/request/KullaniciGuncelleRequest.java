
package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record KullaniciGuncelleRequest(

        @Size(max = 50)
        String ad,

        @Size(max = 50)
        String soyad,

        @Email(message = "Geçerli bir e-posta adresi giriniz.")
        @Size(max = 255)
        String email,

        @Pattern(regexp = "^[0-9+()\\- ]{10,30}$",
                message = "Geçerli bir telefon numarası giriniz.")
        String telefon

) {
}
