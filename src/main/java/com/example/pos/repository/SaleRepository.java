package com.example.pos.repository;

import com.example.pos.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sales, UUID> {
    List<Sales> findByMarketId(UUID marketId);
}
