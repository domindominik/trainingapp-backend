package aitt.trainingapp_backend.service;

import aitt.trainingapp_backend.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto saveUser(UserDto user);
    UserDto findUserById(Long id);
    boolean checkPassword(String rawPassword, String encodedPassword);
    UserDto findUserByEmail(String email);
    String loginUser(UserDto user);
}
