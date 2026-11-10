package com.proje.blokzincirbagisplatformu.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "denetim_kayitlari")
@Getter
@Setter
@NoArgsConstructor
public class DenetimKaydi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id")
    private Kullanici kullanici;

    @Column(nullable = false, length = 100)
    private String islemTuru;

    @Column(nullable = false, length = 100)
    private String varlikTuru;

    private Long varlikId;

    @Column(length = 45)
    private String ipAdresi;

    @Column(columnDefinition = "TEXT")
    private String eskiDeger;

    @Column(columnDefinition = "TEXT")
    private String yeniDeger;

    @Column(nullable = false, updatable = false)
    private LocalDateTime islemTarihi;

    @PrePersist
    protected void onCreate() {
        islemTarihi = LocalDateTime.now();
    }
}