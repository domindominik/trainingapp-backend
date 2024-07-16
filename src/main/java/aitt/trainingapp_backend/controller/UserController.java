package aitt.trainingapp_backend.controller;

import aitt.trainingapp_backend.dto.UserDto;
import aitt.trainingapp_backend.model.UserModel;
import aitt.trainingapp_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) {
        //return ResponseEntity.ok(userService.saveUser(user));
        userService.saveUser(userDto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto user) {
            return ResponseEntity.ok(userService.loginUser(user));
        }
}
