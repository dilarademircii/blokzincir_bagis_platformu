package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record KampanyaRequest(

        @NotBlank(message = "Kampanya başlığı boş bırakılamaz.")
        @Size(max = 255)
        String baslik,

        String aciklama,

        @NotNull(message = "Hedef tutar zorunludur.")
        @DecimalMin(value = "0.01", message = "Hedef tutar sıfırdan büyük olmalıdır.")
        BigDecimal hedefTutar,

        @NotBlank(message = "Para birimi boş bırakılamaz.")
        @Size(max = 10)
        String paraBirimi,

        @NotNull(message = "Kurum ID zorunludur.")
        Long kurumId,

        @NotNull(message = "Başlangıç tarihi zorunludur.")
        LocalDateTime baslangicTarihi,

        @NotNull(message = "Bitiş tarihi zorunludur.")
        LocalDateTime bitisTarihi

) {}