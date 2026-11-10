
package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record GirisRequest(

        @NotBlank(message = "E-posta boş bırakılamaz.")
        @Email(message = "Geçerli bir e-posta giriniz.")
        String email,

        @NotBlank(message = "Şifre boş bırakılamaz.")
        String sifre

) {
}
