package com.proje.blokzincirbagisplatformu.dto.response;

import com.proje.blokzincirbagisplatformu.model.enums.HarcamaDurumu;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HarcamaResponse(

        Long id,
        String baslik,
        String aciklama,
        BigDecimal tutar,
        String paraBirimi,
        HarcamaDurumu durum,
        Long kampanyaId,
        String kampanyaBasligi,
        Long tedarikciId,
        String tedarikciAdi,
        Long olusturanId,
        String olusturanAdSoyad,
        LocalDateTime olusturmaTarihi,
        LocalDateTime gerceklesmeTarihi

) {}