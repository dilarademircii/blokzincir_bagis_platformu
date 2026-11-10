package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.exception.EntityNotFoundException;
import com.proje.blokzincirbagisplatformu.model.Harcama;
import com.proje.blokzincirbagisplatformu.model.enums.HarcamaDurumu;
import com.proje.blokzincirbagisplatformu.repository.HarcamaRepository;
import com.proje.blokzincirbagisplatformu.service.HarcamaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class HarcamaServiceImpl extends TemelServiceImpl<Harcama>
        implements HarcamaService {

    private final HarcamaRepository harcamaRepository;

    public HarcamaServiceImpl(HarcamaRepository harcamaRepository) {
        super(harcamaRepository);
        this.harcamaRepository = harcamaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Harcama> kampanyaIdIleGetir(Long kampanyaId, Pageable pageable) {
        return harcamaRepository.findByKampanya_Id(kampanyaId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Harcama> olusturanIdIleGetir(Long kullaniciId, Pageable pageable) {
        return harcamaRepository.findByOlusturan_Id(kullaniciId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Harcama> tedarikciIdIleGetir(Long tedarikciId, Pageable pageable) {
        return harcamaRepository.findByTedarikci_Id(tedarikciId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Harcama> durumaGoreGetir(HarcamaDurumu durum, Pageable pageable) {
        return harcamaRepository.findByDurum(durum, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Harcama> kampanyaVeDurumaGoreGetir(Long kampanyaId, HarcamaDurumu durum, Pageable pageable) {
        return harcamaRepository.findByKampanya_IdAndDurum(kampanyaId, durum, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Harcama> tutarinUzerindekiler(BigDecimal tutar, Pageable pageable) {
        return harcamaRepository.findByTutarGreaterThanEqual(tutar, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Harcama> tarihAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis, Pageable pageable) {
        return harcamaRepository.findByOlusturmaTarihiBetween(baslangic, bitis, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Harcama> gerceklesmisDurumdakiler(Pageable pageable) {
        return harcamaRepository.findByGerceklesmeTarihiIsNotNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Harcama> gerceklesmemis(Pageable pageable) {
        return harcamaRepository.findByGerceklesmeTarihiIsNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal kampanyaToplamHarcama(Long kampanyaId, HarcamaDurumu durum, String paraBirimi) {
        return harcamaRepository.kampanyaToplamHarcamaTutari(kampanyaId, durum, paraBirimi);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Harcama> detayliGetir(Long harcamaId) {
        return harcamaRepository.harcamaDetayiniGetir(harcamaId);
    }

    @Override
    @Transactional(readOnly = true)
    public long kampanyaDurumluHarcamaSayisi(Long kampanyaId, HarcamaDurumu durum) {
        return harcamaRepository.countByKampanya_IdAndDurum(kampanyaId, durum);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Harcama> ara(String arama, Pageable pageable) {
        return harcamaRepository.harcamaAra(arama, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Harcama> kanitBelgeliHarcamalar(Pageable pageable) {
        return harcamaRepository.kanitBelgeliHarcamalariGetir(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Harcama> kanitBelgesizHarcamalar(Pageable pageable) {
        return harcamaRepository.kanitBelgesizHarcamalariGetir(pageable);
    }

    // -------------------------------------------------------------------------
    @SuppressWarnings("unused")
    private Harcama harcamaGetir(Long harcamaId) {
        return harcamaRepository.findById(harcamaId)
                .orElseThrow(() -> new EntityNotFoundException("Harcama", harcamaId));
    }
}
