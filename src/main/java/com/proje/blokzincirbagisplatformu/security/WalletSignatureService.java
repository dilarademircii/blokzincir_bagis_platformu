package com.proje.blokzincirbagisplatformu.security;

import com.proje.blokzincirbagisplatformu.model.Kullanici;
import com.proje.blokzincirbagisplatformu.repository.KullaniciRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.SignatureException;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletSignatureService {

    private final KullaniciRepository kullaniciRepository;

    public WalletSignatureService(KullaniciRepository kullaniciRepository) {
        this.kullaniciRepository = kullaniciRepository;
    }

    /**
     * Kullanıcı için tek kullanımlık rastgele bir nonce üretir ve kaydeder.
     */
    @Transactional
    public String generateNonce(String walletAddress) {
        String nonce = "Blokzincir Bagis Platformu Giris Nonce: " + UUID.randomUUID().toString();

        Kullanici kullanici = kullaniciRepository.findByWalletAddress(walletAddress)
                .orElseGet(() -> {
                    // Eğer cüzdan ilk kez giriyorsa taslak bir kullanıcı oluşturabiliriz
                    Kullanici yeniKullanici = new Kullanici();
                    yeniKullanici.setWalletAddress(walletAddress);
                    return yeniKullanici;
                });

        kullanici.setWalletNonce(nonce);
        kullaniciRepository.save(kullanici);

        return nonce;
    }

    /**
     * MetaMask ile imzalanan mesajı doğrular ve cüzdan adresinin sahibini teyit eder.
     */
    public boolean verifySignature(String walletAddress, String signature, String originalMessage) {
        try {
            byte[] messageHash = getEthereumMessageHash(originalMessage);
            Sign.SignatureData signatureData = parseSignature(signature);

            // İmzadan public key'i ve oradan Ethereum adresini kurtarıyoruz (recovery)
            BigInteger recoveredPublicKey = Sign.signedMessageToKey(messageHash, signatureData);
            String recoveredAddress = "0x" + Keys.getAddress(recoveredPublicKey);

            // Kurtarılan adres ile istek atan adres eşleşiyor mu kontrol et
            return recoveredAddress.equalsIgnoreCase(walletAddress);

        } catch (Exception e) {
            return false;
        }
    }

    private byte[] getEthereumMessageHash(String message) {
        String prefix = "\u0019Ethereum Signed Message:\n" + message.length();
        return org.web3j.crypto.Hash.sha3((prefix + message).getBytes());
    }

    private Sign.SignatureData parseSignature(String signatureHex) {
        byte[] signatureBytes = Numeric.hexStringToByteArray(signatureHex);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }
        byte[] r = new byte[32];
        byte[] s = new byte[32];
        System.arraycopy(signatureBytes, 0, r, 0, 32);
        System.arraycopy(signatureBytes, 32, s, 0, 32);
        return new Sign.SignatureData(v, r, s);
    }
}