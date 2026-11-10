package com.proje.blokzincirbagisplatformu.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "zincir_islemleri", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tx_hash", "log_index"})
})
@Getter
@Setter
@NoArgsConstructor
public class ZincirIslemi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tx_hash", nullable = false)
    private String txHash;

    @Column(name = "chain_id")
    private Long chainId;

    @Column(name = "contract_address")
    private String contractAddress;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "block_number")
    private Long blockNumber;

    @Column(name = "log_index")
    private Integer logIndex;

    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, CONFIRMED, FAILED

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}