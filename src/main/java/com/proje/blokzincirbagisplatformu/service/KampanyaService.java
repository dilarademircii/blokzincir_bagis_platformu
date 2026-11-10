package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.model.Kampanya;
import com.proje.blokzincirbagisplatformu.model.enums.KampanyaDurumu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface KampanyaService extends TemelService<Kampanya> {

    /** Kurumun tüm kampanyaları (sayfalı) */
    Page<Kampanya> kurumIdIleGetir(Long kurumId, Pageable pageable);

    /** Duruma göre kampanyalar */
    Page<Kampanya> durumaGoreGetir(KampanyaDurumu durum, Pageable pageable);

    /** Kurum ve duruma göre kampanyalar */
    Page<Kampanya> kurumVeDurumaGoreGetir(Long kurumId, KampanyaDurumu durum, Pageable pageable);

    /** Başlığa göre arama */
    Page<Kampanya> basligaGoreAra(String baslik, Pageable pageable);

    /** Belirli hedef tutarın üzerindeki kampanyalar */
    Page<Kampanya> hedefTutarinUzerindekiler(BigDecimal hedefTutar, Pageable pageable);

    /** Başlangıç tarihi belirli aralıkta olanlar */
    List<Kampanya> baslangicTarihiAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis);

    /** Bitiş tarihi geçmiş kampanyalar */
    List<Kampanya> bitisTarihiGecmisler(LocalDateTime tarih);

    /** Tarihi dolmamış ve aktif durumdaki kampanyalar */
    Page<Kampanya> devamEdenKampanyalariGetir(KampanyaDurumu durum, LocalDateTime simdi, Pageable pageable);

    /** Kampanyayı kurumu ile birlikte getir */
    Optional<Kampanya> kurumuylaBirlikteBul(Long kampanyaId);

    /** Başlık veya açıklamada genel arama */
    Page<Kampanya> ara(String arama, Pageable pageable);

    /** Kurumun toplam kampanya sayısı */
    long kurumKampanyaSayisi(Long kurumId);

    /** Kurumun belirli durumdaki kampanya sayısı */
    long kurumDurumluKampanyaSayisi(Long kurumId, KampanyaDurumu durum);
}
