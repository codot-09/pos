package com.example.pos.repository;

import com.example.pos.entity.ProductPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductPurchaseRepository extends JpaRepository<ProductPurchase, UUID> {

    @Query("""
        SELECT pp FROM ProductPurchase pp
        WHERE pp.product.market.id = :id
    """)
    List<ProductPurchase> findByMarket(@Param("id") UUID marketId);

    List<ProductPurchase> findByProductId(UUID productId);
}
