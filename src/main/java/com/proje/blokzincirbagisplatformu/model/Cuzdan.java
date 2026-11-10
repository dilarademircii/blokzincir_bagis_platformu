package com.proje.blokzincirbagisplatformu.model;

import com.proje.blokzincirbagisplatformu.model.enums.CuzdanDurumu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cuzdanlar")
@Getter
@Setter
@NoArgsConstructor
public class Cuzdan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 42)
    private String cuzdanAdresi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CuzdanDurumu durum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id", nullable = false)
    private Kullanici kullanici;

    @Column(length = 100)
    private String agAdi;

    @Column(nullable = false, updatable = false)
    private LocalDateTime olusturmaTarihi;

    private LocalDateTime dogrulamaTarihi;

    @PrePersist
    protected void onCreate() {
        olusturmaTarihi = LocalDateTime.now();

        if (durum == null) {
            durum = CuzdanDurumu.DOGRULANMADI;
        }
    }
}