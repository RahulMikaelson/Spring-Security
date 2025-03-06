package org.varunmatangi.springsecurity.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.varunmatangi.springsecurity.documents.UserEntity;
import org.varunmatangi.springsecurity.repository.UserRepo;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitSetupConfig implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Override
    public void run(String... args) throws Exception {
        adminSetup();
    }

    private void adminSetup() {
        if (userRepo.findByUsername("admin") == null) {
            UserEntity user = new UserEntity();
            user.setFirstName("Admin");
            user.setLastName("Admin");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("secret"));
            user.setRoles(List.of("SUPER_ADMIN", "ADMIN", "USER"));
            userRepo.save(user);
            log.info("Successfully added Admin user");
        } else {
            log.info("Admin user already exists");
        }
    }


}
