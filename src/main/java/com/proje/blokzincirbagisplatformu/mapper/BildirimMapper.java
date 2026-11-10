
package com.proje.blokzincirbagisplatformu.mapper;

import com.proje.blokzincirbagisplatformu.dto.request.BildirimRequest;
import com.proje.blokzincirbagisplatformu.dto.response.BildirimResponse;
import com.proje.blokzincirbagisplatformu.model.Bildirim;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ProjectMapperConfig.class)
public interface BildirimMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "kullanici", ignore = true)
    @Mapping(target = "durum", ignore = true)
    @Mapping(target = "olusturmaTarihi", ignore = true)
    @Mapping(target = "okunmaTarihi", ignore = true)
    Bildirim requestToEntity(BildirimRequest request);

    @Mapping(target = "kullaniciId", source = "kullanici.id")
    BildirimResponse entityToResponse(Bildirim bildirim);
}
