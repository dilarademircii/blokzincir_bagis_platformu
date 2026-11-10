package com.proje.blokzincirbagisplatformu.dto.request;

import com.proje.blokzincirbagisplatformu.model.enums.OnayKarari;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record HarcamaOnayRequest(

        @NotNull(message = "Harcama seçilmelidir.")
        Long harcamaId,

        @NotNull(message = "Onay kararı belirtilmelidir.")
        OnayKarari karar,

        @Size(max = 1000)
        String aciklama

) {}