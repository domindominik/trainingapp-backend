package aitt.trainingapp_backend.controller;

import aitt.trainingapp_backend.model.UserModel;
import aitt.trainingapp_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserModel> saveUser(@RequestBody UserModel user) {
        UserModel savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody String email, @RequestBody String password) {
        UserModel user = userService.findUserByEmail(email);
        if(user != null && userService.checkPassword(password, user.getPassword())) {
            String token = userService.generateToken(user);
            return ResponseEntity.ok(token);
        } else{
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}
