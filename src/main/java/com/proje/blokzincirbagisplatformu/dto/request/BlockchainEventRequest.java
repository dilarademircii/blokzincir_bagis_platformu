package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BlockchainEventRequest(

        @NotNull(message = "Chain ID zorunludur.")
        Long chainId,

        @NotBlank(message = "Transaction hash zorunludur.")
        @Size(max = 255)
        String transactionHash,

        @NotNull(message = "Log index zorunludur.")
        Integer logIndex,

        @NotBlank(message = "Event adı zorunludur.")
        @Size(max = 100)
        String eventAdi,

        @NotNull(message = "Blok numarası zorunludur.")
        Long blokNumarasi,

        String eventVerisi,

        Long blockchainIslemId

) {}