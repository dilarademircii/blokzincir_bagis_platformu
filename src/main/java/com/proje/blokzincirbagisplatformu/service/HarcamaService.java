package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.model.Harcama;
import com.proje.blokzincirbagisplatformu.model.enums.HarcamaDurumu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface HarcamaService extends TemelService<Harcama> {

    /** Kampanyanın tüm harcamaları (sayfalı) */
    Page<Harcama> kampanyaIdIleGetir(Long kampanyaId, Pageable pageable);

    /** Harcamayı oluşturan kullanıcıya göre */
    Page<Harcama> olusturanIdIleGetir(Long kullaniciId, Pageable pageable);

    /** Tedarikçiye ait harcamalar */
    Page<Harcama> tedarikciIdIleGetir(Long tedarikciId, Pageable pageable);

    /** Duruma göre harcamalar */
    Page<Harcama> durumaGoreGetir(HarcamaDurumu durum, Pageable pageable);

    /** Kampanya ve duruma göre filtrele */
    Page<Harcama> kampanyaVeDurumaGoreGetir(Long kampanyaId, HarcamaDurumu durum, Pageable pageable);

    /** Belirli tutarın üzerindeki harcamalar */
    Page<Harcama> tutarinUzerindekiler(BigDecimal tutar, Pageable pageable);

    /** Tarih aralığındaki harcamalar */
    Page<Harcama> tarihAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis, Pageable pageable);

    /** Gerçekleşme tarihi olan harcamalar */
    Page<Harcama> gerceklesmisDurumdakiler(Pageable pageable);

    /** Gerçekleşme tarihi olmayan harcamalar */
    Page<Harcama> gerceklesmemis(Pageable pageable);

    /** Kampanyanın belirli durum+para birimi için toplam harcama tutarı */
    BigDecimal kampanyaToplamHarcama(Long kampanyaId, HarcamaDurumu durum, String paraBirimi);

    /** Harcamayı kampanya, oluşturan ve tedarikçi bilgileriyle getir */
    Optional<Harcama> detayliGetir(Long harcamaId);

    /** Kampanyada belirli durumdaki harcama sayısı */
    long kampanyaDurumluHarcamaSayisi(Long kampanyaId, HarcamaDurumu durum);

    /** Başlık veya açıklamada genel arama */
    Page<Harcama> ara(String arama, Pageable pageable);

    /** Kanıt belgesi bulunan harcamalar */
    Page<Harcama> kanitBelgeliHarcamalar(Pageable pageable);

    /** Kanıt belgesi bulunmayan harcamalar */
    Page<Harcama> kanitBelgesizHarcamalar(Pageable pageable);
}
