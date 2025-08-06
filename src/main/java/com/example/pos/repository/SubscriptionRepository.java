package com.example.pos.repository;

import com.example.pos.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    @Query("SELECT s FROM Subscription s WHERE s.expireDate = :date")
    List<Subscription> findExpiredSubscriptions(@Param("date") LocalDate date);

    Optional<Subscription> findByUserId(UUID userId);
}
