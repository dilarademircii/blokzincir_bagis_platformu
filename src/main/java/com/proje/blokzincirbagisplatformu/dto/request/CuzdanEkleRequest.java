package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CuzdanEkleRequest(

        @NotBlank(message = "Cüzdan adresi boş bırakılamaz.")
        @Size(max = 255)
        String cuzdanAdresi,

        @NotBlank(message = "Ağ adı boş bırakılamaz.")
        @Size(max = 50)
        String agAdi

) {}