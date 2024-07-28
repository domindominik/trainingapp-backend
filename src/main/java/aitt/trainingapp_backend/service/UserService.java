package aitt.trainingapp_backend.service;

import aitt.trainingapp_backend.dto.UserLoginDto;
import aitt.trainingapp_backend.dto.UserRegisterDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserRegisterDto saveUser(UserRegisterDto user);
    UserRegisterDto findUserById(Long id);
    boolean checkPassword(String rawPassword, String encodedPassword);
    UserRegisterDto findUserByEmail(String email);
    String loginUser(UserLoginDto user);
}
