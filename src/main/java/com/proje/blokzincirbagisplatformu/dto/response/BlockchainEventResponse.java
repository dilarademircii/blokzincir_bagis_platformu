package com.proje.blokzincirbagisplatformu.dto.response;

import java.time.LocalDateTime;

public record BlockchainEventResponse(

        Long id,
        Long chainId,
        String transactionHash,
        Integer logIndex,
        String eventAdi,
        Long blokNumarasi,
        String eventVerisi,
        Boolean islendi,
        Long blockchainIslemId,
        LocalDateTime olusturmaTarihi,
        LocalDateTime islenmeTarihi

) {}