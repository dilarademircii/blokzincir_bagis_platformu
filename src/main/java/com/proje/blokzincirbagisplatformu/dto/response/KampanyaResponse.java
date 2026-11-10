package com.proje.blokzincirbagisplatformu.dto.response;

import com.proje.blokzincirbagisplatformu.model.enums.KampanyaDurumu;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record KampanyaResponse(

        Long id,
        String baslik,
        String aciklama,
        BigDecimal hedefTutar,
        String paraBirimi,
        KampanyaDurumu durum,
        Long kurumId,
        String kurumAdi,
        BigDecimal toplamBagisTutari,
        LocalDateTime baslangicTarihi,
        LocalDateTime bitisTarihi,
        LocalDateTime olusturmaTarihi

) {}