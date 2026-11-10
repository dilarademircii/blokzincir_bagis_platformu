
package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record KullaniciKayitRequest(

        @NotBlank(message = "Ad alanı boş bırakılamaz.")
        @Size(max = 50, message = "Ad en fazla 50 karakter olabilir.")
        String ad,

        @NotBlank(message = "Soyad alanı boş bırakılamaz.")
        @Size(max = 50, message = "Soyad en fazla 50 karakter olabilir.")
        String soyad,

        @NotBlank(message = "E-posta alanı boş bırakılamaz.")
        @Email(message = "Geçerli bir e-posta adresi giriniz.")
        @Size(max = 255)
        String email,

        @NotBlank(message = "Şifre boş bırakılamaz.")
        @Size(min = 8, max = 100,
                message = "Şifre 8 ile 100 karakter arasında olmalıdır.")
        String sifre,

        @Pattern(regexp = "^[0-9+()\\- ]{10,30}$",
                message = "Geçerli bir telefon numarası giriniz.")
        String telefon,

        Long kurumId

) {
}
