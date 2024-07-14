package aitt.trainingapp_backend.service.impl;

import aitt.trainingapp_backend.dto.UserDto;
import aitt.trainingapp_backend.model.UserModel;
import aitt.trainingapp_backend.repository.UserRepository;
import aitt.trainingapp_backend.service.UserService;
import aitt.trainingapp_backend.util.JwtTokenUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @Override
    public UserDto saveUser(UserDto userDTO) {
        UserModel user = new UserModel();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserModel savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }
    @Override
    public UserDto findUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    @Override
    public UserDto findUserByEmail(String email) {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mapToDto(user);
    }
    @Override
    public String loginUser(UserDto userDTO) {
        Optional<UserModel> userOptional = userRepository.findByEmail(userDTO.getEmail());
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            if (checkPassword(userDTO.getPassword(), user.getPassword())) {
                return jwtTokenUtil.generateToken(mapToUserDetails(user));
            }
        }
        throw new UsernameNotFoundException("Invalid email or password");
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mapToUserDetails(user);
    }
    private UserDto mapToDto(UserModel user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
    private UserDetails mapToUserDetails(UserModel user) {
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}