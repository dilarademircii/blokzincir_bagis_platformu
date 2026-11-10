package com.proje.blokzincirbagisplatformu.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kurumlar")
@Getter
@Setter
@NoArgsConstructor
public class Kurum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String kurumAdi;

    @Column(nullable = false, unique = true, length = 20)
    private String vergiNumarasi;

    @Column(length = 500)
    private String adres;

    @Column(length = 20)
    private String telefon;

    @Column(length = 100)
    private String email;

    @Column(length = 500)
    private String aciklama;

    @Column(nullable = false)
    private Boolean aktif = true;

    @OneToMany(mappedBy = "kurum", fetch = FetchType.LAZY)
    private List<Kullanici> kullanicilar = new ArrayList<>();

    @OneToMany(mappedBy = "kurum", fetch = FetchType.LAZY)
    private List<Kampanya> kampanyalar = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime olusturmaTarihi;

    @PrePersist
    protected void onCreate() {
        olusturmaTarihi = LocalDateTime.now();
    }
}