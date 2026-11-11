
package com.proje.blokzincirbagisplatformu.security;

import com.proje.blokzincirbagisplatformu.model.Cuzdan;
import com.proje.blokzincirbagisplatformu.model.enums.CuzdanDurumu;
import com.proje.blokzincirbagisplatformu.repository.CuzdanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WalletSignatureService {

    private final CuzdanRepository cuzdanRepository;

    private static final int NONCE_GECERLILIK_DAKIKASI = 5;

    public WalletSignatureService(CuzdanRepository cuzdanRepository) {
        this.cuzdanRepository = cuzdanRepository;
    }

    // Cüzdan için tek kullanımlık nonce üretir.
    @Transactional
    public String generateNonce(String walletAddress) {

        Cuzdan cuzdan = cuzdanRepository
                .findByCuzdanAdresiIgnoreCase(walletAddress)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Bu cüzdan sistemde kayıtlı değil."
                        )
                );

        if (cuzdan.getDurum() == CuzdanDurumu.IPTAL) {
            throw new IllegalStateException(
                    "İptal edilmiş cüzdan doğrulanamaz."
            );
        }

        String nonce = "Blokzincir Bagis Platformu Giris Nonce: "
                + UUID.randomUUID();

        cuzdan.setNonce(nonce);
        cuzdan.setNonceOlusturmaTarihi(LocalDateTime.now());

        cuzdanRepository.save(cuzdan);

        return nonce;
    }

    // MetaMask imzasını kontrol eder ve cüzdanı doğrular.
    @Transactional
    public boolean verifySignature(
            String walletAddress,
            String signature,
            String originalMessage
    ) {

        try {

            Cuzdan cuzdan = cuzdanRepository
                    .findByCuzdanAdresiIgnoreCase(walletAddress)
                    .orElse(null);

            if (cuzdan == null) {
                return false;
            }

            // İptal edilmiş cüzdan doğrulanamaz.
            if (cuzdan.getDurum() == CuzdanDurumu.IPTAL) {
                return false;
            }

            // Daha önce oluşturulmuş nonce kontrolü.
            if (cuzdan.getNonce() == null
                    || cuzdan.getNonceOlusturmaTarihi() == null) {
                return false;
            }

            // Gönderilen mesaj kayıtlı nonce ile aynı olmalı.
            if (!cuzdan.getNonce().equals(originalMessage)) {
                return false;
            }

            // Nonce 5 dakika içerisinde kullanılmalı.
            LocalDateTime simdi = LocalDateTime.now();

            long gecenDakika = Duration.between(
                    cuzdan.getNonceOlusturmaTarihi(),
                    simdi
            ).toMinutes();

            if (gecenDakika >= NONCE_GECERLILIK_DAKIKASI
                    || gecenDakika < 0) {
                cuzdan.setNonce(null);
                cuzdan.setNonceOlusturmaTarihi(null);
                cuzdanRepository.save(cuzdan);
                return false;
            }

            // Ethereum adresini imzadan kurtar.
            byte[] messageBytes = originalMessage.getBytes(
                    StandardCharsets.UTF_8
            );

            Sign.SignatureData signatureData = parseSignature(signature);

            var recoveredPublicKey =
                    Sign.signedPrefixedMessageToKey(
                            messageBytes,
                            signatureData
                    );

            String recoveredAddress =
                    "0x" + Keys.getAddress(recoveredPublicKey);

            // Kurtarılan adres ile kayıtlı adres eşleşmeli.
            if (!recoveredAddress.equalsIgnoreCase(
                    cuzdan.getCuzdanAdresi()
            )) {
                return false;
            }

            // İmza doğruysa cüzdanı doğrula.
            cuzdan.setDurum(CuzdanDurumu.DOGRULANDI);
            cuzdan.setDogrulamaTarihi(simdi);

            // Nonce tek kullanımlık olduğu için silinir.
            cuzdan.setNonce(null);
            cuzdan.setNonceOlusturmaTarihi(null);

            cuzdanRepository.save(cuzdan);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // MetaMask imzasını R, S ve V parçalarına ayırır.
    private Sign.SignatureData parseSignature(String signatureHex) {

        byte[] signatureBytes =
                Numeric.hexStringToByteArray(signatureHex);

        if (signatureBytes.length != 65) {
            throw new IllegalArgumentException(
                    "Geçersiz imza uzunluğu."
            );
        }

        byte v = signatureBytes[64];

        if (v == 0 || v == 1) {
            v += 27;
        }

        if (v != 27 && v != 28) {
            throw new IllegalArgumentException(
                    "Geçersiz imza doğrulama değeri."
            );
        }

        byte[] r = new byte[32];
        byte[] s = new byte[32];

        System.arraycopy(signatureBytes, 0, r, 0, 32);
        System.arraycopy(signatureBytes, 32, s, 0, 32);

        return new Sign.SignatureData(v, r, s);
    }
}
