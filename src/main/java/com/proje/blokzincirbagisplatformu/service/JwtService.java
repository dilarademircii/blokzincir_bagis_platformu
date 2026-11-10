package com.proje.blokzincirbagisplatformu.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Cüzdan adresi için JWT token oluşturur.
     */
    public String generateToken(String walletAddress) {

        Date simdikiZaman = new Date();

        Date bitisZamani = new Date(
                simdikiZaman.getTime() + expiration
        );

        SecretKey key = Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );

        return Jwts.builder()
                .subject(walletAddress)
                .issuedAt(simdikiZaman)
                .expiration(bitisZamani)
                .signWith(key)
                .compact();
    }
}
