
package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.dto.request.CuzdanEkleRequest;
import com.proje.blokzincirbagisplatformu.model.Cuzdan;
import com.proje.blokzincirbagisplatformu.model.Kullanici;
import com.proje.blokzincirbagisplatformu.model.enums.CuzdanDurumu;
import com.proje.blokzincirbagisplatformu.repository.CuzdanRepository;
import com.proje.blokzincirbagisplatformu.repository.KullaniciRepository;
import com.proje.blokzincirbagisplatformu.service.CuzdanService;
import com.proje.blokzincirbagisplatformu.service.JwtService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import jakarta.persistence.EntityNotFoundException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CuzdanServiceImpl extends TemelServiceImpl<Cuzdan>
        implements CuzdanService {

    private final CuzdanRepository cuzdanRepository;
    private final KullaniciRepository kullaniciRepository;
    private final JwtService jwtService;

    private final SecureRandom secureRandom = new SecureRandom();

    private static final int NONCE_GECERLILIK_DAKIKASI = 5;

    public CuzdanServiceImpl(
            CuzdanRepository cuzdanRepository,
            KullaniciRepository kullaniciRepository,
            JwtService jwtService
    ) {
        super(cuzdanRepository);
        this.cuzdanRepository = cuzdanRepository;
        this.kullaniciRepository = kullaniciRepository;
        this.jwtService = jwtService;
    }

    @Override
    public Cuzdan cuzdanEkle(
            Long kullaniciId,
            CuzdanEkleRequest request
    ) {

        Kullanici kullanici = kullaniciRepository.findById(kullaniciId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Kullanıcı bulunamadı. Kullanıcı ID: " + kullaniciId
                        )
                );

        String adres = request.cuzdanAdresi().trim();

        if (!adres.matches("^0x[0-9a-fA-F]{40}$")) {
            throw new IllegalArgumentException(
                    "Geçerli bir Ethereum cüzdan adresi girilmelidir."
            );
        }

        if (cuzdanRepository.existsByCuzdanAdresiIgnoreCase(adres)) {
            throw new IllegalArgumentException(
                    "Bu cüzdan adresi daha önce sisteme kaydedilmiş."
            );
        }

        Cuzdan cuzdan = new Cuzdan();

        cuzdan.setCuzdanAdresi(adres);
        cuzdan.setAgAdi(request.agAdi());
        cuzdan.setKullanici(kullanici);
        cuzdan.setDurum(CuzdanDurumu.DOGRULANMADI);

        return cuzdanRepository.save(cuzdan);
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
    public List<Cuzdan> kullaniciVeDurumIleGetir(
            Long kullaniciId,
            CuzdanDurumu durum
    ) {
        return cuzdanRepository.findByKullanici_IdAndDurum(
                kullaniciId,
                durum
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cuzdan> durumIleGetir(
            CuzdanDurumu durum,
            Pageable pageable
    ) {
        return cuzdanRepository.findByDurum(durum, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cuzdan> agAdiIleGetir(
            String agAdi,
            Pageable pageable
    ) {
        return cuzdanRepository.findByAgAdiIgnoreCase(agAdi, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean kullaniciDurumluCuzdaniVarMi(
            Long kullaniciId,
            CuzdanDurumu durum
    ) {
        return cuzdanRepository.existsByKullanici_IdAndDurum(
                kullaniciId,
                durum
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cuzdan> dogrulanmisCuzdanlar(Pageable pageable) {
        return cuzdanRepository.findByDurum(
                CuzdanDurumu.DOGRULANDI,
                pageable
        );
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
    public Optional<Cuzdan> adresVeAgIleBul(
            String adres,
            String agAdi
    ) {
        return cuzdanRepository.adresVeAgIleBul(adres, agAdi);
    }

    @Override
    public Cuzdan durumGuncelle(
            Long cuzdanId,
            CuzdanDurumu yeniDurum
    ) {

        Cuzdan cuzdan = cuzdanGetir(cuzdanId);

        cuzdan.setDurum(yeniDurum);

        if (yeniDurum == CuzdanDurumu.DOGRULANDI) {
            cuzdan.setDogrulamaTarihi(LocalDateTime.now());
        } else {
            cuzdan.setDogrulamaTarihi(null);
        }

        return cuzdanRepository.save(cuzdan);
    }

    @Override
    public String nonceOlustur(String cuzdanAdresi) {

        Cuzdan cuzdan = cuzdanRepository
                .findByCuzdanAdresiIgnoreCase(cuzdanAdresi)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Cüzdan bulunamadı."
                        )
                );

        byte[] rastgeleByte = new byte[16];
        secureRandom.nextBytes(rastgeleByte);

        StringBuilder nonce = new StringBuilder();

        for (byte b : rastgeleByte) {
            nonce.append(String.format("%02x", b));
        }

        cuzdan.setNonce(nonce.toString());
        cuzdan.setNonceOlusturmaTarihi(LocalDateTime.now());

        cuzdanRepository.save(cuzdan);

        return nonce.toString();
    }

    @Override
    public String imzaDogrulaVeTokenUret(
            String cuzdanAdresi,
            String imzalananMesaj,
            String imza
    ) {

        Cuzdan cuzdan = cuzdanRepository
                .findByCuzdanAdresiIgnoreCase(cuzdanAdresi)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Cüzdan bulunamadı."
                        )
                );

        if (cuzdan.getNonce() == null ||
                cuzdan.getNonceOlusturmaTarihi() == null) {
            throw new IllegalArgumentException(
                    "Doğrulama için geçerli nonce bulunamadı."
            );
        }

        if (cuzdan.getNonceOlusturmaTarihi()
                .plusMinutes(NONCE_GECERLILIK_DAKIKASI)
                .isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "Nonce süresi dolmuş. Yeniden nonce oluşturun."
            );
        }

        if (!imzalananMesaj.contains(cuzdan.getNonce())) {
            throw new IllegalArgumentException(
                    "İmzalanan mesaj geçerli nonce içermiyor."
            );
        }

        try {
            byte[] imzaBytes = Numeric.hexStringToByteArray(imza);

            if (imzaBytes.length != 65) {
                throw new IllegalArgumentException(
                        "Geçersiz Ethereum imza uzunluğu."
                );
            }

            byte[] mesajBytes =
                    imzalananMesaj.getBytes(StandardCharsets.UTF_8);

            Sign.SignatureData signatureData = new Sign.SignatureData(
                    imzaBytes[64],
                    new byte[]{
                            imzaBytes[0], imzaBytes[1], imzaBytes[2],
                            imzaBytes[3], imzaBytes[4], imzaBytes[5],
                            imzaBytes[6], imzaBytes[7], imzaBytes[8],
                            imzaBytes[9], imzaBytes[10], imzaBytes[11],
                            imzaBytes[12], imzaBytes[13], imzaBytes[14],
                            imzaBytes[15], imzaBytes[16], imzaBytes[17],
                            imzaBytes[18], imzaBytes[19], imzaBytes[20],
                            imzaBytes[21], imzaBytes[22], imzaBytes[23],
                            imzaBytes[24], imzaBytes[25], imzaBytes[26],
                            imzaBytes[27], imzaBytes[28], imzaBytes[29],
                            imzaBytes[30], imzaBytes[31]
                    },
                    new byte[]{
                            imzaBytes[32], imzaBytes[33], imzaBytes[34],
                            imzaBytes[35], imzaBytes[36], imzaBytes[37],
                            imzaBytes[38], imzaBytes[39], imzaBytes[40],
                            imzaBytes[41], imzaBytes[42], imzaBytes[43],
                            imzaBytes[44], imzaBytes[45], imzaBytes[46],
                            imzaBytes[47], imzaBytes[48], imzaBytes[49],
                            imzaBytes[50], imzaBytes[51], imzaBytes[52],
                            imzaBytes[53], imzaBytes[54], imzaBytes[55],
                            imzaBytes[56], imzaBytes[57], imzaBytes[58],
                            imzaBytes[59], imzaBytes[60], imzaBytes[61],
                            imzaBytes[62], imzaBytes[63]
                    }
            );

            BigInteger publicKey = Sign.signedPrefixedMessageToKey(
                    mesajBytes,
                    signatureData
            );

            String kurtarilanAdres = "0x" + Keys.getAddress(publicKey);

            if (!kurtarilanAdres.equalsIgnoreCase(cuzdan.getCuzdanAdresi())) {
                throw new IllegalArgumentException(
                        "MetaMask imzası cüzdan adresiyle eşleşmiyor."
                );
            }

            cuzdan.setDurum(CuzdanDurumu.DOGRULANDI);
            cuzdan.setDogrulamaTarihi(LocalDateTime.now());

            // Nonce tekrar kullanılamaz.
            cuzdan.setNonce(null);
            cuzdan.setNonceOlusturmaTarihi(null);

            cuzdanRepository.save(cuzdan);

            return jwtService.generateToken(cuzdan.getCuzdanAdresi());

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "MetaMask imzası doğrulanamadı."
            );
        }
    }

    private Cuzdan cuzdanGetir(Long cuzdanId) {
        return cuzdanRepository.findById(cuzdanId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Cüzdan bulunamadı. Cüzdan ID: " + cuzdanId
                        )
                );
    }
}
