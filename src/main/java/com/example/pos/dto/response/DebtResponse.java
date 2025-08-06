package com.example.pos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DebtResponse {
    private String debtorName;
    private String contact;
    private BigDecimal amount;
    private UUID salesId;
    private LocalDateTime debtDate;
}
