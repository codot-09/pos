package com.example.pos.repository;

import com.example.pos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByPhone(String phone);
    Optional<User> findByPhone(String phone);

    @Query("""
    SELECT u FROM User u
    WHERE u.role = 'SELLER'
    AND (
        :field IS NULL OR
        u.phone = :field OR
        u.name = :field
    )
    AND u.market.id = :id
""")
    List<User> findByRoleAndNameOrPhone(@Param("field") String field,
                                        @Param("id") UUID id);
}
