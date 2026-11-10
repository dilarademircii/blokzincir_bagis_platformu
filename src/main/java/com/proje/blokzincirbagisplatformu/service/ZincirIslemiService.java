package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.model.ZincirIslemi;
import java.util.Optional;

public interface ZincirIslemiService extends TemelService<ZincirIslemi> {

    /** Belirli bir işlemin ve log indeksinin daha önce kaydedilip kaydedilmediğini kontrol eder */
    boolean islemKayitliMi(String txHash, Integer logIndex);

    /** TxHash değerine göre zincir işlemini getirir */
    Optional<ZincirIslemi> txHashIleGetir(String txHash);
}