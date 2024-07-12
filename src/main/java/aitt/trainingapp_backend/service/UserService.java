package aitt.trainingapp_backend.service;

import aitt.trainingapp_backend.model.UserModel;

public interface UserService {
    UserModel saveUser(UserModel user);
    UserModel findUserById(Long id);
    String generateToken(UserModel user);
    boolean checkPassword(String rawPassword, String encodedPassword);
    UserModel findUserByEmail(String email);
}
