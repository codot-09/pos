package com.example.pos.entity;

import jakarta.persistence.*;
import lombok.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Sales sales;
}
