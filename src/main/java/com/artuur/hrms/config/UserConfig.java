package com.artuur.hrms.config;

import com.artuur.hrms.entities.Role;
import com.artuur.hrms.entities.User;
import com.artuur.hrms.repository.RoleRepository;
import com.artuur.hrms.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class UserConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserConfig(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = roleRepository.findByName(Role.Values.ROLE_ADMIN.name());
        var roleManager = roleRepository.findByName(Role.Values.ROLE_MANAGER.name());
        var roleEmployee = roleRepository.findByName(Role.Values.ROLE_EMPLOYEE.name());

        var userAdmin = userRepository.findByUsername("Artuur");

        userAdmin.ifPresentOrElse(
                (user) -> {
                    System.out.println("Artuur ja exite!");
                },
                () -> {
                    var user = new User();
                    user.setUsername("Artuur");
                    user.setPassword(passwordEncoder.encode("123456"));
                    user.setEmail("arturfarias49@gmail.com");
                    user.setRoles(Set.of(roleAdmin, roleManager, roleEmployee));
                    userRepository.save(user);
                }

        );
    }
}
