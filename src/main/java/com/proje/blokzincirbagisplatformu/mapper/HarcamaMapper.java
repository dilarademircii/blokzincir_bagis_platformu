
package com.proje.blokzincirbagisplatformu.mapper;

import com.proje.blokzincirbagisplatformu.dto.request.HarcamaRequest;
import com.proje.blokzincirbagisplatformu.dto.response.HarcamaResponse;
import com.proje.blokzincirbagisplatformu.model.Harcama;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ProjectMapperConfig.class)
public interface HarcamaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "kampanya", ignore = true)
    @Mapping(target = "tedarikci", ignore = true)
    @Mapping(target = "olusturan", ignore = true)
    @Mapping(target = "kanitBelgeleri", ignore = true)
    @Mapping(target = "onaylar", ignore = true)
    @Mapping(target = "blockchainIslemleri", ignore = true)
    @Mapping(target = "durum", ignore = true)
    @Mapping(target = "olusturmaTarihi", ignore = true)
    @Mapping(target = "gerceklesmeTarihi", ignore = true)
    Harcama requestToEntity(HarcamaRequest request);

    @Mapping(target = "kampanyaId", source = "kampanya.id")
    @Mapping(target = "kampanyaBasligi", source = "kampanya.baslik")
    @Mapping(target = "tedarikciId", source = "tedarikci.id")
    @Mapping(target = "tedarikciAdi", source = "tedarikci.firmaAdi")
    @Mapping(target = "olusturanId", source = "olusturan.id")
    @Mapping(
            target = "olusturanAdSoyad",
            expression = "java(harcama.getOlusturan() == null ? null : harcama.getOlusturan().getAd() + \" \" + harcama.getOlusturan().getSoyad())"
    )
    HarcamaResponse entityToResponse(Harcama harcama);
}

