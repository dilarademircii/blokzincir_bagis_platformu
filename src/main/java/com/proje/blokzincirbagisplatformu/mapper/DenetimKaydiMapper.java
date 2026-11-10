
package com.proje.blokzincirbagisplatformu.mapper;

import com.proje.blokzincirbagisplatformu.dto.request.DenetimKaydiRequest;
import com.proje.blokzincirbagisplatformu.dto.response.DenetimKaydiResponse;
import com.proje.blokzincirbagisplatformu.model.DenetimKaydi;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ProjectMapperConfig.class)
public interface DenetimKaydiMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "kullanici", ignore = true)
    @Mapping(target = "islemTarihi", ignore = true)
    DenetimKaydi requestToEntity(DenetimKaydiRequest request);

    @Mapping(target = "kullaniciId", source = "kullanici.id")
    @Mapping(
            target = "kullaniciAdSoyad",
            expression = "java(denetimKaydi.getKullanici() == null ? null : denetimKaydi.getKullanici().getAd() + \" \" + denetimKaydi.getKullanici().getSoyad())"
    )
    DenetimKaydiResponse entityToResponse(
            DenetimKaydi denetimKaydi
    );
}
