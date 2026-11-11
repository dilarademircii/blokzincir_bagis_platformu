package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record WalletVerifyRequest(

        @NotBlank(message = "Cüzdan adresi boş olamaz.")
        @Pattern(
                regexp = "^0x[0-9a-fA-F]{40}$",
                message = "Geçerli bir Ethereum cüzdan adresi giriniz."
        )
        String walletAddress,

        @NotBlank(message = "İmza boş olamaz.")
        @Size(min = 132, max = 132, message = "İmza uzunluğu geçersiz.")
        String signature,

        @NotBlank(message = "Nonce mesajı boş olamaz.")
        @Size(max = 255, message = "Nonce mesajı çok uzun.")
        String message
) {
}
