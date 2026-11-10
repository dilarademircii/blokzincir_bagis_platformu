package com.proje.blokzincirbagisplatformu.model;

import com.proje.blokzincirbagisplatformu.model.enums.BlockchainIslemTuru;
import com.proje.blokzincirbagisplatformu.model.enums.BlockchainIslemDurumu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "blockchain_islemleri")
@Getter
@Setter
@NoArgsConstructor
public class BlockchainIslem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private BlockchainIslemTuru islemTuru;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private BlockchainIslemDurumu durum;

    @Column(unique = true, length = 66)
    private String transactionHash;

    @Column(nullable = false)
    private Long chainId;

    @Column(precision = 36, scale = 18)
    private BigDecimal tutar;

    @Column(length = 42)
    private String gonderenAdres;

    @Column(length = 42)
    private String aliciAdres;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bagis_id")
    private Bagis bagis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "harcama_id")
    private Harcama harcama;

    private Long blokNumarasi;

    @Column(nullable = false, updatable = false)
    private LocalDateTime olusturmaTarihi;

    private LocalDateTime onaylanmaTarihi;

    @PrePersist
    protected void onCreate() {
        olusturmaTarihi = LocalDateTime.now();

        if (durum == null) {
            durum = BlockchainIslemDurumu.BEKLEMEDE;
        }
    }
}