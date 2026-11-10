
package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.model.Kurum;
import com.proje.blokzincirbagisplatformu.repository.KurumRepository;
import com.proje.blokzincirbagisplatformu.service.KurumService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class KurumServiceImpl extends TemelServiceImpl<Kurum>
        implements KurumService {

    private final KurumRepository kurumRepository;

    public KurumServiceImpl(KurumRepository kurumRepository) {
        super(kurumRepository);
        this.kurumRepository = kurumRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Kurum> vergiNumarasinaGoreGetir(String vergiNumarasi) {
        return kurumRepository.findByVergiNumarasi(vergiNumarasi);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Kurum> emailIleGetir(String email) {
        return kurumRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kurum> adaGoreAra(String kurumAdi, Pageable pageable) {
        return kurumRepository.findByKurumAdiContainingIgnoreCase(
                kurumAdi, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kurum> aktiflikDurumunaGoreGetir(
            Boolean aktif, Pageable pageable) {
        return kurumRepository.findByAktif(aktif, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public long kampanyaSayisiniGetir(Long kurumId) {
        kurumGetir(kurumId);
        return kurumRepository.kampanyaSayisiniGetir(kurumId);
    }

    @Override
    @Transactional(readOnly = true)
    public long kullaniciSayisiniGetir(Long kurumId) {
        kurumGetir(kurumId);
        return kurumRepository.kullaniciSayisiniGetir(kurumId);
    }

    @Override
    public Kurum kurumDurumunuGuncelle(Long kurumId, Boolean aktif) {
        Kurum kurum = kurumGetir(kurumId);
        kurum.setAktif(aktif);
        return kurumRepository.save(kurum);
    }

    private Kurum kurumGetir(Long kurumId) {
        return kurumRepository.findById(kurumId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Kurum bulunamadı. Kurum ID: " + kurumId));
    }
}
