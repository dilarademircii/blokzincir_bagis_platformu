package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.exception.EntityNotFoundException;
import com.proje.blokzincirbagisplatformu.model.Cuzdan;
import com.proje.blokzincirbagisplatformu.model.enums.CuzdanDurumu;
import com.proje.blokzincirbagisplatformu.repository.CuzdanRepository;
import com.proje.blokzincirbagisplatformu.service.CuzdanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CuzdanServiceImpl extends TemelServiceImpl<Cuzdan>
        implements CuzdanService {

    private final CuzdanRepository cuzdanRepository;

    public CuzdanServiceImpl(CuzdanRepository cuzdanRepository) {
        super(cuzdanRepository);
        this.cuzdanRepository = cuzdanRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean cuzdanAdresiKayitliMi(String cuzdanAdresi) {
        return cuzdanRepository.existsByCuzdanAdresiIgnoreCase(cuzdanAdresi);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cuzdan> adreseGoreBul(String cuzdanAdresi) {
        return cuzdanRepository.findByCuzdanAdresiIgnoreCase(cuzdanAdresi);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cuzdan> kullaniciIdIleGetir(Long kullaniciId) {
        return cuzdanRepository.findByKullanici_Id(kullaniciId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cuzdan> kullaniciVeDurumIleGetir(Long kullaniciId, CuzdanDurumu durum) {
        return cuzdanRepository.findByKullanici_IdAndDurum(kullaniciId, durum);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cuzdan> durumIleGetir(CuzdanDurumu durum, Pageable pageable) {
        return cuzdanRepository.findByDurum(durum, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cuzdan> agAdiIleGetir(String agAdi, Pageable pageable) {
        return cuzdanRepository.findByAgAdiIgnoreCase(agAdi, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean kullaniciDurumluCuzdaniVarMi(Long kullaniciId, CuzdanDurumu durum) {
        return cuzdanRepository.existsByKullanici_IdAndDurum(kullaniciId, durum);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cuzdan> dogrulanmisCuzdanlar(Pageable pageable) {
        return cuzdanRepository.findByDogrulamaTarihiIsNotNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public long kullaniciCuzdanSayisi(Long kullaniciId) {
        return cuzdanRepository.countByKullanici_Id(kullaniciId);
    }

    @Override
    @Transactional(readOnly = true)
    public long agCuzdanSayisi(String agAdi) {
        return cuzdanRepository.countByAgAdiIgnoreCase(agAdi);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cuzdan> adresVeAgIleBul(String adres, String agAdi) {
        return cuzdanRepository.adresVeAgIleBul(adres, agAdi);
    }

    @Override
    public Cuzdan durumGuncelle(Long cuzdanId, CuzdanDurumu yeniDurum) {
        Cuzdan cuzdan = cuzdanGetir(cuzdanId);
        cuzdan.setDurum(yeniDurum);
        return cuzdanRepository.save(cuzdan);
    }

    // -------------------------------------------------------------------------
    private Cuzdan cuzdanGetir(Long cuzdanId) {
        return cuzdanRepository.findById(cuzdanId)
                .orElseThrow(() -> new EntityNotFoundException("Cüzdan", cuzdanId));
    }
}
