package org.varunmatangi.springsecurity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.varunmatangi.springsecurity.documents.UserEntity;
import org.varunmatangi.springsecurity.dto.UserRequestDTO;
import org.varunmatangi.springsecurity.dto.UserResponseDTO;
import org.varunmatangi.springsecurity.repository.UserRepo;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService , UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        log.info("UserEntity Service - Getting All Users");
        return userRepo.findAll().stream().map(userEntity -> UserResponseDTO.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .roles(userEntity.getRoles())
                .username(userEntity.getUsername())
                .build()).toList();
    }

    @Override
    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {
        log.info("UserEntity Service - Adding UserEntity");
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userRequestDTO.getFirstName());
        userEntity.setLastName(userRequestDTO.getLastName());
        userEntity.setUsername(userRequestDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        userEntity.setRoles(List.of("USER"));
        UserEntity savedUserEntity = userRepo.save(userEntity);
        log.info("UserEntity Service - Saved UserEntity");
        return UserResponseDTO.builder()
                .id(savedUserEntity.getId())
                .firstName(savedUserEntity.getFirstName())
                .lastName(savedUserEntity.getLastName())
                .roles(savedUserEntity.getRoles())
                .username(savedUserEntity.getUsername())
                .build();
    }

    @Override
    public void deleteUser(String userId) {
        log.info("UserEntity Service - Deleting User with Id {}:", userId);
        userRepo.deleteById(userId);
    }

}
