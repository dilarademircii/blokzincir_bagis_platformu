package com.proje.blokzincirbagisplatformu.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "blockchain_eventleri",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_event_islem",
                        columnNames = {"chain_id", "transaction_hash", "log_index"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class BlockchainEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chain_id", nullable = false)
    private Long chainId;

    @Column(name = "transaction_hash", nullable = false, length = 66)
    private String transactionHash;

    @Column(name = "log_index", nullable = false)
    private Long logIndex;

    @Column(nullable = false, length = 100)
    private String eventAdi;

    @Column(nullable = false)
    private Long blokNumarasi;

    @Column(columnDefinition = "TEXT")
    private String eventVerisi;

    @Column(nullable = false)
    private Boolean islendi = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blockchain_islem_id")
    private BlockchainIslem blockchainIslem;

    @Column(nullable = false, updatable = false)
    private LocalDateTime olusturmaTarihi;

    private LocalDateTime islenmeTarihi;

    @PrePersist
    protected void onCreate() {
        olusturmaTarihi = LocalDateTime.now();
    }
}
