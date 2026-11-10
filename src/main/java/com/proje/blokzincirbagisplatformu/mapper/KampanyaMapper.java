
package com.proje.blokzincirbagisplatformu.mapper;

import com.proje.blokzincirbagisplatformu.dto.request.KampanyaRequest;
import com.proje.blokzincirbagisplatformu.dto.request.KampanyaGuncelleRequest;
import com.proje.blokzincirbagisplatformu.dto.response.KampanyaResponse;
import com.proje.blokzincirbagisplatformu.model.Kampanya;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = ProjectMapperConfig.class)
public interface KampanyaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "kurum", ignore = true)
    @Mapping(target = "bagislar", ignore = true)
    @Mapping(target = "harcamalar", ignore = true)
    @Mapping(target = "durum", ignore = true)
    @Mapping(target = "olusturmaTarihi", ignore = true)
    Kampanya requestToEntity(KampanyaRequest request);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    org.mapstruct.NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "kurum", ignore = true)
    @Mapping(target = "bagislar", ignore = true)
    @Mapping(target = "harcamalar", ignore = true)
    @Mapping(target = "durum", ignore = true)
    @Mapping(target = "olusturmaTarihi", ignore = true)
    void guncelleRequestToEntity(
            KampanyaGuncelleRequest request,
            @MappingTarget Kampanya kampanya
    );

    @Mapping(target = "kurumId", source = "kurum.id")
    @Mapping(target = "kurumAdi", source = "kurum.kurumAdi")
    @Mapping(target = "toplamBagisTutari", ignore = true)
    KampanyaResponse entityToResponse(Kampanya kampanya);
}
