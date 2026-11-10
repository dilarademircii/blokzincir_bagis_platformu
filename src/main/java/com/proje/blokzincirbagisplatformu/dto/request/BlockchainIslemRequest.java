package com.proje.blokzincirbagisplatformu.dto.request;

import com.proje.blokzincirbagisplatformu.model.enums.BlockchainIslemTuru;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record BlockchainIslemRequest(

        @NotNull(message = "İşlem türü belirtilmelidir.")
        BlockchainIslemTuru islemTuru,

        @NotNull(message = "Chain ID zorunludur.")
        Long chainId,

        @NotNull(message = "İşlem tutarı zorunludur.")
        @DecimalMin(value = "0.01")
        BigDecimal tutar,

        @NotBlank(message = "Gönderen cüzdan adresi zorunludur.")
        @Size(max = 255)
        String gonderenAdres,

        @NotBlank(message = "Alıcı cüzdan adresi zorunludur.")
        @Size(max = 255)
        String aliciAdres,

        Long bagisId,

        Long harcamaId

) {}