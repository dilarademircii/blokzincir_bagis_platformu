
package com.proje.blokzincirbagisplatformu.controller;

import com.proje.blokzincirbagisplatformu.dto.request.WalletNonceRequest;
import com.proje.blokzincirbagisplatformu.dto.request.WalletVerifyRequest;
import com.proje.blokzincirbagisplatformu.security.WalletSignatureService;
import com.proje.blokzincirbagisplatformu.service.JwtService;

import jakarta.validation.Valid;

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

    // 1. Adım: Cüzdan için nonce üretir.
    @PostMapping("/nonce")
    public ResponseEntity<Map<String, String>> getNonce(
            @Valid @RequestBody WalletNonceRequest request
    ) {

        try {
            String nonceMessage =
                    walletSignatureService.generateNonce(
                            request.walletAddress()
                    );

            return ResponseEntity.ok(
                    Map.of("message", nonceMessage)
            );

        } catch (IllegalArgumentException | IllegalStateException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 2. Adım: MetaMask imzasını doğrular ve JWT üretir.
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
}
