package com.example.pos.component;

import com.example.pos.entity.User;
import com.example.pos.entity.enums.UserRole;
import com.example.pos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;


    @Override
    public void run(String... args) throws Exception {
        if (ddl.equals("create")) {
            User user = User.builder()
                    .name("Admin")
                    .phone("998901234567")
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .role(UserRole.ADMIN)
                    .active(true)
                    .build();

            userRepository.save(user);
        }
    }
}
