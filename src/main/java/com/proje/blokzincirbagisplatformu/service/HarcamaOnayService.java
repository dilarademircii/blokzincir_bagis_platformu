package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.dto.response.HarcamaOnayResponse;
import com.proje.blokzincirbagisplatformu.model.HarcamaOnay;
import com.proje.blokzincirbagisplatformu.model.enums.OnayKarari;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface HarcamaOnayService extends TemelService<HarcamaOnay> {

    /** Harcamaya ait tüm onaylar */
    List<HarcamaOnay> harcamaIdIleGetir(Long harcamaId);

    /** Onaylayan kullanıcının verdiği kararlar (sayfalı) */
    Page<HarcamaOnay> onaylayanIdIleGetir(Long kullaniciId, Pageable pageable);

    /** Belirli karar türüne göre listele */
    Page<HarcamaOnay> kararIleGetir(OnayKarari karar, Pageable pageable);

    /** Harcama ve karar türüne göre listele */
    List<HarcamaOnay> harcamaVeKararIleGetir(Long harcamaId, OnayKarari karar);

    /** Kullanıcı bu harcamayı daha önce değerlendirmiş mi? */
    boolean kullaniciHarcamayiDegerlendirmisMi(Long harcamaId, Long kullaniciId);

    /** Kullanıcı belirli kararı vermiş mi? */
    boolean kullaniciKarariVarMi(Long harcamaId, Long kullaniciId, OnayKarari karar);

    /** Harcama için belirli karar sayısı */
    long harcamaKararSayisi(Long harcamaId, OnayKarari karar);

    /** Onay tarihi aralığındaki kayıtlar */
    Page<HarcamaOnay> tarihAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis, Pageable pageable);

    /** Harcama onaylarını kullanıcı bilgisiyle getir */
    List<HarcamaOnay> harcamaOnayGecmisiniGetir(Long harcamaId);

    /** Kullanıcının verdiği onay geçmişi (Response DTO döner) */
    Page<HarcamaOnayResponse> kullaniciOnayGecmisiniGetir(Long kullaniciId, Pageable pageable);
}