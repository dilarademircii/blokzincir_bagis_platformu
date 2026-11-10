package com.proje.blokzincirbagisplatformu.model;

import com.proje.blokzincirbagisplatformu.model.enums.HesapDurumu;
import com.proje.blokzincirbagisplatformu.model.enums.KullaniciRol;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "kullanicilar")
@Getter
@Setter
@NoArgsConstructor
public class Kullanici {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String ad;

    @Column(nullable = false, length = 50)
    private String soyad;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String sifreHash;

    @Column(length = 20)
    private String telefon;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private KullaniciRol rol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private HesapDurumu hesapDurumu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kurum_id")
    private Kurum kurum;

    @Column(nullable = false, updatable = false)
    private LocalDateTime olusturmaTarihi;

    private LocalDateTime guncellemeTarihi;

    @Column(name = "wallet_address", unique = true)
    private String walletAddress;

    @Column(name = "wallet_nonce")
    private String walletNonce;

    // Getter ve Setter'lar
    public String getWalletAddress() { return walletAddress; }
    public void setWalletAddress(String walletAddress) { this.walletAddress = walletAddress; }

    public String getWalletNonce() { return walletNonce; }
    public void setWalletNonce(String walletNonce) { this.walletNonce = walletNonce; }

    @PrePersist
    protected void onCreate() {
        olusturmaTarihi = LocalDateTime.now();

        if (hesapDurumu == null) {
            hesapDurumu = HesapDurumu.AKTIF;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        guncellemeTarihi = LocalDateTime.now();
    }
}