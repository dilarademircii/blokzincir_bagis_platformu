package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.exception.EntityNotFoundException;
import com.proje.blokzincirbagisplatformu.model.Kullanici;
import com.proje.blokzincirbagisplatformu.model.enums.HesapDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.KullaniciRol;
import com.proje.blokzincirbagisplatformu.repository.KullaniciRepository;
import com.proje.blokzincirbagisplatformu.service.KullaniciService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KullaniciServiceImpl extends TemelServiceImpl<Kullanici>
        implements KullaniciService {

    private final KullaniciRepository kullaniciRepository;

    public KullaniciServiceImpl(KullaniciRepository kullaniciRepository) {
        super(kullaniciRepository);
        this.kullaniciRepository = kullaniciRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailKayitliMi(String email) {
        return kullaniciRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Kullanici> emailIleGetir(String email) {
        return kullaniciRepository.findByEmailIgnoreCase(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kullanici> hesapDurumunaGoreGetir(HesapDurumu hesapDurumu, Pageable pageable) {
        return kullaniciRepository.findByHesapDurumu(hesapDurumu, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kullanici> roleGoreGetir(KullaniciRol rol, Pageable pageable) {
        return kullaniciRepository.findByRol(rol, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kullanici> kurumIdIleGetir(Long kurumId, Pageable pageable) {
        return kullaniciRepository.findByKurum_Id(kurumId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kullanici> kurumVeHesapDurumunaGoreGetir(Long kurumId, HesapDurumu hesapDurumu, Pageable pageable) {
        return kullaniciRepository.findByKurum_IdAndHesapDurumu(kurumId, hesapDurumu, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kullanici> ara(String arama, Pageable pageable) {
        return kullaniciRepository.kullaniciAra(arama, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Kullanici> tarihAraligiIleGetir(LocalDateTime baslangic, LocalDateTime bitis) {
        return kullaniciRepository.findByOlusturmaTarihiBetween(baslangic, bitis);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Kullanici> kurumsuKullanicilar(Pageable pageable) {
        return kullaniciRepository.findByKurumIsNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public long kurumRolluKullaniciSayisi(Long kurumId, KullaniciRol rol) {
        return kullaniciRepository.countByKurum_IdAndRol(kurumId, rol);
    }

    @Override
    @Transactional(readOnly = true)
    public long kurumAktifHesapSayisi(Long kurumId, HesapDurumu hesapDurumu) {
        return kullaniciRepository.countByKurum_IdAndHesapDurumu(kurumId, hesapDurumu);
    }

    @Override
    public Kullanici hesapDurumunuGuncelle(Long kullaniciId, HesapDurumu yeniDurum) {
        Kullanici kullanici = kullaniciGetir(kullaniciId);
        kullanici.setHesapDurumu(yeniDurum);
        return kullaniciRepository.save(kullanici);
    }

    // -------------------------------------------------------------------------
    private Kullanici kullaniciGetir(Long kullaniciId) {
        return kullaniciRepository.findById(kullaniciId)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı", kullaniciId));
    }
}
