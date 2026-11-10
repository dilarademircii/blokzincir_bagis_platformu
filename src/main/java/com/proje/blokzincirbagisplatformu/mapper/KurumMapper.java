
package com.proje.blokzincirbagisplatformu.mapper;

import com.proje.blokzincirbagisplatformu.dto.request.KurumRequest;
import com.proje.blokzincirbagisplatformu.dto.request.KurumGuncelleRequest;
import com.proje.blokzincirbagisplatformu.dto.response.KurumResponse;
import com.proje.blokzincirbagisplatformu.model.Kurum;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = ProjectMapperConfig.class)
public interface KurumMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "kullanicilar", ignore = true)
    @Mapping(target = "kampanyalar", ignore = true)
    @Mapping(target = "olusturmaTarihi", ignore = true)
    @Mapping(target = "aktif", ignore = true)
    Kurum requestToEntity(KurumRequest request);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    org.mapstruct.NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "kullanicilar", ignore = true)
    @Mapping(target = "kampanyalar", ignore = true)
    @Mapping(target = "olusturmaTarihi", ignore = true)
    void guncelleRequestToEntity(
            KurumGuncelleRequest request,
            @MappingTarget Kurum kurum
    );

    @Mapping(target = "kullaniciSayisi", ignore = true)
    @Mapping(target = "kampanyaSayisi", ignore = true)
    KurumResponse entityToResponse(Kurum kurum);
}

