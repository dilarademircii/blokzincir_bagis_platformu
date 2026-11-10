package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.model.BlockchainEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BlockchainEventService extends TemelService<BlockchainEvent> {

    /** Aynı event daha önce kaydedilmiş mi? */
    boolean eventKayitliMi(Long chainId, String transactionHash, Long logIndex);

    /** Event'i benzersiz alanlarıyla bul */
    Optional<BlockchainEvent> benzersizAlanlarlaBul(Long chainId, String transactionHash, Long logIndex);

    /** Transaction hash ile event kayıtları */
    List<BlockchainEvent> transactionHashIleGetir(String transactionHash);

    /** Ağ kimliğine göre event kayıtları (sayfalı) */
    Page<BlockchainEvent> chainIdIleGetir(Long chainId, Pageable pageable);

    /** İşlenmiş / işlenmemiş event kayıtları */
    Page<BlockchainEvent> islendiDurumunaGoreGetir(Boolean islendi, Pageable pageable);

    /** Event adına göre listele */
    Page<BlockchainEvent> eventAdiIleGetir(String eventAdi, Pageable pageable);

    /** Blockchain işlemine bağlı event kayıtları */
    List<BlockchainEvent> blockchainIslemIdIleGetir(Long blockchainIslemId);

    /** İşlenmemiş event'leri eskiden yeniye getir */
    List<BlockchainEvent> islenmemisEventiGetir();

    /** Belirli ağdaki işlenmemiş event'ler */
    List<BlockchainEvent> chainIdIleIslenmemisEventiGetir(Long chainId);

    /** İşlenecek event'i kilitleyerek bul (eşzamanlı işleme koruması) */
    Optional<BlockchainEvent> islenmemisEventiKilitle(Long eventId);

    /** Belirli blockchain işlemiyle ilişkilendirilmiş event var mı? */
    boolean blockchainIslemEventiVarMi(Long blockchainIslemId);

    /** Ağdaki toplam event sayısı */
    long chainIdEventSayisi(Long chainId);

    /** Ağdaki işlenmemiş event sayısı */
    long chainIdIslenmemisEventSayisi(Long chainId);

    /** Event'i işlendi olarak işaretle */
    BlockchainEvent islendiIsaretle(Long eventId);
}
