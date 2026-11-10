package com.proje.blokzincirbagisplatformu.model;

import com.proje.blokzincirbagisplatformu.model.enums.BagisDurumu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bagislar")
@Getter
@Setter
@NoArgsConstructor
public class Bagis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 36, scale = 18)
    private BigDecimal tutar;

    @Column(nullable = false, length = 10)
    private String paraBirimi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private BagisDurumu durum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kampanya_id", nullable = false)
    private Kampanya kampanya;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bagisci_id", nullable = false)
    private Kullanici bagisci;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuzdan_id")
    private Cuzdan cuzdan;

    @OneToMany(mappedBy = "bagis", fetch = FetchType.LAZY)
    private List<BlockchainIslem> blockchainIslemleri = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime olusturmaTarihi;

    @PrePersist
    protected void onCreate() {
        olusturmaTarihi = LocalDateTime.now();

        if (durum == null) {
            durum = BagisDurumu.BEKLEMEDE;
        }
    }
}