package com.example.pos.repository;

import com.example.pos.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByBarcodeAndMarketId(String barcode,UUID marketId);

    List<Product> findByMarketId(UUID marketId);


    @Query(value = """
        select p.* from products p left join categories c on p.category_id = c.id where
        (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%',:name,'%'))) and
        (:category IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%',:category,'%'))) and
        (:barcode IS NULL OR LOWER(p.barcode) LIKE LOWER(CONCAT('%',:barcode,'%'))) and
        (:marketId IS NULL OR p.market_id = :marketId)
        order by p.id desc
        """, nativeQuery = true)
    Page<Product> searchProduct(String category, String name, UUID marketId, String barcode, Pageable pageable);
}
