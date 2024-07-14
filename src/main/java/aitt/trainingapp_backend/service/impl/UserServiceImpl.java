package aitt.trainingapp_backend.service.impl;

import aitt.trainingapp_backend.dto.UserDto;
import aitt.trainingapp_backend.model.UserModel;
import aitt.trainingapp_backend.repository.UserRepository;
import aitt.trainingapp_backend.service.UserService;
import aitt.trainingapp_backend.util.JwtTokenUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @Override
    public UserDto saveUser(UserDto userDto) {
        UserModel user = new UserModel();
        user.setEmail(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        UserModel savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }
    @Override
    public UserDto findUserById(Long id) {
        UserModel userModel = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mapToDto(userModel);
    }
    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
    @Override
    public UserDto findUserByEmail(String email) {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mapToDto(user);
    }
    @Override
    public String loginUser(UserDto userDto) {
        Optional<UserModel> userOptional = userRepository.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            if (checkPassword(userDto.getPassword(), user.getPassword())) {
                return jwtTokenUtil.generateToken(mapToUserDetails(user));
            }
        }
        throw new UsernameNotFoundException("Invalid email or password");
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mapToUserDetails(userModel);
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