package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.model.KanitBelgesi;
import com.proje.blokzincirbagisplatformu.model.enums.BelgeTuru;
import com.proje.blokzincirbagisplatformu.repository.KanitBelgesiRepository;
import com.proje.blokzincirbagisplatformu.service.KanitBelgesiService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KanitBelgesiServiceImpl extends TemelServiceImpl<KanitBelgesi>
        implements KanitBelgesiService {

    private final KanitBelgesiRepository kanitBelgesiRepository;

    public KanitBelgesiServiceImpl(KanitBelgesiRepository kanitBelgesiRepository) {
        super(kanitBelgesiRepository);
        this.kanitBelgesiRepository = kanitBelgesiRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean dosyaHashKayitliMi(String dosyaHash) {
        return kanitBelgesiRepository.existsByDosyaHash(dosyaHash);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KanitBelgesi> dosyaHashIleGetir(String dosyaHash) {
        return kanitBelgesiRepository.findByDosyaHash(dosyaHash);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KanitBelgesi> harcamaIdIleGetir(Long harcamaId) {
        return kanitBelgesiRepository.findByHarcama_Id(harcamaId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KanitBelgesi> yukleyenIdIleGetir(Long kullaniciId, Pageable pageable) {
        return kanitBelgesiRepository.findByYukleyen_Id(kullaniciId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KanitBelgesi> belgeTuruIleGetir(BelgeTuru belgeTuru, Pageable pageable) {
        return kanitBelgesiRepository.findByBelgeTuru(belgeTuru, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KanitBelgesi> harcamaVeBelgeTuruIleGetir(Long harcamaId, BelgeTuru belgeTuru) {
        return kanitBelgesiRepository.findByHarcama_IdAndBelgeTuru(harcamaId, belgeTuru);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KanitBelgesi> tarihAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis, Pageable pageable) {
        return kanitBelgesiRepository.findByYuklenmeTarihiBetween(baslangic, bitis, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean harcamaBelgesiVarMi(Long harcamaId) {
        return kanitBelgesiRepository.existsByHarcama_Id(harcamaId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KanitBelgesi> detayliGetir(Long belgeId) {
        return kanitBelgesiRepository.belgeDetayiniGetir(belgeId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean kullaniciHarcamaBelgesiVarMi(Long harcamaId, Long kullaniciId) {
        return kanitBelgesiRepository.existsByHarcama_IdAndYukleyen_Id(harcamaId, kullaniciId);
    }

    @Override
    @Transactional(readOnly = true)
    public long harcamaBelgeSayisi(Long harcamaId) {
        return kanitBelgesiRepository.countByHarcama_Id(harcamaId);
    }
}