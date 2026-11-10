package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.model.DenetimKaydi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DenetimKaydiService extends TemelService<DenetimKaydi> {

    /** Kullanıcının işlem geçmişi */
    Page<DenetimKaydi> kullaniciIdIleGetir(Long kullaniciId, Pageable pageable);

    /** İşlem türüne göre kayıtlar */
    Page<DenetimKaydi> islemTuruIleGetir(String islemTuru, Pageable pageable);

    /** Varlık türüne göre kayıtlar */
    Page<DenetimKaydi> varlikTuruIleGetir(String varlikTuru, Pageable pageable);

    /** Belirli varlığın işlem geçmişi */
    Page<DenetimKaydi> varlikTuruVeIdIleGetir(String varlikTuru, Long varlikId, Pageable pageable);

    /** Tarih aralığındaki denetim kayıtları */
    Page<DenetimKaydi> tarihAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis, Pageable pageable);

    /** Kullanıcının tarih aralığındaki işlemleri */
    Page<DenetimKaydi> kullaniciTarihAraligiIleGetir(Long kullaniciId, LocalDateTime baslangic, LocalDateTime bitis, Pageable pageable);

    /** IP adresine göre işlem geçmişi */
    Page<DenetimKaydi> ipAdresineGoreGetir(String ipAdresi, Pageable pageable);

    /** Denetim kaydını kullanıcı bilgisiyle getir */
    Optional<DenetimKaydi> detayliGetir(Long kayitId);

    /** Belirli kullanıcının belirli varlık üzerindeki işlemleri */
    List<DenetimKaydi> kullaniciVarlikGecmisiniGetir(Long kullaniciId, String varlikTuru, Long varlikId);

    /** Kullanıcıya bağlı olmayan sistem kayıtları */
    Page<DenetimKaydi> sistemKayitlari(Pageable pageable);

    /** Kullanıcının toplam işlem sayısı */
    long kullaniciIslemSayisi(Long kullaniciId);
}
