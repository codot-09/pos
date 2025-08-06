package com.example.pos.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "purchases")
public class ProductPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Double quantity;
    private BigDecimal price;

    @CreationTimestamp
    private LocalDate purchaseDate;

    @ManyToOne
    private Product product;
}
