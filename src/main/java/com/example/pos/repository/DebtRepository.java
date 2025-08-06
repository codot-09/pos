package com.example.pos.repository;

import com.example.pos.entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DebtRepository extends JpaRepository<Debt, UUID> {

    @Query("""
            SELECT d FROM Debt d
            WHERE d.sales.market.id = :marketId
            """)
    List<Debt> findByMarket(@Param("marketId") UUID marketId);
}
