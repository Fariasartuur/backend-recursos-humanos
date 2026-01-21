package com.artuur.hrms.config;

import com.artuur.hrms.entities.Role;
import com.artuur.hrms.entities.User;
import com.artuur.hrms.repository.RoleRepository;
import com.artuur.hrms.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = roleRepository.findByName(Role.Values.ROLE_ADMIN.name());

        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                (user) -> {
                    System.out.println("admin ja exite!");
                },
                () -> {
                    var user = new User();
                    user.setUsername("admin");
                    user.setPassword(passwordEncoder.encode("123"));
                    user.setEmail("admin@gmail.com");
                    user.setRoles(Set.of(roleAdmin));
                    userRepository.save(user);
                }

        );
    }
}
