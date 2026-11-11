
package com.proje.blokzincirbagisplatformu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CuzdanEkleRequest(

        @NotBlank(message = "Cüzdan adresi boş bırakılamaz.")
        @Size(max = 42, message = "Cüzdan adresi en fazla 42 karakter olabilir.")
        String cuzdanAdresi,

        @NotBlank(message = "Ağ adı boş bırakılamaz.")
        @Size(max = 100, message = "Ağ adı en fazla 100 karakter olabilir.")
        String agAdi

) {
}
