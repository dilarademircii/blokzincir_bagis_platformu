package com.proje.blokzincirbagisplatformu.service;

import com.proje.blokzincirbagisplatformu.model.Tedarikci;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TedarikciService extends TemelService<Tedarikci> {

    /** Vergi numarasıyla tedarikçi bul */
    Optional<Tedarikci> vergiNumarasiIleGetir(String vergiNumarasi);

    /** Vergi numarası kayıtlı mı? */
    boolean vergiNumarasiKayitliMi(String vergiNumarasi);

    /** E-posta kontrolü */
    boolean emailKayitliMi(String email);

    /** E-posta ile tedarikçi bul */
    Optional<Tedarikci> emailIleGetir(String email);

    /** Aktif tedarikçileri listele */
    Page<Tedarikci> aktifTedarikciler(Pageable pageable);

    /** Pasif tedarikçileri listele */
    Page<Tedarikci> pasifTedarikciler(Pageable pageable);

    /** Firma adına göre arama */
    Page<Tedarikci> firmaAdiIleAra(String firmaAdi, Pageable pageable);

    /** Harcaması bulunan tedarikçiler */
    Page<Tedarikci> harcamasiBulunanlar(Pageable pageable);

    /** Tedarikçinin harcama sayısı */
    long harcamaSayisi(Long tedarikciId);

    /** Firma adı, e-posta veya vergi numarasına göre genel arama */
    Page<Tedarikci> ara(String arama, Pageable pageable);

    /** Tedarikçi aktiflik durumunu güncelle */
    Tedarikci durumGuncelle(Long tedarikciId, Boolean aktif);
}
