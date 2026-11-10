package com.proje.blokzincirbagisplatformu.model;

import com.proje.blokzincirbagisplatformu.model.enums.KampanyaDurumu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kampanyalar")
@Getter
@Setter
@NoArgsConstructor
public class Kampanya {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String baslik;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String aciklama;

    @Column(nullable = false, precision = 36, scale = 18)
    private BigDecimal hedefTutar;

    @Column(nullable = false, length = 10)
    private String paraBirimi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private KampanyaDurumu durum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kurum_id", nullable = false)
    private Kurum kurum;

    @Column(nullable = false)
    private boolean genelBagisMi = false;

    @OneToMany(mappedBy = "kampanya", fetch = FetchType.LAZY)
    private List<Bagis> bagislar = new ArrayList<>();

    @OneToMany(mappedBy = "kampanya", fetch = FetchType.LAZY)
    private List<Harcama> harcamalar = new ArrayList<>();

    private LocalDateTime baslangicTarihi;

    private LocalDateTime bitisTarihi;

    @Column(nullable = false, updatable = false)
    private LocalDateTime olusturmaTarihi;

    @PrePersist
    protected void onCreate() {
        olusturmaTarihi = LocalDateTime.now();

        if (durum == null) {
            durum = KampanyaDurumu.TASLAK;
        }
    }
}