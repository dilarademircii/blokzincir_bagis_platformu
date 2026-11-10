package com.proje.blokzincirbagisplatformu.model;

import com.proje.blokzincirbagisplatformu.model.enums.BelgeTuru;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "kanit_belgeleri")
@Getter
@Setter
@NoArgsConstructor
public class KanitBelgesi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String dosyaAdi;

    @Column(nullable = false, length = 500)
    private String dosyaYolu;

    @Column(nullable = false, length = 64)
    private String dosyaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private BelgeTuru belgeTuru;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "harcama_id", nullable = false)
    private Harcama harcama;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yukleyen_id", nullable = false)
    private Kullanici yukleyen;

    @Column(nullable = false, updatable = false)
    private LocalDateTime yuklenmeTarihi;

    @PrePersist
    protected void onCreate() {
        yuklenmeTarihi = LocalDateTime.now();
    }
}