package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.dto.request.BagisRequest;
import com.proje.blokzincirbagisplatformu.model.Bagis;
import com.proje.blokzincirbagisplatformu.model.enums.BagisDurumu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BagisService extends TemelService<Bagis> {

    Bagis bagisOlustur(String cuzdanAdresi, BagisRequest request);
    Bagis blockchainBagisiniDogrula(Long bagisId, String transactionHash, String cuzdanAdresi);
    Page<Bagis> bagisciIdIleGetir(Long bagisciId, Pageable pageable);

    Page<Bagis> kampanyaIdIleGetir(Long kampanyaId, Pageable pageable);

    Page<Bagis> durumaGoreGetir(BagisDurumu durum, Pageable pageable);

    Page<Bagis> bagisciVeDurumaGoreGetir(
            Long bagisciId,
            BagisDurumu durum,
            Pageable pageable
    );

    List<Bagis> kampanyaVeDurumaGoreGetir(
            Long kampanyaId,
            BagisDurumu durum
    );

    Page<Bagis> cuzdanIdIleGetir(Long cuzdanId, Pageable pageable);

    Page<Bagis> tarihAraligiIleGetir(
            LocalDateTime baslangic,
            LocalDateTime bitis,
            Pageable pageable
    );

    BigDecimal bagisciToplamTutarGetir(
            Long bagisciId,
            BagisDurumu durum
    );

    BigDecimal kampanyaToplamTutarGetir(
            Long kampanyaId,
            BagisDurumu durum
    );

    boolean blockchainIslemiVarMi(Long bagisId);

    boolean bagisVarMi(Long bagisciId, Long kampanyaId);

    long kampanyaBagisSayisi(Long kampanyaId);

    long bagisciBagisSayisi(Long bagisciId);

    Page<Bagis> cuzdanAdresiIleBagiscininBagislariniGetir(
            String cuzdanAdresi,
            Pageable pageable
    );
}
