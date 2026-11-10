package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.exception.EntityNotFoundException;
import com.proje.blokzincirbagisplatformu.model.Kampanya;
import com.proje.blokzincirbagisplatformu.model.enums.KampanyaDurumu;
import com.proje.blokzincirbagisplatformu.repository.KampanyaRepository;
import com.proje.blokzincirbagisplatformu.service.KampanyaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KampanyaServiceImpl extends TemelServiceImpl<Kampanya>
        implements KampanyaService {

    private final KampanyaRepository kampanyaRepository;

    public KampanyaServiceImpl(KampanyaRepository kampanyaRepository) {
        super(kampanyaRepository);
        this.kampanyaRepository = kampanyaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kampanya> kurumIdIleGetir(Long kurumId, Pageable pageable) {
        return kampanyaRepository.findByKurum_Id(kurumId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kampanya> durumaGoreGetir(KampanyaDurumu durum, Pageable pageable) {
        return kampanyaRepository.findByDurum(durum, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kampanya> kurumVeDurumaGoreGetir(Long kurumId, KampanyaDurumu durum, Pageable pageable) {
        return kampanyaRepository.findByKurum_IdAndDurum(kurumId, durum, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kampanya> basligaGoreAra(String baslik, Pageable pageable) {
        return kampanyaRepository.findByBaslikContainingIgnoreCase(baslik, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kampanya> hedefTutarinUzerindekiler(BigDecimal hedefTutar, Pageable pageable) {
        return kampanyaRepository.findByHedefTutarGreaterThanEqual(hedefTutar, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Kampanya> baslangicTarihiAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis) {
        return kampanyaRepository.findByBaslangicTarihiBetween(baslangic, bitis);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Kampanya> bitisTarihiGecmisler(LocalDateTime tarih) {
        return kampanyaRepository.findByBitisTarihiBefore(tarih);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kampanya> devamEdenKampanyalariGetir(KampanyaDurumu durum, LocalDateTime simdi, Pageable pageable) {
        return kampanyaRepository.devamEdenKampanyalariGetir(durum, simdi, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Kampanya> kurumuylaBirlikteBul(Long kampanyaId) {
        return kampanyaRepository.kurumuylaBirlikteBul(kampanyaId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kampanya> ara(String arama, Pageable pageable) {
        return kampanyaRepository.kampanyaAra(arama, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public long kurumKampanyaSayisi(Long kurumId) {
        return kampanyaRepository.countByKurum_Id(kurumId);
    }

    @Override
    @Transactional(readOnly = true)
    public long kurumDurumluKampanyaSayisi(Long kurumId, KampanyaDurumu durum) {
        return kampanyaRepository.countByKurum_IdAndDurum(kurumId, durum);
    }
}
