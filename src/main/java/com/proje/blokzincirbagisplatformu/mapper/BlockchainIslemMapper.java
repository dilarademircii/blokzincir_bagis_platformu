
package com.proje.blokzincirbagisplatformu.mapper;

import com.proje.blokzincirbagisplatformu.dto.request.BlockchainIslemRequest;
import com.proje.blokzincirbagisplatformu.dto.response.BlockchainIslemResponse;
import com.proje.blokzincirbagisplatformu.model.BlockchainIslem;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ProjectMapperConfig.class)
public interface BlockchainIslemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "durum", ignore = true)
    @Mapping(target = "transactionHash", ignore = true)
    @Mapping(target = "bagis", ignore = true)
    @Mapping(target = "harcama", ignore = true)
    @Mapping(target = "blokNumarasi", ignore = true)
    @Mapping(target = "olusturmaTarihi", ignore = true)
    @Mapping(target = "onaylanmaTarihi", ignore = true)
    BlockchainIslem requestToEntity(BlockchainIslemRequest request);

    @Mapping(target = "bagisId", source = "bagis.id")
    @Mapping(target = "harcamaId", source = "harcama.id")
    BlockchainIslemResponse entityToResponse(
            BlockchainIslem blockchainIslem
    );
}
