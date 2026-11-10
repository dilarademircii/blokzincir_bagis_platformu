package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.model.Bagis;
import com.proje.blokzincirbagisplatformu.model.enums.BagisDurumu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BagisService extends TemelService<Bagis> {

    /** Belirli bağışçının tüm bağışları (sayfalı) */
    Page<Bagis> bagisciIdIleGetir(Long bagisciId, Pageable pageable);

    /** Belirli kampanyanın tüm bağışları (sayfalı) */
    Page<Bagis> kampanyaIdIleGetir(Long kampanyaId, Pageable pageable);

    /** Duruma göre bağışları listele */
    Page<Bagis> durumaGoreGetir(BagisDurumu durum, Pageable pageable);

    /** Bağışçı ve duruma göre filtrele */
    Page<Bagis> bagisciVeDurumaGoreGetir(Long bagisciId, BagisDurumu durum, Pageable pageable);

    /** Kampanya ve duruma göre filtrele */
    List<Bagis> kampanyaVeDurumaGoreGetir(Long kampanyaId, BagisDurumu durum);

    /** Belirli cüzdanın bağışları */
    Page<Bagis> cuzdanIdIleGetir(Long cuzdanId, Pageable pageable);

    /** Tarih aralığındaki bağışlar */
    Page<Bagis> tarihAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis, Pageable pageable);

    /** Bağışçının belirli durumdaki toplam bağış tutarı */
    BigDecimal bagisciToplamTutarGetir(Long bagisciId, BagisDurumu durum);

    /** Kampanyanın belirli durumdaki toplam bağış tutarı */
    BigDecimal kampanyaToplamTutarGetir(Long kampanyaId, BagisDurumu durum);

    /** Bağışın blockchain işlemi var mı? */
    boolean blockchainIslemiVarMi(Long bagisId);

    /** Bağışçı kampanyaya daha önce bağış yapmış mı? */
    boolean bagisVarMi(Long bagisciId, Long kampanyaId);

    /** Kampanyadaki bağış sayısı */
    long kampanyaBagisSayisi(Long kampanyaId);

    /** Bağışçının toplam bağış sayısı */
    long bagisciBagisSayisi(Long bagisciId);
}
