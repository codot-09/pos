package com.example.pos.repository;

import com.example.pos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByBarcodeAndMarketId(String barcode,UUID marketId);

    List<Product> findByMarketId(UUID marketId);
}
