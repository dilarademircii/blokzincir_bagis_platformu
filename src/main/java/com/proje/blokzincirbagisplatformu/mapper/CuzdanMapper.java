
package com.proje.blokzincirbagisplatformu.mapper;

import com.proje.blokzincirbagisplatformu.dto.request.CuzdanEkleRequest;
import com.proje.blokzincirbagisplatformu.dto.response.CuzdanResponse;
import com.proje.blokzincirbagisplatformu.model.Cuzdan;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ProjectMapperConfig.class)
public interface CuzdanMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "kullanici", ignore = true)
    @Mapping(target = "durum", ignore = true)
    @Mapping(target = "olusturmaTarihi", ignore = true)
    @Mapping(target = "dogrulamaTarihi", ignore = true)
    Cuzdan requestToEntity(CuzdanEkleRequest request);

    @Mapping(target = "kullaniciId", source = "kullanici.id")
    CuzdanResponse entityToResponse(Cuzdan cuzdan);
}
