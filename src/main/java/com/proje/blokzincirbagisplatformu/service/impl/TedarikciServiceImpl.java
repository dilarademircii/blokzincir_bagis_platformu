package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.model.Tedarikci;
import com.proje.blokzincirbagisplatformu.repository.TedarikciRepository;
import com.proje.blokzincirbagisplatformu.service.TedarikciService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TedarikciServiceImpl extends TemelServiceImpl<Tedarikci>
        implements TedarikciService {

    private final TedarikciRepository tedarikciRepository;

    public TedarikciServiceImpl(TedarikciRepository tedarikciRepository) {
        super(tedarikciRepository);
        this.tedarikciRepository = tedarikciRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tedarikci> vergiNumarasiIleGetir(String vergiNumarasi) {
        return tedarikciRepository.findByVergiNumarasi(vergiNumarasi);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean vergiNumarasiKayitliMi(String vergiNumarasi) {
        return tedarikciRepository.existsByVergiNumarasi(vergiNumarasi);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailKayitliMi(String email) {
        return tedarikciRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tedarikci> emailIleGetir(String email) {
        return tedarikciRepository.findByEmailIgnoreCase(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Tedarikci> aktifTedarikciler(Pageable pageable) {
        return tedarikciRepository.findByAktifTrue(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Tedarikci> pasifTedarikciler(Pageable pageable) {
        return tedarikciRepository.findByAktifFalse(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Tedarikci> firmaAdiIleAra(String firmaAdi, Pageable pageable) {
        return tedarikciRepository.findByFirmaAdiContainingIgnoreCase(firmaAdi, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Tedarikci> harcamasiBulunanlar(Pageable pageable) {
        return tedarikciRepository.harcamasiBulunanTedarikcileriGetir(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public long harcamaSayisi(Long tedarikciId) {
        return tedarikciRepository.harcamaSayisiniGetir(tedarikciId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Tedarikci> ara(String arama, Pageable pageable) {
        return tedarikciRepository.tedarikciAra(arama, pageable);
    }

    @Override
    public Tedarikci durumGuncelle(Long tedarikciId, Boolean aktif) {
        Tedarikci tedarikci = tedarikciRepository.findById(tedarikciId)
                .orElseThrow(() -> new RuntimeException("Tedarikçi bulunamadı! ID: " + tedarikciId));

        tedarikci.setAktif(aktif); // Eğer entity'de setAktif metodu yoksa alan ismine göre güncelleyebilirsin
        return tedarikciRepository.save(tedarikci);
    }
}