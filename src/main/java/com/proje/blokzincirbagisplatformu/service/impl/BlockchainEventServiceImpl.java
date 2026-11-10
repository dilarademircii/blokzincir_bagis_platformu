package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.exception.EntityNotFoundException;
import com.proje.blokzincirbagisplatformu.model.BlockchainEvent;
import com.proje.blokzincirbagisplatformu.repository.BlockchainEventRepository;
import com.proje.blokzincirbagisplatformu.service.BlockchainEventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BlockchainEventServiceImpl extends TemelServiceImpl<BlockchainEvent>
        implements BlockchainEventService {

    private final BlockchainEventRepository blockchainEventRepository;

    public BlockchainEventServiceImpl(BlockchainEventRepository blockchainEventRepository) {
        super(blockchainEventRepository);
        this.blockchainEventRepository = blockchainEventRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean eventKayitliMi(Long chainId, String transactionHash, Long logIndex) {
        return blockchainEventRepository.existsByChainIdAndTransactionHashAndLogIndex(
                chainId, transactionHash, logIndex);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BlockchainEvent> benzersizAlanlarlaBul(Long chainId, String transactionHash, Long logIndex) {
        return blockchainEventRepository.findByChainIdAndTransactionHashAndLogIndex(
                chainId, transactionHash, logIndex);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlockchainEvent> transactionHashIleGetir(String transactionHash) {
        return blockchainEventRepository.findByTransactionHash(transactionHash);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlockchainEvent> chainIdIleGetir(Long chainId, Pageable pageable) {
        return blockchainEventRepository.findByChainId(chainId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlockchainEvent> islendiDurumunaGoreGetir(Boolean islendi, Pageable pageable) {
        return blockchainEventRepository.findByIslendi(islendi, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlockchainEvent> eventAdiIleGetir(String eventAdi, Pageable pageable) {
        return blockchainEventRepository.findByEventAdi(eventAdi, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlockchainEvent> blockchainIslemIdIleGetir(Long blockchainIslemId) {
        return blockchainEventRepository.findByBlockchainIslem_Id(blockchainIslemId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlockchainEvent> islenmemisEventiGetir() {
        return blockchainEventRepository.findByIslendiFalseOrderByOlusturmaTarihiAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlockchainEvent> chainIdIleIslenmemisEventiGetir(Long chainId) {
        return blockchainEventRepository.findByChainIdAndIslendiFalseOrderByOlusturmaTarihiAsc(chainId);
    }

    @Override
    @Transactional  // PESSIMISTIC_WRITE kilidi için readOnly olmamalı
    public Optional<BlockchainEvent> islenmemisEventiKilitle(Long eventId) {
        return blockchainEventRepository.islenmemisEventiKilitle(eventId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean blockchainIslemEventiVarMi(Long blockchainIslemId) {
        return blockchainEventRepository.existsByBlockchainIslem_Id(blockchainIslemId);
    }

    @Override
    @Transactional(readOnly = true)
    public long chainIdEventSayisi(Long chainId) {
        return blockchainEventRepository.countByChainId(chainId);
    }

    @Override
    @Transactional(readOnly = true)
    public long chainIdIslenmemisEventSayisi(Long chainId) {
        return blockchainEventRepository.countByChainIdAndIslendiFalse(chainId);
    }

    @Override
    public BlockchainEvent islendiIsaretle(Long eventId) {
        BlockchainEvent event = eventGetir(eventId);
        event.setIslendi(true);
        event.setIslenmeTarihi(LocalDateTime.now());
        return blockchainEventRepository.save(event);
    }

    // -------------------------------------------------------------------------
    private BlockchainEvent eventGetir(Long eventId) {
        return blockchainEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Blockchain Event", eventId));
    }
}
