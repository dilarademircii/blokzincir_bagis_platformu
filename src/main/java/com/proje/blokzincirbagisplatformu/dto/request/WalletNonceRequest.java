
package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record WalletNonceRequest(

        @NotBlank(message = "Cüzdan adresi boş olamaz.")
        @Pattern(
                regexp = "^0x[0-9a-fA-F]{40}$",
                message = "Geçerli bir Ethereum cüzdan adresi giriniz."
        )
        String walletAddress

) {
}
