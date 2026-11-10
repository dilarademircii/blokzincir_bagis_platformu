package com.proje.blokzincirbagisplatformu.model;

import com.proje.blokzincirbagisplatformu.model.enums.OnayKarari;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "harcama_onaylari")
@Getter
@Setter
@NoArgsConstructor
public class HarcamaOnay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "harcama_id", nullable = false)
    private Harcama harcama;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onaylayan_id", nullable = false)
    private Kullanici onaylayan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OnayKarari karar;

    @Column(length = 1000)
    private String aciklama;

    @Column(nullable = false, updatable = false)
    private LocalDateTime onayTarihi;

    @PrePersist
    protected void onCreate() {
        onayTarihi = LocalDateTime.now();
    }
}