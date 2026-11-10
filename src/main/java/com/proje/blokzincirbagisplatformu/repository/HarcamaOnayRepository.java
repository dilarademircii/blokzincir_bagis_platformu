
package com.proje.blokzincirbagisplatformu.repository;

import com.proje.blokzincirbagisplatformu.model.HarcamaOnay;
import com.proje.blokzincirbagisplatformu.model.enums.OnayKarari;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HarcamaOnayRepository
        extends JpaRepository<HarcamaOnay, Long> {

    // Harcamaya ait tüm onaylar
    List<HarcamaOnay> findByHarcama_Id(Long harcamaId);

    // Onaylayan kullanıcının verdiği kararlar
    Page<HarcamaOnay> findByOnaylayan_Id(
            Long kullaniciId, Pageable pageable);

    // Belirli karar türüne göre listeleme
    Page<HarcamaOnay> findByKarar(
            OnayKarari karar, Pageable pageable);

    // Harcama ve karar türüne göre listeleme
    List<HarcamaOnay> findByHarcama_IdAndKarar(
            Long harcamaId, OnayKarari karar);

    // Kullanıcı bu harcamayı daha önce değerlendirmiş mi?
    boolean existsByHarcama_IdAndOnaylayan_Id(
            Long harcamaId, Long kullaniciId);

    // Kullanıcı belirli kararı vermiş mi?
    boolean existsByHarcama_IdAndOnaylayan_IdAndKarar(
            Long harcamaId,
            Long kullaniciId,
            OnayKarari karar);

    // Harcama için belirli karar sayısı
    long countByHarcama_IdAndKarar(
            Long harcamaId, OnayKarari karar);

    // Onay tarihi aralığındaki kayıtlar
    Page<HarcamaOnay> findByOnayTarihiBetween(
            LocalDateTime baslangic,
            LocalDateTime bitis,
            Pageable pageable);

    // Harcama onaylarını kullanıcı bilgisiyle getirme
    @Query("""
        SELECT ho FROM HarcamaOnay ho
        JOIN FETCH ho.onaylayan
        WHERE ho.harcama.id = :harcamaId
        ORDER BY ho.onayTarihi DESC
        """)
    List<HarcamaOnay> harcamaOnayGecmisiniGetir(
            @Param("harcamaId") Long harcamaId);

    // Kullanıcının verdiği son onay kayıtları
    @Query("""
        SELECT ho FROM HarcamaOnay ho
        WHERE ho.onaylayan.id = :kullaniciId
        ORDER BY ho.onayTarihi DESC
        """)
    Page<HarcamaOnay> kullaniciOnayGecmisiniGetir(
            @Param("kullaniciId") Long kullaniciId,
            Pageable pageable);
}
