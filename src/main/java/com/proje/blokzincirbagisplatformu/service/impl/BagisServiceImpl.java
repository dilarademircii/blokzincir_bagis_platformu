package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.dto.request.BagisRequest;
import com.proje.blokzincirbagisplatformu.model.Bagis;
import com.proje.blokzincirbagisplatformu.model.BlockchainIslem;
import com.proje.blokzincirbagisplatformu.model.Cuzdan;
import com.proje.blokzincirbagisplatformu.model.Kampanya;
import com.proje.blokzincirbagisplatformu.model.Kullanici;
import com.proje.blokzincirbagisplatformu.model.enums.BagisDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.BlockchainIslemDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.BlockchainIslemTuru;
import com.proje.blokzincirbagisplatformu.model.enums.CuzdanDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.HesapDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.KampanyaDurumu;
import com.proje.blokzincirbagisplatformu.repository.*;
import com.proje.blokzincirbagisplatformu.service.BagisService;
import com.proje.blokzincirbagisplatformu.service.BagisSozlesmesiService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class BagisServiceImpl extends TemelServiceImpl<Bagis>
        implements BagisService {

    private final BagisRepository bagisRepository;
    private final KampanyaRepository kampanyaRepository;
    private final KullaniciRepository kullaniciRepository;
    private final CuzdanRepository cuzdanRepository;

    @Autowired
    private BagisSozlesmesiService bagisSozlesmesiService;

    @Autowired
    private BlockchainIslemRepository blockchainIslemRepository;

    @Autowired
    private Web3j web3j;

    public BagisServiceImpl(
            BagisRepository bagisRepository,
            KampanyaRepository kampanyaRepository,
            KullaniciRepository kullaniciRepository,
            CuzdanRepository cuzdanRepository
    ) {
        super(bagisRepository);
        this.bagisRepository = bagisRepository;
        this.kampanyaRepository = kampanyaRepository;
        this.kullaniciRepository = kullaniciRepository;
        this.cuzdanRepository = cuzdanRepository;
    }

    @Override
    public Bagis bagisOlustur(String cuzdanAdresi, BagisRequest request) {

        Cuzdan cuzdan = cuzdanRepository
                .findByCuzdanAdresiIgnoreCase(cuzdanAdresi)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cüzdan bulunamadı."
                ));

        if (!cuzdan.getId().equals(request.cuzdanId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Bu cüzdan adına bağış yapamazsınız."
            );
        }

        if (cuzdan.getDurum() != CuzdanDurumu.DOGRULANDI) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Bağış yapmak için doğrulanmış cüzdan gereklidir."
            );
        }

        Kullanici bagisci = kullaniciRepository
                .findById(cuzdan.getKullanici().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Bağışçı bulunamadı."
                ));

        if (bagisci.getHesapDurumu() != HesapDurumu.AKTIF) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Aktif olmayan hesaplar bağış yapamaz."
            );
        }

        Kampanya kampanya = kampanyaRepository
                .findById(request.kampanyaId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Kampanya bulunamadı."
                ));

        if (kampanya.getDurum() != KampanyaDurumu.AKTIF) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Bu kampanya bağış kabul etmiyor."
            );
        }

        LocalDateTime simdi = LocalDateTime.now();

        if (kampanya.getBaslangicTarihi() != null
                && kampanya.getBaslangicTarihi().isAfter(simdi)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Kampanya henüz başlamadı."
            );
        }

        if (kampanya.getBitisTarihi() != null
                && kampanya.getBitisTarihi().isBefore(simdi)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Kampanyanın bağış süresi sona erdi."
            );
        }

        if (request.paraBirimi() != null
                && !request.paraBirimi().equalsIgnoreCase(
                kampanya.getParaBirimi())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Bağış para birimi kampanya para birimiyle aynı olmalıdır."
            );
        }

        Bagis bagis = new Bagis();
        bagis.setTutar(request.tutar());
        bagis.setParaBirimi(kampanya.getParaBirimi());
        bagis.setKampanya(kampanya);
        bagis.setBagisci(bagisci);
        bagis.setCuzdan(cuzdan);
        bagis.setDurum(BagisDurumu.BEKLEMEDE);

        return bagisRepository.save(bagis);
    }

    @Override
    public Bagis blockchainBagisiniDogrula(
            Long bagisId,
            String transactionHash,
            String cuzdanAdresi
    ) {

        try {
            Bagis bagis = bagisRepository.findById(bagisId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Bağış bulunamadı."
                    ));

            if (bagis.getDurum() != BagisDurumu.BEKLEMEDE) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Bağış doğrulama için beklemede olmalıdır."
                );
            }

            if (bagis.getCuzdan() == null
                    || !bagis.getCuzdan().getCuzdanAdresi()
                    .equalsIgnoreCase(cuzdanAdresi)) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Cüzdan adresi bağış kaydıyla eşleşmiyor."
                );
            }

            if (blockchainIslemRepository
                    .existsByTransactionHash(transactionHash)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Bu transaction hash daha önce kullanılmış."
                );
            }

            if (!"ETH".equalsIgnoreCase(bagis.getParaBirimi())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Blockchain bağışı için para birimi ETH olmalıdır."
                );
            }

            BigInteger agId = bagisSozlesmesiService.agIdGetir();

            if (!agId.equals(BigInteger.valueOf(11155111))) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Bağlantı Sepolia ağına ait değil."
                );
            }

            TransactionReceipt makbuz =
                    bagisSozlesmesiService.islemMakbuzuGetir(
                            transactionHash
                    );

            if (makbuz == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Blockchain işlemi henüz bulunamadı."
                );
            }

            if (!"0x1".equalsIgnoreCase(makbuz.getStatus())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Blockchain işlemi başarısız."
                );
            }

            Transaction islem = web3j
                    .ethGetTransactionByHash(transactionHash)
                    .send()
                    .getTransaction()
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Blockchain işlemi bulunamadı."
                    ));

            String sozlesmeAdresi =
                    bagisSozlesmesiService.getContractAddress();

            if (islem.getTo() == null
                    || !islem.getTo().equalsIgnoreCase(sozlesmeAdresi)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "İşlem doğru akıllı sözleşmeye gönderilmemiş."
                );
            }

            if (!islem.getFrom().equalsIgnoreCase(cuzdanAdresi)) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "İşlemi gönderen cüzdan eşleşmiyor."
                );
            }

            BigInteger kampanyaId =
                    BigInteger.valueOf(bagis.getKampanya().getId());

            Function fonksiyon = new Function(
                    "bagisYap",
                    Arrays.asList(new Uint256(kampanyaId)),
                    Collections.emptyList()
            );

            String beklenenVeri = FunctionEncoder.encode(fonksiyon);

            if (!beklenenVeri.equalsIgnoreCase(islem.getInput())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "İşlemdeki kampanya bilgisi eşleşmiyor."
                );
            }

            BigInteger islemDegeri = islem.getValue();

            if (islemDegeri == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Blockchain işlem tutarı okunamadı."
                );
            }

            BigInteger gonderilenWei = islemDegeri;
            BigInteger beklenenWei = bagis.getTutar()
                    .multiply(new BigDecimal("1000000000000000000"))
                    .toBigIntegerExact();

            if (!gonderilenWei.equals(beklenenWei)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Blockchain işlemindeki bağış tutarı eşleşmiyor."
                );
            }

            BlockchainIslem blockchainIslem = new BlockchainIslem();

            blockchainIslem.setIslemTuru(BlockchainIslemTuru.BAGIS);
            blockchainIslem.setDurum(BlockchainIslemDurumu.ONAYLANDI);
            blockchainIslem.setTransactionHash(transactionHash);
            blockchainIslem.setChainId(agId.longValue());
            blockchainIslem.setTutar(bagis.getTutar());
            blockchainIslem.setGonderenAdres(islem.getFrom());
            blockchainIslem.setAliciAdres(islem.getTo());
            blockchainIslem.setBagis(bagis);
            blockchainIslem.setBlokNumarasi(
                    makbuz.getBlockNumber().longValue()
            );
            blockchainIslem.setOnaylanmaTarihi(LocalDateTime.now());

            blockchainIslemRepository.save(blockchainIslem);

            bagis.setDurum(BagisDurumu.BASARILI);

            return bagisRepository.save(bagis);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Blockchain bağış doğrulama hatası: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bagis> bagisciIdIleGetir(
            Long bagisciId, Pageable pageable) {
        return bagisRepository.findByBagisci_Id(bagisciId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bagis> kampanyaIdIleGetir(
            Long kampanyaId, Pageable pageable) {
        return bagisRepository.findByKampanya_Id(kampanyaId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bagis> durumaGoreGetir(
            BagisDurumu durum, Pageable pageable) {
        return bagisRepository.findByDurum(durum, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bagis> bagisciVeDurumaGoreGetir(
            Long bagisciId, BagisDurumu durum, Pageable pageable) {
        return bagisRepository.findByBagisci_IdAndDurum(
                bagisciId, durum, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bagis> kampanyaVeDurumaGoreGetir(
            Long kampanyaId, BagisDurumu durum) {
        return bagisRepository.findByKampanya_IdAndDurum(
                kampanyaId, durum);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bagis> cuzdanIdIleGetir(
            Long cuzdanId, Pageable pageable) {
        return bagisRepository.findByCuzdan_Id(cuzdanId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bagis> tarihAraligiIleGetir(
            LocalDateTime baslangic,
            LocalDateTime bitis,
            Pageable pageable) {
        return bagisRepository.findByOlusturmaTarihiBetween(
                baslangic, bitis, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal bagisciToplamTutarGetir(
            Long bagisciId, BagisDurumu durum) {
        return bagisRepository.toplamBagisTutariGetir(
                bagisciId, durum);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal kampanyaToplamTutarGetir(
            Long kampanyaId, BagisDurumu durum) {
        return bagisRepository.kampanyaBagisTutariGetir(
                kampanyaId, durum);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean blockchainIslemiVarMi(Long bagisId) {
        bagisGetir(bagisId);
        return bagisRepository.blockchainIslemiVarMi(bagisId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean bagisVarMi(Long bagisciId, Long kampanyaId) {
        return bagisRepository.existsByBagisci_IdAndKampanya_Id(
                bagisciId, kampanyaId);
    }

    @Override
    @Transactional(readOnly = true)
    public long kampanyaBagisSayisi(Long kampanyaId) {
        return bagisRepository.countByKampanya_Id(kampanyaId);
    }

    @Override
    @Transactional(readOnly = true)
    public long bagisciBagisSayisi(Long bagisciId) {
        return bagisRepository.countByBagisci_Id(bagisciId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bagis> cuzdanAdresiIleBagiscininBagislariniGetir(
            String cuzdanAdresi,
            Pageable pageable) {

        Cuzdan cuzdan = cuzdanRepository
                .findByCuzdanAdresiIgnoreCase(cuzdanAdresi)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cüzdan bulunamadı."
                ));

        return bagisRepository.findByBagisci_Id(
                cuzdan.getKullanici().getId(),
                pageable
        );
    }

    private Bagis bagisGetir(Long bagisId) {
        return bagisRepository.findById(bagisId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Bağış bulunamadı. ID: " + bagisId
                ));
    }
}