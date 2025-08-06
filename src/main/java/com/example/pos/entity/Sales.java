package com.example.pos.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "sales")
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToMany
    private List<Product> products;

    @ManyToOne
    private Market market;

    @ManyToOne
    private User salesPerson;

    private BigDecimal totalPrice;
    private BigDecimal payedPrice;

    private boolean paid;

    private LocalDateTime salesDate;
}
