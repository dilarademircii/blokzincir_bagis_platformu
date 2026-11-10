
package com.proje.blokzincirbagisplatformu.mapper;

import com.proje.blokzincirbagisplatformu.dto.request.BagisRequest;
import com.proje.blokzincirbagisplatformu.dto.response.BagisResponse;
import com.proje.blokzincirbagisplatformu.model.Bagis;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ProjectMapperConfig.class)
public interface BagisMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "kampanya", ignore = true)
    @Mapping(target = "bagisci", ignore = true)
    @Mapping(target = "cuzdan", ignore = true)
    @Mapping(target = "blockchainIslemleri", ignore = true)
    @Mapping(target = "durum", ignore = true)
    @Mapping(target = "olusturmaTarihi", ignore = true)
    Bagis requestToEntity(BagisRequest request);

    @Mapping(target = "kampanyaId", source = "kampanya.id")
    @Mapping(target = "kampanyaBasligi", source = "kampanya.baslik")
    @Mapping(target = "bagisciId", source = "bagisci.id")
    @Mapping(
            target = "bagisciAdSoyad",
            expression = "java(bagis.getBagisci() == null ? null : bagis.getBagisci().getAd() + \" \" + bagis.getBagisci().getSoyad())"
    )
    @Mapping(target = "cuzdanId", source = "cuzdan.id")
    @Mapping(target = "cuzdanAdresi", source = "cuzdan.cuzdanAdresi")
    @Mapping(target = "transactionHash", ignore = true)
    BagisResponse entityToResponse(Bagis bagis);
}
