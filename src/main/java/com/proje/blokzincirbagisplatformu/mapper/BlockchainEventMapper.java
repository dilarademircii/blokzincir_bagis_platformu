
package com.proje.blokzincirbagisplatformu.mapper;

import com.proje.blokzincirbagisplatformu.dto.request.BlockchainEventRequest;
import com.proje.blokzincirbagisplatformu.dto.response.BlockchainEventResponse;
import com.proje.blokzincirbagisplatformu.model.BlockchainEvent;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ProjectMapperConfig.class)
public interface BlockchainEventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blockchainIslem", ignore = true)
    @Mapping(target = "islendi", ignore = true)
    @Mapping(target = "olusturmaTarihi", ignore = true)
    @Mapping(target = "islenmeTarihi", ignore = true)
    BlockchainEvent requestToEntity(BlockchainEventRequest request);

    @Mapping(
            target = "blockchainIslemId",
            source = "blockchainIslem.id"
    )
    BlockchainEventResponse entityToResponse(
            BlockchainEvent blockchainEvent
    );
}

