package aitt.trainingapp_backend.controller;

import aitt.trainingapp_backend.dto.UserRegisterDto;
import aitt.trainingapp_backend.dto.UserLoginDto;
import aitt.trainingapp_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody UserRegisterDto userDto) {
        logger.debug("Registering user with email: {}", userDto.getEmail());
        userService.saveUser(userDto);
        logger.info("User registered successfully with email: {}", userDto.getEmail());
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto userDto) {
        logger.debug("Logging in user with email: {}", userDto.getEmail());
        String token = userService.loginUser(userDto);
        logger.info("User logged in successfully with email: {}", userDto.getEmail());
        return ResponseEntity.ok(token);
    }
}