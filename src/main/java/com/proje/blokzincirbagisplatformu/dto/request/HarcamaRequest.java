package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record HarcamaRequest(

        @NotBlank(message = "Harcama başlığı boş bırakılamaz.")
        @Size(max = 255)
        String baslik,

        String aciklama,

        @NotNull(message = "Harcama tutarı zorunludur.")
        @DecimalMin(value = "0.01",
                message = "Harcama tutarı sıfırdan büyük olmalıdır.")
        BigDecimal tutar,

        @NotBlank(message = "Para birimi boş bırakılamaz.")
        @Size(max = 10)
        String paraBirimi,

        @NotNull(message = "Kampanya seçilmelidir.")
        Long kampanyaId,

        @NotNull(message = "Tedarikçi seçilmelidir.")
        Long tedarikciId

) {}