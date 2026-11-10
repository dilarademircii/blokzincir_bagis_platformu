
package com.proje.blokzincirbagisplatformu.mapper;

import com.proje.blokzincirbagisplatformu.dto.request.KanitBelgesiRequest;
import com.proje.blokzincirbagisplatformu.dto.response.KanitBelgesiResponse;
import com.proje.blokzincirbagisplatformu.model.KanitBelgesi;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ProjectMapperConfig.class)
public interface KanitBelgesiMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dosyaAdi", ignore = true)
    @Mapping(target = "dosyaYolu", ignore = true)
    @Mapping(target = "dosyaHash", ignore = true)
    @Mapping(target = "harcama", ignore = true)
    @Mapping(target = "yukleyen", ignore = true)
    @Mapping(target = "yuklenmeTarihi", ignore = true)
    KanitBelgesi requestToEntity(KanitBelgesiRequest request);

    @Mapping(target = "harcamaId", source = "harcama.id")
    @Mapping(target = "harcamaBasligi", source = "harcama.baslik")
    @Mapping(target = "yukleyenId", source = "yukleyen.id")
    @Mapping(
            target = "yukleyenAdSoyad",
            expression = "java(kanitBelgesi.getYukleyen() == null ? null : kanitBelgesi.getYukleyen().getAd() + \" \" + kanitBelgesi.getYukleyen().getSoyad())"
    )
    KanitBelgesiResponse entityToResponse(KanitBelgesi kanitBelgesi);
}
