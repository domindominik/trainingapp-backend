package aitt.trainingapp_backend.service.impl;

import aitt.trainingapp_backend.dto.UserRegisterDto;
import aitt.trainingapp_backend.dto.UserLoginDto;
import aitt.trainingapp_backend.model.Role;
import aitt.trainingapp_backend.model.UserModel;
import aitt.trainingapp_backend.repository.UserRepository;
import aitt.trainingapp_backend.util.JwtTokenUtil;
import aitt.trainingapp_backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public UserRegisterDto saveUser(UserRegisterDto userDTO) {
        logger.debug("Saving user with email: {}", userDTO.getEmail());
        UserModel user = new UserModel();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(userDTO.getRoles().stream().map(Role::valueOf).collect(Collectors.toSet()));
        UserModel savedUser = userRepository.save(user);
        logger.info("User saved successfully with email: {}", userDTO.getEmail());
        return mapToDto(savedUser);
    }
    @Override
    public UserRegisterDto findUserById(Long id) {
        logger.debug("Finding user by ID: {}", id);
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        logger.info("User found by ID: {}", id);
        return mapToDto(user);
    }
    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        logger.debug("Checking password");
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        logger.info("Password check result: {}", matches);
        return matches;
    }
    @Override
    public UserRegisterDto findUserByEmail(String email) {
        logger.debug("Finding user by email: {}", email);
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        logger.info("User found by email: {}", email);
        return mapToDto(user);
    }
    @Override
    public String loginUser(UserLoginDto userDTO) {
        logger.debug("Logging in user with email: {}", userDTO.getEmail());
        Optional<UserModel> userOptional = userRepository.findByEmail(userDTO.getEmail());
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            if (checkPassword(userDTO.getPassword(), user.getPassword())) {
                String token = jwtTokenUtil.generateToken(mapToUserDetails(user));
                logger.info("User logged in successfully with email: {}", userDTO.getEmail());
                return token;
            }
        }
        logger.error("Invalid email or password for user: {}", userDTO.getEmail());
        throw new UsernameNotFoundException("Invalid email or password");
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Loading user by username: {}", username);
        UserModel user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        logger.info("User loaded by username: {}", username);
        return mapToUserDetails(user);
    }
    @Override
    public List<UserRegisterDto> findAllUsers() {
        logger.debug("Fetching all users from the database");
        List<UserModel> users = userRepository.findAll();
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }
    private UserRegisterDto mapToDto(UserModel user) {
        logger.debug("Mapping UserModel to UserRegisterDto for user: {}", user.getEmail());
        UserRegisterDto userDto = new UserRegisterDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles().stream().map(Role::name).collect(Collectors.toSet()));
        logger.info("UserModel mapped to UserRegisterDto for user: {}", user.getEmail());
        return userDto;
    }
    private UserDetails mapToUserDetails(UserModel user) {
        logger.debug("Mapping UserModel to UserDetails for user: {}", user.getEmail());
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toList()))
                .build();
        logger.info("UserModel mapped to UserDetails for user: {}", user.getEmail());
        return userDetails;
    }
}