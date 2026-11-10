package com.proje.blokzincirbagisplatformu.model;

import com.proje.blokzincirbagisplatformu.model.enums.BildirimTuru;
import com.proje.blokzincirbagisplatformu.model.enums.BildirimDurumu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "bildirimler")
@Getter
@Setter
@NoArgsConstructor
public class Bildirim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String baslik;

    @Column(nullable = false, length = 1000)
    private String mesaj;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private BildirimTuru bildirimTuru;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BildirimDurumu durum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id", nullable = false)
    private Kullanici kullanici;

    @Column(nullable = false, updatable = false)
    private LocalDateTime olusturmaTarihi;

    private LocalDateTime okunmaTarihi;

    @PrePersist
    protected void onCreate() {
        olusturmaTarihi = LocalDateTime.now();

        if (durum == null) {
            durum = BildirimDurumu.OKUNMADI;
        }
    }
}