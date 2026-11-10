package com.proje.blokzincirbagisplatformu.dto.request;

import com.proje.blokzincirbagisplatformu.model.enums.BildirimTuru;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BildirimRequest(

        @NotBlank(message = "Bildirim başlığı boş bırakılamaz.")
        @Size(max = 255)
        String baslik,

        @NotBlank(message = "Bildirim mesajı boş bırakılamaz.")
        String mesaj,

        @NotNull(message = "Bildirim türü seçilmelidir.")
        BildirimTuru bildirimTuru,

        @NotNull(message = "Kullanıcı seçilmelidir.")
        Long kullaniciId

) {}
