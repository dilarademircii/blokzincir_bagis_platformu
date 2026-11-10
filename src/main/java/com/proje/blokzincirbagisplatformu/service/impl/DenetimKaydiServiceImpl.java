package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.model.DenetimKaydi;
import com.proje.blokzincirbagisplatformu.repository.DenetimKaydiRepository;
import com.proje.blokzincirbagisplatformu.service.DenetimKaydiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DenetimKaydiServiceImpl extends TemelServiceImpl<DenetimKaydi>
        implements DenetimKaydiService {

    private final DenetimKaydiRepository denetimKaydiRepository;

    public DenetimKaydiServiceImpl(DenetimKaydiRepository denetimKaydiRepository) {
        super(denetimKaydiRepository);
        this.denetimKaydiRepository = denetimKaydiRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DenetimKaydi> kullaniciIdIleGetir(Long kullaniciId, Pageable pageable) {
        return denetimKaydiRepository.findByKullanici_Id(kullaniciId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DenetimKaydi> islemTuruIleGetir(String islemTuru, Pageable pageable) {
        return denetimKaydiRepository.findByIslemTuru(islemTuru, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DenetimKaydi> varlikTuruIleGetir(String varlikTuru, Pageable pageable) {
        return denetimKaydiRepository.findByVarlikTuru(varlikTuru, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DenetimKaydi> varlikTuruVeIdIleGetir(String varlikTuru, Long varlikId, Pageable pageable) {
        return denetimKaydiRepository.findByVarlikTuruAndVarlikId(varlikTuru, varlikId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DenetimKaydi> tarihAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis, Pageable pageable) {
        return denetimKaydiRepository.findByIslemTarihiBetween(baslangic, bitis, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DenetimKaydi> kullaniciTarihAraligiIleGetir(Long kullaniciId, LocalDateTime baslangic, LocalDateTime bitis, Pageable pageable) {
        return denetimKaydiRepository.findByKullanici_IdAndIslemTarihiBetween(kullaniciId, baslangic, bitis, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DenetimKaydi> ipAdresineGoreGetir(String ipAdresi, Pageable pageable) {
        return denetimKaydiRepository.findByIpAdresi(ipAdresi, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DenetimKaydi> detayliGetir(Long kayitId) {
        return denetimKaydiRepository.denetimKaydiniDetayliGetir(kayitId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DenetimKaydi> kullaniciVarlikGecmisiniGetir(Long kullaniciId, String varlikTuru, Long varlikId) {
        return denetimKaydiRepository.kullaniciVarlikGecmisiniGetir(kullaniciId, varlikTuru, varlikId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DenetimKaydi> sistemKayitlari(Pageable pageable) {
        return denetimKaydiRepository.findByKullaniciIsNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public long kullaniciIslemSayisi(Long kullaniciId) {
        return denetimKaydiRepository.countByKullanici_Id(kullaniciId);
    }
}