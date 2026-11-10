
package com.proje.blokzincirbagisplatformu.mapper;

import com.proje.blokzincirbagisplatformu.dto.request.HarcamaOnayRequest;
import com.proje.blokzincirbagisplatformu.dto.response.HarcamaOnayResponse;
import com.proje.blokzincirbagisplatformu.model.HarcamaOnay;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ProjectMapperConfig.class)
public interface HarcamaOnayMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "harcama", ignore = true)
    @Mapping(target = "onaylayan", ignore = true)
    @Mapping(target = "onayTarihi", ignore = true)
    HarcamaOnay requestToEntity(HarcamaOnayRequest request);

    @Mapping(target = "harcamaId", source = "harcama.id")
    @Mapping(target = "harcamaBasligi", source = "harcama.baslik")
    @Mapping(target = "onaylayanId", source = "onaylayan.id")
    @Mapping(
            target = "onaylayanAdSoyad",
            expression = "java(harcamaOnay.getOnaylayan() == null ? null : harcamaOnay.getOnaylayan().getAd() + \" \" + harcamaOnay.getOnaylayan().getSoyad())"
    )
    HarcamaOnayResponse entityToResponse(HarcamaOnay harcamaOnay);
}
