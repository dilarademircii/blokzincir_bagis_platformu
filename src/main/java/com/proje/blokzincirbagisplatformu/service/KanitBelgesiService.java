package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.model.KanitBelgesi;
import com.proje.blokzincirbagisplatformu.model.enums.BelgeTuru;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface KanitBelgesiService extends TemelService<KanitBelgesi> {

    /** Dosya hash'i daha önce yüklenmiş mi? */
    boolean dosyaHashKayitliMi(String dosyaHash);

    /** Hash üzerinden belgeleri bul */
    List<KanitBelgesi> dosyaHashIleGetir(String dosyaHash);

    /** Harcamaya ait kanıt belgeleri */
    List<KanitBelgesi> harcamaIdIleGetir(Long harcamaId);

    /** Yükleyen kullanıcıya göre listele */
    Page<KanitBelgesi> yukleyenIdIleGetir(Long kullaniciId, Pageable pageable);

    /** Belge türüne göre listele */
    Page<KanitBelgesi> belgeTuruIleGetir(BelgeTuru belgeTuru, Pageable pageable);

    /** Harcama ve belge türüne göre listele */
    List<KanitBelgesi> harcamaVeBelgeTuruIleGetir(Long harcamaId, BelgeTuru belgeTuru);

    /** Yüklenme tarihi aralığındaki belgeler */
    Page<KanitBelgesi> tarihAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis, Pageable pageable);

    /** Harcamanın kanıt belgesi olup olmadığı */
    boolean harcamaBelgesiVarMi(Long harcamaId);

    /** Belgeyi harcama ve yükleyen bilgisiyle getir */
    Optional<KanitBelgesi> detayliGetir(Long belgeId);

    /** Belirli kullanıcının belirli harcamaya belge yükleyip yüklemediği */
    boolean kullaniciHarcamaBelgesiVarMi(Long harcamaId, Long kullaniciId);

    /** Bir harcamaya yüklenen belge sayısı */
    long harcamaBelgeSayisi(Long harcamaId);
}
