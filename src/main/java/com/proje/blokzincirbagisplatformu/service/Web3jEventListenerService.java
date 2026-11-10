package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.model.ZincirIslemi;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;

@Service
public class Web3jEventListenerService {

    private final Web3j web3j;
    private final ZincirIslemiService zincirIslemiService;

    public Web3jEventListenerService(Web3j web3j, ZincirIslemiService zincirIslemiService) {
        this.web3j = web3j;
        this.zincirIslemiService = zincirIslemiService;
    }

    /**
     * Uygulama ayağa kalktığında Sepolia test ağındaki blokları dinlemeye başlar[cite: 14, 19].
     */
    @PostConstruct
    public void listenToBlockchainEvents() {
        try {
            web3j.blockFlowable(false).subscribe(ethBlock -> {
                // Gelen blok içerisindeki işlemler ve loglar burada taranır
                EthBlock.Block block = ethBlock.getBlock();
                if (block != null && block.getTransactions() != null) {
                    // İşlem logları işleme adımı
                }
            }, throwable -> {
                System.err.println("Blockchain dinleme hatası: " + throwable.getMessage());
            });
        } catch (Exception e) {
            System.err.println("Web3j dinleyici başlatılamadı: " + e.getMessage());
        }
    }

    /**
     * Gelen zincir olayını veritabanına idempotent (tekil) olarak kaydeder[cite: 12, 19].
     */
    public void processEventLog(String txHash, Long blockNumber, Integer logIndex, String eventName, String contractAddress) {
        boolean exists = zincirIslemiService.islemKayitliMi(txHash, logIndex);
        if (exists) {
            return; // Olay daha önce işlenmiş, mükerrer kaydı önle[cite: 12, 19]
        }

        ZincirIslemi zincirIslemi = new ZincirIslemi();
        zincirIslemi.setTxHash(txHash);
        zincirIslemi.setBlockNumber(blockNumber);
        zincirIslemi.setLogIndex(logIndex);
        zincirIslemi.setEventName(eventName);
        zincirIslemi.setContractAddress(contractAddress);
        zincirIslemi.setStatus("CONFIRMED");

        zincirIslemiService.kaydet(zincirIslemi);
    }
}