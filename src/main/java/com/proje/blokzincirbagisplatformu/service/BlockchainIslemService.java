package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.model.BlockchainIslem;
import com.proje.blokzincirbagisplatformu.model.enums.BlockchainIslemDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.BlockchainIslemTuru;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BlockchainIslemService extends TemelService<BlockchainIslem> {

    /** Transaction hash daha önce kaydedilmiş mi? */
    boolean transactionHashKayitliMi(String transactionHash);

    /** Transaction hash ile işlem bul */
    Optional<BlockchainIslem> transactionHashIleGetir(String transactionHash);

    /** Ağ kimliğine göre işlemleri listele */
    Page<BlockchainIslem> chainIdIleGetir(Long chainId, Pageable pageable);

    /** Duruma göre işlemleri listele */
    Page<BlockchainIslem> durumIleGetir(BlockchainIslemDurumu durum, Pageable pageable);

    /** İşlem türüne göre listele */
    Page<BlockchainIslem> islemTuruIleGetir(BlockchainIslemTuru islemTuru, Pageable pageable);

    /** Ağ ve duruma göre filtrele */
    Page<BlockchainIslem> chainIdVeDurumIleGetir(Long chainId, BlockchainIslemDurumu durum, Pageable pageable);

    /** Bağışa ait blockchain işlemleri */
    List<BlockchainIslem> bagisIdIleGetir(Long bagisId);

    /** Harcamaya ait blockchain işlemleri */
    List<BlockchainIslem> harcamaIdIleGetir(Long harcamaId);

    /** Onaylanmış işlemler */
    Page<BlockchainIslem> onaylanmisIslemler(Pageable pageable);

    /** Henüz onaylanmamış işlemler */
    Page<BlockchainIslem> onaylanmamisIslemler(Pageable pageable);

    /** Tarih aralığındaki işlemler */
    Page<BlockchainIslem> tarihAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis, Pageable pageable);

    /** Belirli ağda ve durumda bekleyen işlemleri eskiden yeniye getir */
    List<BlockchainIslem> bekleyenIslemleriGetir(Long chainId, BlockchainIslemDurumu durum);

    /** Belirli bağış için işlem türü mevcut mu? */
    boolean bagisTuruIleVarMi(Long bagisId, BlockchainIslemTuru islemTuru);

    /** Belirli harcama için işlem var mı? */
    boolean harcamaIslemiVarMi(Long harcamaId);

    /** İşlemi bağış ve harcama bilgileriyle getir */
    Optional<BlockchainIslem> detayliGetir(Long islemId);

    /** Ağdaki belirli durumdaki işlem sayısı */
    long chainIdVeDurumSayisi(Long chainId, BlockchainIslemDurumu durum);

}
