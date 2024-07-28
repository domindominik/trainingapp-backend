package aitt.trainingapp_backend.service;

import aitt.trainingapp_backend.dto.UserLoginDto;
import aitt.trainingapp_backend.dto.UserRegisterDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UserService extends UserDetailsService {
    UserRegisterDto saveUser(UserRegisterDto user);
    UserRegisterDto findUserById(Long id);
    boolean checkPassword(String rawPassword, String encodedPassword);
    UserRegisterDto findUserByEmail(String email);
    String loginUser(UserLoginDto user);
    List<UserRegisterDto> findAllUsers();
}
