package org.varunmatangi.springsecurity.service;

import org.varunmatangi.springsecurity.dto.UserRequestDTO;
import org.varunmatangi.springsecurity.dto.UserResponseDTO;

import java.util.List;


public interface UserService  {
    List<UserResponseDTO> getAllUsers();

    UserResponseDTO saveUser(UserRequestDTO userRequestDTO);

    void deleteUser(String userId);
}
