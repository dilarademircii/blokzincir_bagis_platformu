package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.dto.response.HarcamaOnayResponse;
import com.proje.blokzincirbagisplatformu.mapper.HarcamaOnayMapper;
import com.proje.blokzincirbagisplatformu.model.HarcamaOnay;
import com.proje.blokzincirbagisplatformu.model.enums.OnayKarari;
import com.proje.blokzincirbagisplatformu.repository.HarcamaOnayRepository;
import com.proje.blokzincirbagisplatformu.service.HarcamaOnayService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class HarcamaOnayServiceImpl
        extends TemelServiceImpl<HarcamaOnay>
        implements HarcamaOnayService {

    private final HarcamaOnayRepository harcamaOnayRepository;
    private final HarcamaOnayMapper harcamaOnayMapper;

    public HarcamaOnayServiceImpl(
            HarcamaOnayRepository harcamaOnayRepository,
            HarcamaOnayMapper harcamaOnayMapper
    ) {
        super(harcamaOnayRepository);
        this.harcamaOnayRepository = harcamaOnayRepository;
        this.harcamaOnayMapper = harcamaOnayMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HarcamaOnay> harcamaIdIleGetir(Long harcamaId) {
        return harcamaOnayRepository.findByHarcama_Id(harcamaId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HarcamaOnay> onaylayanIdIleGetir(Long kullaniciId, Pageable pageable) {
        return harcamaOnayRepository.findByOnaylayan_Id(kullaniciId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HarcamaOnay> kararIleGetir(OnayKarari karar, Pageable pageable) {
        return harcamaOnayRepository.findByKarar(karar, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HarcamaOnay> harcamaVeKararIleGetir(Long harcamaId, OnayKarari karar) {
        return harcamaOnayRepository.findByHarcama_IdAndKarar(harcamaId, karar);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean kullaniciHarcamayiDegerlendirmisMi(Long harcamaId, Long kullaniciId) {
        return harcamaOnayRepository.existsByHarcama_IdAndOnaylayan_Id(harcamaId, kullaniciId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean kullaniciKarariVarMi(Long harcamaId, Long kullaniciId, OnayKarari karar) {
        return harcamaOnayRepository.existsByHarcama_IdAndOnaylayan_IdAndKarar(harcamaId, kullaniciId, karar);
    }

    @Override
    @Transactional(readOnly = true)
    public long harcamaKararSayisi(Long harcamaId, OnayKarari karar) {
        return harcamaOnayRepository.countByHarcama_IdAndKarar(harcamaId, karar);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HarcamaOnay> tarihAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis, Pageable pageable) {
        return harcamaOnayRepository.findByOnayTarihiBetween(baslangic, bitis, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HarcamaOnay> harcamaOnayGecmisiniGetir(Long harcamaId) {
        return harcamaOnayRepository.harcamaOnayGecmisiniGetir(harcamaId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HarcamaOnayResponse> kullaniciOnayGecmisiniGetir(
            Long kullaniciId,
            Pageable pageable
    ) {
        Page<HarcamaOnay> onayPage =
                harcamaOnayRepository.kullaniciOnayGecmisiniGetir(
                        kullaniciId,
                        pageable
                );

        return onayPage.map(harcamaOnayMapper::entityToResponse);
    }
}