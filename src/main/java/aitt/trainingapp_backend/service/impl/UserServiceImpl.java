package aitt.trainingapp_backend.service.impl;

import aitt.trainingapp_backend.dto.UserRegisterDto;
import aitt.trainingapp_backend.dto.UserLoginDto;
import aitt.trainingapp_backend.model.Role;
import aitt.trainingapp_backend.model.UserModel;
import aitt.trainingapp_backend.repository.UserRepository;
import aitt.trainingapp_backend.util.JwtTokenUtil;
import aitt.trainingapp_backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public UserRegisterDto saveUser(UserRegisterDto userDTO) {
        log.info("Saving user: {}", userDTO.getEmail());
        UserModel user = new UserModel();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(userDTO.getRoles().stream().map(Role::valueOf).collect(Collectors.toSet()));
        UserModel savedUser = userRepository.save(user);
        log.info("User saved: {}", savedUser.getEmail());
        return mapToDto(savedUser);
    }
    @Override
    public UserRegisterDto findUserById(Long id) {
        log.info("Finding user by id: {}", id);
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found by id: {}", id);
                    return new UsernameNotFoundException("User not found");
                });
        return mapToDto(user);
    }
    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    @Override
    public UserRegisterDto findUserByEmail(String email) {
        log.info("Finding user by email: {}", email);
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found by email: {}", email);
                    return new UsernameNotFoundException("User not found");
                });
        return mapToDto(user);
    }
    @Override
    public String loginUser(UserLoginDto userDTO) {
        log.info("Logging in user: {}", userDTO.getEmail());
        Optional<UserModel> userOptional = userRepository.findByEmail(userDTO.getEmail());
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            if (checkPassword(userDTO.getPassword(), user.getPassword())) {
                UserDetails userDetails = mapToUserDetails(user);
                String token = jwtTokenUtil.generateToken(userDetails);
                log.info("User logged in, token generated: {}", userDTO.getEmail());
                return token;
            }
        }
        log.error("Invalid email or password for user: {}", userDTO.getEmail());
        throw new UsernameNotFoundException("Invalid email or password");
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        UserModel user = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    log.error("User not found by username: {}", username);
                    return new UsernameNotFoundException("User not found");
                });
        return mapToUserDetails(user);
    }
    @Override
    public List<UserRegisterDto> findAllUsers() {
        log.debug("Fetching all users from the database");
        List<UserModel> users = userRepository.findAll();
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }
    private UserRegisterDto mapToDto(UserModel user) {
        UserRegisterDto userDto = new UserRegisterDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles().stream().map(Role::name).collect(Collectors.toSet()));
        return userDto;
    }
    private UserDetails mapToUserDetails(UserModel user) {
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList()))
                .build();
    }
}