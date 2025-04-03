package com.web.finance.startup;

import com.web.finance.model.User;
import com.web.finance.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.boot.ApplicationArguments;

@Component
public class SetAdmin implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SetAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User admin = userRepository.findByEmail("admin@admin.com");
        if(admin == null){
            User adminUser = new User();
            String adminEmail = System.getenv("ADMIN_EMAIL");
            String adminPassword = System.getenv("ADMIN_PASSWORD");
            adminUser.setEmail(adminEmail);
            adminUser.setRole("admin");
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            userRepository.save(adminUser);
        }
    }
}
