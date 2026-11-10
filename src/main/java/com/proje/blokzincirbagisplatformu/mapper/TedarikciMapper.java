
package com.proje.blokzincirbagisplatformu.mapper;

import com.proje.blokzincirbagisplatformu.dto.request.TedarikciRequest;
import com.proje.blokzincirbagisplatformu.dto.request.TedarikciGuncelleRequest;
import com.proje.blokzincirbagisplatformu.dto.response.TedarikciResponse;
import com.proje.blokzincirbagisplatformu.model.Tedarikci;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = ProjectMapperConfig.class)
public interface TedarikciMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "harcamalar", ignore = true)
    @Mapping(target = "aktif", ignore = true)
    @Mapping(target = "olusturmaTarihi", ignore = true)
    Tedarikci requestToEntity(TedarikciRequest request);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    org.mapstruct.NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "harcamalar", ignore = true)
    @Mapping(target = "olusturmaTarihi", ignore = true)
    void guncelleRequestToEntity(
            TedarikciGuncelleRequest request,
            @MappingTarget Tedarikci tedarikci
    );

    TedarikciResponse entityToResponse(Tedarikci tedarikci);
}
