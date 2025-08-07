package com.example.pos.repository;

import com.example.pos.entity.Market;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MarketRepository extends JpaRepository<Market, UUID> {
    Optional<Market> findByOwnerId(UUID ownerId);

    @Query("SELECT u.market FROM User u WHERE u.id = :userId")
    Optional<Market> findMarketBySeller(@Param("userId") UUID userId);

    @Query(value = """
        SELECT m.* FROM markets m WHERE
                (:name IS NULL OR (LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%')))) ORDER BY  m.id DESC
        """, nativeQuery = true)
    Page<Market> searchByName(String name, Pageable pageable);
}
