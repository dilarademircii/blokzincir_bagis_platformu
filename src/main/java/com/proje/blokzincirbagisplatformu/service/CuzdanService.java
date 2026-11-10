package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.model.Cuzdan;
import com.proje.blokzincirbagisplatformu.model.enums.CuzdanDurumu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CuzdanService extends TemelService<Cuzdan> {

    /** Cüzdan adresi zaten kayıtlı mı? */
    boolean cuzdanAdresiKayitliMi(String cuzdanAdresi);

    /** Cüzdan adresine göre bul (büyük/küçük harf duyarsız) */
    Optional<Cuzdan> adreseGoreBul(String cuzdanAdresi);

    /** Kullanıcının tüm cüzdanları */
    List<Cuzdan> kullaniciIdIleGetir(Long kullaniciId);

    /** Kullanıcının belirli durumdaki cüzdanları */
    List<Cuzdan> kullaniciVeDurumIleGetir(Long kullaniciId, CuzdanDurumu durum);

    /** Duruma göre sayfalı listele */
    Page<Cuzdan> durumIleGetir(CuzdanDurumu durum, Pageable pageable);

    /** Belirli ağdaki cüzdanlar */
    Page<Cuzdan> agAdiIleGetir(String agAdi, Pageable pageable);

    /** Kullanıcının belirli durumdaki cüzdanı var mı? */
    boolean kullaniciDurumluCuzdaniVarMi(Long kullaniciId, CuzdanDurumu durum);

    /** Doğrulanma tarihi olan cüzdanlar */
    Page<Cuzdan> dogrulanmisCuzdanlar(Pageable pageable);

    /** Kullanıcının cüzdan sayısı */
    long kullaniciCuzdanSayisi(Long kullaniciId);

    /** Ağdaki cüzdan sayısı */
    long agCuzdanSayisi(String agAdi);

    /** Adres ve ağ bilgisiyle cüzdan bul */
    Optional<Cuzdan> adresVeAgIleBul(String adres, String agAdi);

    /** Cüzdan durumunu güncelle */
    Cuzdan durumGuncelle(Long cuzdanId, CuzdanDurumu yeniDurum);
}
