package com.example.pos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesResponse {
    private UUID id;
    private LocalDateTime salesDate;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private boolean paid;
    private List<ProductResponse> products;
}
