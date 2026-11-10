package com.proje.blokzincirbagisplatformu.model;

import com.proje.blokzincirbagisplatformu.model.enums.HarcamaDurumu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "harcamalar")
@Getter
@Setter
@NoArgsConstructor
public class Harcama {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String baslik;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String aciklama;

    @Column(nullable = false, precision = 36, scale = 18)
    private BigDecimal tutar;

    @Column(nullable = false, length = 10)
    private String paraBirimi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private HarcamaDurumu durum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kampanya_id", nullable = false)
    private Kampanya kampanya;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tedarikci_id")
    private Tedarikci tedarikci;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "olusturan_id", nullable = false)
    private Kullanici olusturan;

    @OneToMany(mappedBy = "harcama", fetch = FetchType.LAZY)
    private List<KanitBelgesi> kanitBelgeleri = new ArrayList<>();

    @OneToMany(mappedBy = "harcama", fetch = FetchType.LAZY)
    private List<HarcamaOnay> onaylar = new ArrayList<>();

    @OneToMany(mappedBy = "harcama", fetch = FetchType.LAZY)
    private List<BlockchainIslem> blockchainIslemleri = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime olusturmaTarihi;

    private LocalDateTime gerceklesmeTarihi;

    @PrePersist
    protected void onCreate() {
        olusturmaTarihi = LocalDateTime.now();

        if (durum == null) {
            durum = HarcamaDurumu.BEKLEMEDE;
        }
    }
}