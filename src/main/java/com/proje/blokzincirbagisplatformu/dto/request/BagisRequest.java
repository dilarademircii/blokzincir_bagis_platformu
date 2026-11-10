package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record BagisRequest(

        @NotNull(message = "Bağış tutarı zorunludur.")
        @DecimalMin(value = "0.01",
                message = "Bağış tutarı sıfırdan büyük olmalıdır.")
        BigDecimal tutar,

        @NotNull(message = "Kampanya seçilmelidir.")
        Long kampanyaId,

        @NotNull(message = "Cüzdan seçilmelidir.")
        Long cuzdanId,

        @Size(max = 10)
        String paraBirimi

) {}