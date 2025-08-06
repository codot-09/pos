package com.example.pos.entity;

import com.example.pos.entity.enums.UnitsOfMeasure;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String barcode;
    private BigDecimal salesPrice;
    private BigDecimal purchasePrice;

    @Enumerated(EnumType.STRING)
    private UnitsOfMeasure unitsOfMeasure;

    @ManyToOne
    private Category category;
    private String imageUrl;

    private Double quantity;

    @ManyToOne
    private Market market;
}
