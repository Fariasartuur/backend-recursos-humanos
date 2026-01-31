package com.artuur.hrms.services;

import com.artuur.hrms.dto.CreateUserDTO;
import com.artuur.hrms.dto.UpdateUserDTO;
import com.artuur.hrms.dto.UserResponseDTO;
import com.artuur.hrms.entities.Role;
import com.artuur.hrms.entities.User;
import com.artuur.hrms.repository.RoleRepository;
import com.artuur.hrms.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void newUser(CreateUserDTO dto){

        Set<Role> roles = dto.roles().stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toSet());

        var user = User.builder()
                .username(dto.username())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .roles(roles)
                .build();

        userRepository.save(user);
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User getMyUser(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }

    @Transactional
    public void updateUser(UUID id, UpdateUserDTO dto) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setEmail(dto.email());
        user.setUsername(dto.username());

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UUID id){
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        userRepository.delete(user);
    }


}
