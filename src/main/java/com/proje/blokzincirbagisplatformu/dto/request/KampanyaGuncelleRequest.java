package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record KampanyaGuncelleRequest(

        @Size(max = 255)
        String baslik,

        String aciklama,

        @DecimalMin(value = "0.01",
                message = "Hedef tutar sıfırdan büyük olmalıdır.")
        BigDecimal hedefTutar,

        @Size(max = 10)
        String paraBirimi,

        LocalDateTime baslangicTarihi,

        LocalDateTime bitisTarihi

) {}
