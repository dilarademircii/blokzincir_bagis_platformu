package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.model.ZincirIslemi;
import com.proje.blokzincirbagisplatformu.repository.ZincirIslemiRepository;
import com.proje.blokzincirbagisplatformu.service.ZincirIslemiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ZincirIslemiServiceImpl extends TemelServiceImpl<ZincirIslemi>
        implements ZincirIslemiService {

    private final ZincirIslemiRepository zincirIslemiRepository;

    public ZincirIslemiServiceImpl(ZincirIslemiRepository zincirIslemiRepository) {
        super(zincirIslemiRepository);
        this.zincirIslemiRepository = zincirIslemiRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean islemKayitliMi(String txHash, Integer logIndex) {
        return zincirIslemiRepository.existsByTxHashAndLogIndex(txHash, logIndex);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ZincirIslemi> txHashIleGetir(String txHash) {
        return zincirIslemiRepository.findByTxHash(txHash);
    }
}