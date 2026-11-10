package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.exception.EntityNotFoundException;
import com.proje.blokzincirbagisplatformu.model.Bildirim;
import com.proje.blokzincirbagisplatformu.model.enums.BildirimDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.BildirimTuru;
import com.proje.blokzincirbagisplatformu.repository.BildirimRepository;
import com.proje.blokzincirbagisplatformu.service.BildirimService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class BildirimServiceImpl extends TemelServiceImpl<Bildirim>
        implements BildirimService {

    private final BildirimRepository bildirimRepository;

    public BildirimServiceImpl(BildirimRepository bildirimRepository) {
        super(bildirimRepository);
        this.bildirimRepository = bildirimRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bildirim> kullaniciIdIleGetir(Long kullaniciId, Pageable pageable) {
        return bildirimRepository.findByKullanici_Id(kullaniciId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bildirim> kullaniciVeDurumIleGetir(Long kullaniciId, BildirimDurumu durum, Pageable pageable) {
        return bildirimRepository.findByKullanici_IdAndDurum(kullaniciId, durum, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bildirim> kullaniciDurumTarihSiraliGetir(Long kullaniciId, BildirimDurumu durum, Pageable pageable) {
        return bildirimRepository.findByKullanici_IdAndDurumOrderByOlusturmaTarihiDesc(kullaniciId, durum, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bildirim> bildirimTuruIleGetir(BildirimTuru bildirimTuru, Pageable pageable) {
        return bildirimRepository.findByBildirimTuru(bildirimTuru, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bildirim> kullaniciVeTurIleGetir(Long kullaniciId, BildirimTuru bildirimTuru, Pageable pageable) {
        return bildirimRepository.findByKullanici_IdAndBildirimTuru(kullaniciId, bildirimTuru, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public long okunmamisBildirimSayisi(Long kullaniciId, BildirimDurumu durum) {
        return bildirimRepository.countByKullanici_IdAndDurum(kullaniciId, durum);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bildirim> kullaniciTarihAraligiIleGetir(Long kullaniciId, LocalDateTime baslangic,
                                                         LocalDateTime bitis, Pageable pageable) {
        return bildirimRepository.findByKullanici_IdAndOlusturmaTarihiBetween(kullaniciId, baslangic, bitis, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bildirim> okunmusDurumVeTarihtenOncekiler(BildirimDurumu durum, LocalDateTime tarih, Pageable pageable) {
        return bildirimRepository.findByDurumAndOkunmaTarihiBefore(durum, tarih, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bildirim> enYeniBildirimleriGetir(Long kullaniciId, Pageable pageable) {
        return bildirimRepository.enYeniBildirimleriGetir(kullaniciId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean kullaniciyaAitMi(Long bildirimId, Long kullaniciId) {
        return bildirimRepository.existsByIdAndKullanici_Id(bildirimId, kullaniciId);
    }

    @Override
    public Bildirim okunduIsaretle(Long bildirimId) {
        Bildirim bildirim = bildirimGetir(bildirimId);
        bildirim.setDurum(BildirimDurumu.OKUNDU);
        bildirim.setOkunmaTarihi(LocalDateTime.now());
        return bildirimRepository.save(bildirim);
    }

    // -------------------------------------------------------------------------
    private Bildirim bildirimGetir(Long bildirimId) {
        return bildirimRepository.findById(bildirimId)
                .orElseThrow(() -> new EntityNotFoundException("Bildirim", bildirimId));
    }
}
