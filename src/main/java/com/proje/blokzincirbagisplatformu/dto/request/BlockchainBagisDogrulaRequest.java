
package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record BlockchainBagisDogrulaRequest(

        @NotNull(message = "Bağış ID boş olamaz.")
        @Positive(message = "Bağış ID pozitif olmalıdır.")
        Long bagisId,

        @NotBlank(message = "Transaction hash boş olamaz.")
        @Pattern(
                regexp = "^0x[a-fA-F0-9]{64}$",
                message = "Geçersiz transaction hash."
        )
        String transactionHash

) {
}
