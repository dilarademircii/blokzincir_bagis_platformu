package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.exception.EntityNotFoundException;
import com.proje.blokzincirbagisplatformu.model.BlockchainIslem;
import com.proje.blokzincirbagisplatformu.model.enums.BlockchainIslemDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.BlockchainIslemTuru;
import com.proje.blokzincirbagisplatformu.repository.BlockchainIslemRepository;
import com.proje.blokzincirbagisplatformu.service.BlockchainIslemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BlockchainIslemServiceImpl extends TemelServiceImpl<BlockchainIslem>
        implements BlockchainIslemService {

    private final BlockchainIslemRepository blockchainIslemRepository;

    public BlockchainIslemServiceImpl(BlockchainIslemRepository blockchainIslemRepository) {
        super(blockchainIslemRepository);
        this.blockchainIslemRepository = blockchainIslemRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean transactionHashKayitliMi(String transactionHash) {
        return blockchainIslemRepository.existsByTransactionHash(transactionHash);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BlockchainIslem> transactionHashIleGetir(String transactionHash) {
        return blockchainIslemRepository.findByTransactionHash(transactionHash);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlockchainIslem> chainIdIleGetir(Long chainId, Pageable pageable) {
        return blockchainIslemRepository.findByChainId(chainId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlockchainIslem> durumIleGetir(BlockchainIslemDurumu durum, Pageable pageable) {
        return blockchainIslemRepository.findByDurum(durum, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlockchainIslem> islemTuruIleGetir(BlockchainIslemTuru islemTuru, Pageable pageable) {
        return blockchainIslemRepository.findByIslemTuru(islemTuru, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlockchainIslem> chainIdVeDurumIleGetir(Long chainId, BlockchainIslemDurumu durum, Pageable pageable) {
        return blockchainIslemRepository.findByChainIdAndDurum(chainId, durum, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlockchainIslem> bagisIdIleGetir(Long bagisId) {
        return blockchainIslemRepository.findByBagis_Id(bagisId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlockchainIslem> harcamaIdIleGetir(Long harcamaId) {
        return blockchainIslemRepository.findByHarcama_Id(harcamaId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlockchainIslem> onaylanmisIslemler(Pageable pageable) {
        return blockchainIslemRepository.findByOnaylanmaTarihiIsNotNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlockchainIslem> onaylanmamisIslemler(Pageable pageable) {
        return blockchainIslemRepository.findByOnaylanmaTarihiIsNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlockchainIslem> tarihAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis, Pageable pageable) {
        return blockchainIslemRepository.findByOlusturmaTarihiBetween(baslangic, bitis, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlockchainIslem> bekleyenIslemleriGetir(Long chainId, BlockchainIslemDurumu durum) {
        return blockchainIslemRepository.bekleyenIslemleriGetir(chainId, durum);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean bagisTuruIleVarMi(Long bagisId, BlockchainIslemTuru islemTuru) {
        return blockchainIslemRepository.existsByBagis_IdAndIslemTuru(bagisId, islemTuru);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean harcamaIslemiVarMi(Long harcamaId) {
        return blockchainIslemRepository.existsByHarcama_Id(harcamaId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BlockchainIslem> detayliGetir(Long islemId) {
        return blockchainIslemRepository.islemDetayiniGetir(islemId);
    }

    @Override
    @Transactional(readOnly = true)
    public long chainIdVeDurumSayisi(Long chainId, BlockchainIslemDurumu durum) {
        return blockchainIslemRepository.countByChainIdAndDurum(chainId, durum);
    }

    // -------------------------------------------------------------------------
    @SuppressWarnings("unused")
    private BlockchainIslem islemGetir(Long islemId) {
        return blockchainIslemRepository.findById(islemId)
                .orElseThrow(() -> new EntityNotFoundException("Blockchain İşlemi", islemId));
    }
}
