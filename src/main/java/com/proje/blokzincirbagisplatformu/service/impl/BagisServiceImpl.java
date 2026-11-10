package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.exception.EntityNotFoundException;
import com.proje.blokzincirbagisplatformu.model.Bagis;
import com.proje.blokzincirbagisplatformu.model.enums.BagisDurumu;
import com.proje.blokzincirbagisplatformu.repository.BagisRepository;
import com.proje.blokzincirbagisplatformu.service.BagisService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BagisServiceImpl extends TemelServiceImpl<Bagis>
        implements BagisService {

    private final BagisRepository bagisRepository;

    public BagisServiceImpl(BagisRepository bagisRepository) {
        super(bagisRepository);
        this.bagisRepository = bagisRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bagis> bagisciIdIleGetir(Long bagisciId, Pageable pageable) {
        return bagisRepository.findByBagisci_Id(bagisciId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bagis> kampanyaIdIleGetir(Long kampanyaId, Pageable pageable) {
        return bagisRepository.findByKampanya_Id(kampanyaId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bagis> durumaGoreGetir(BagisDurumu durum, Pageable pageable) {
        return bagisRepository.findByDurum(durum, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bagis> bagisciVeDurumaGoreGetir(Long bagisciId, BagisDurumu durum, Pageable pageable) {
        return bagisRepository.findByBagisci_IdAndDurum(bagisciId, durum, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bagis> kampanyaVeDurumaGoreGetir(Long kampanyaId, BagisDurumu durum) {
        return bagisRepository.findByKampanya_IdAndDurum(kampanyaId, durum);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bagis> cuzdanIdIleGetir(Long cuzdanId, Pageable pageable) {
        return bagisRepository.findByCuzdan_Id(cuzdanId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bagis> tarihAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis, Pageable pageable) {
        return bagisRepository.findByOlusturmaTarihiBetween(baslangic, bitis, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal bagisciToplamTutarGetir(Long bagisciId, BagisDurumu durum) {
        return bagisRepository.toplamBagisTutariGetir(bagisciId, durum);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal kampanyaToplamTutarGetir(Long kampanyaId, BagisDurumu durum) {
        return bagisRepository.kampanyaBagisTutariGetir(kampanyaId, durum);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean blockchainIslemiVarMi(Long bagisId) {
        bagisGetir(bagisId);
        return bagisRepository.blockchainIslemiVarMi(bagisId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean bagisVarMi(Long bagisciId, Long kampanyaId) {
        return bagisRepository.existsByBagisci_IdAndKampanya_Id(bagisciId, kampanyaId);
    }

    @Override
    @Transactional(readOnly = true)
    public long kampanyaBagisSayisi(Long kampanyaId) {
        return bagisRepository.countByKampanya_Id(kampanyaId);
    }

    @Override
    @Transactional(readOnly = true)
    public long bagisciBagisSayisi(Long bagisciId) {
        return bagisRepository.countByBagisci_Id(bagisciId);
    }

    // -------------------------------------------------------------------------
    private Bagis bagisGetir(Long bagisId) {
        return bagisRepository.findById(bagisId)
                .orElseThrow(() -> new EntityNotFoundException("Bağış", bagisId));
    }
}
