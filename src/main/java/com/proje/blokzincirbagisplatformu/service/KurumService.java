
package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.model.Kurum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface KurumService extends TemelService<Kurum> {

    Optional<Kurum> vergiNumarasinaGoreGetir(String vergiNumarasi);

    Optional<Kurum> emailIleGetir(String email);

    Page<Kurum> adaGoreAra(String kurumAdi, Pageable pageable);

    Page<Kurum> aktiflikDurumunaGoreGetir(Boolean aktif, Pageable pageable);

    long kampanyaSayisiniGetir(Long kurumId);

    long kullaniciSayisiniGetir(Long kurumId);

    Kurum kurumDurumunuGuncelle(Long kurumId, Boolean aktif);
}
