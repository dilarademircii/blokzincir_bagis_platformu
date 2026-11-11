
package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.model.Cuzdan;
import com.proje.blokzincirbagisplatformu.model.enums.CuzdanDurumu;
import com.proje.blokzincirbagisplatformu.dto.request.CuzdanEkleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CuzdanService extends TemelService<Cuzdan> {

    Cuzdan cuzdanEkle(Long kullaniciId, CuzdanEkleRequest request);

    boolean cuzdanAdresiKayitliMi(String cuzdanAdresi);

    Optional<Cuzdan> adreseGoreBul(String cuzdanAdresi);

    List<Cuzdan> kullaniciIdIleGetir(Long kullaniciId);

    List<Cuzdan> kullaniciVeDurumIleGetir(
            Long kullaniciId,
            CuzdanDurumu durum
    );

    Page<Cuzdan> durumIleGetir(
            CuzdanDurumu durum,
            Pageable pageable
    );

    Page<Cuzdan> agAdiIleGetir(
            String agAdi,
            Pageable pageable
    );

    boolean kullaniciDurumluCuzdaniVarMi(
            Long kullaniciId,
            CuzdanDurumu durum
    );

    Page<Cuzdan> dogrulanmisCuzdanlar(Pageable pageable);

    long kullaniciCuzdanSayisi(Long kullaniciId);

    long agCuzdanSayisi(String agAdi);

    Optional<Cuzdan> adresVeAgIleBul(
            String adres,
            String agAdi
    );

    Cuzdan durumGuncelle(
            Long cuzdanId,
            CuzdanDurumu yeniDurum
    );
    // MetaMask doğrulaması için nonce oluşturur.
    String nonceOlustur(String cuzdanAdresi);

    // MetaMask imzasını doğrular ve başarılıysa JWT üretir.
    String imzaDogrulaVeTokenUret(
            String cuzdanAdresi,
            String imzalananMesaj,
            String imza
    );
}
