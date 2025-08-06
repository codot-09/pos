package com.example.pos.mapper;

import com.example.pos.dto.response.DebtResponse;
import com.example.pos.entity.Debt;
import org.springframework.stereotype.Component;

@Component
public class DebtMapper {
    public DebtResponse toResponse(Debt debt){
        return new DebtResponse(
                debt.getDebtor(),
                debt.getContact(),
                debt.getDebtAmount(),
                debt.getSales().getId(),
                debt.getCreatedAt()
        );
    }
}
