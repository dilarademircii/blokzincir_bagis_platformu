package com.proje.blokzincirbagisplatformu.dto.request;

import com.proje.blokzincirbagisplatformu.model.enums.BelgeTuru;

import jakarta.validation.constraints.NotNull;

public record KanitBelgesiRequest(

        @NotNull(message = "Harcama seçilmelidir.")
        Long harcamaId,

        @NotNull(message = "Belge türü seçilmelidir.")
        BelgeTuru belgeTuru

) {}