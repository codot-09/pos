package com.example.pos.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "debts")
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String debtor;
    private String contact;

    private BigDecimal debtAmount;

    @ManyToOne
    private Sales sales;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
