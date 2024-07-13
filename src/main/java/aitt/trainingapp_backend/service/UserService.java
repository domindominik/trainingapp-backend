package aitt.trainingapp_backend.service;

import aitt.trainingapp_backend.model.UserModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserModel saveUser(UserModel user);
    //UserModel findUserById(Long id);
    //boolean checkPassword(String rawPassword, String encodedPassword);
    //UserModel findUserByEmail(String email);
    String loginUser(UserModel user);
}
