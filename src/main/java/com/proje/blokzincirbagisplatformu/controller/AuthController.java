
package com.proje.blokzincirbagisplatformu.controller;

import com.proje.blokzincirbagisplatformu.service.JwtService;
import com.proje.blokzincirbagisplatformu.security.WalletSignatureService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/wallet")
public class AuthController {

    private final WalletSignatureService walletSignatureService;
    private final JwtService jwtService;

    public AuthController(
            WalletSignatureService walletSignatureService,
            JwtService jwtService
    ) {
        this.walletSignatureService = walletSignatureService;
        this.jwtService = jwtService;
    }

    /**
     * 1. Adım:
     * MetaMask cüzdan adresi için tek kullanımlık nonce üretir.
     */
    @PostMapping("/nonce")
    public ResponseEntity<Map<String, String>> getNonce(
            @Valid @RequestBody WalletNonceRequest request
    ) {

        String nonceMessage =
                walletSignatureService.generateNonce(
                        request.walletAddress()
                );

        return ResponseEntity.ok(
                Map.of("message", nonceMessage)
        );
    }

    /**
     * 2. Adım:
     * MetaMask tarafından imzalanan mesajı doğrular.
     * İmza geçerliyse JWT token üretir.
     */
    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifySignature(
            @Valid @RequestBody WalletVerifyRequest request
    ) {

        boolean isValid = walletSignatureService.verifySignature(
                request.walletAddress(),
                request.signature(),
                request.message()
        );

        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "error",
                            "Geçersiz veya süresi dolmuş cüzdan imzası."
                    ));
        }

        // İmza doğrulandıktan sonra JWT oluşturulur.
        String token = jwtService.generateToken(
                request.walletAddress()
        );

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "walletAddress", request.walletAddress(),
                        "message", "Cüzdan doğrulaması başarılı."
                )
        );
    }

    /**
     * Nonce üretimi için istek modeli.
     */
    public record WalletNonceRequest(

            @NotBlank(message = "Cüzdan adresi boş olamaz.")
            String walletAddress

    ) {
    }

    /**
     * Cüzdan imzası doğrulaması için istek modeli.
     */
    public record WalletVerifyRequest(

            @NotBlank(message = "Cüzdan adresi boş olamaz.")
            String walletAddress,

            @NotBlank(message = "İmza boş olamaz.")
            String signature,

            @NotBlank(message = "İmzalanan mesaj boş olamaz.")
            String message

    ) {
    }
}
