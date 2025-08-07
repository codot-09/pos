package com.example.pos.repository;

import com.example.pos.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        AND u.market.id = :id
    """)
    List<User> findByRoleAndNameOrPhone(@Param("id") UUID id);


    @Query(value = """
        select u.* from users u where
        (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))) and
        (:phone IS NULL OR LOWER(u.phone) LIKE LOWER(CONCAT('%', :phone, '%'))) and
        (:role IS NULL OR LOWER(u.role) LIKE LOWER(CONCAT('%', :role, '%'))) order by u.id desc
        """, nativeQuery = true)
    Page<User> searchUsers(String name, String phone, String role, Pageable pageable);
}
