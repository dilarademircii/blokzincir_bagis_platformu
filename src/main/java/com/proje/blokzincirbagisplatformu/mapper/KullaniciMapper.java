
package com.proje.blokzincirbagisplatformu.mapper;

import com.proje.blokzincirbagisplatformu.dto.request.KullaniciKayitRequest;
import com.proje.blokzincirbagisplatformu.dto.request.KullaniciGuncelleRequest;
import com.proje.blokzincirbagisplatformu.dto.response.KullaniciResponse;
import com.proje.blokzincirbagisplatformu.model.Kullanici;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = ProjectMapperConfig.class)
public interface KullaniciMapper {

    // Kullanıcı kayıt DTO'sunu Entity'ye dönüştürür.
    // Şifre hash'i ve kurum ilişkisi Service tarafından ayarlanır.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sifreHash", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "hesapDurumu", ignore = true)
    @Mapping(target = "kurum", ignore = true)
    @Mapping(target = "olusturmaTarihi", ignore = true)
    @Mapping(target = "guncellemeTarihi", ignore = true)
    Kullanici kayitRequestToEntity(KullaniciKayitRequest request);

    // Kullanıcı güncelleme işlemi.
    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    org.mapstruct.NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sifreHash", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "hesapDurumu", ignore = true)
    @Mapping(target = "kurum", ignore = true)
    @Mapping(target = "olusturmaTarihi", ignore = true)
    @Mapping(target = "guncellemeTarihi", ignore = true)
    void guncelleRequestToEntity(
            KullaniciGuncelleRequest request,
            @MappingTarget Kullanici kullanici
    );

    // Entity'den kullanıcı response DTO'su oluşturur.
    @Mapping(target = "kurumId", source = "kurum.id")
    @Mapping(target = "kurumAdi", source = "kurum.kurumAdi")
    KullaniciResponse entityToResponse(Kullanici kullanici);
}
