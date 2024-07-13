package aitt.trainingapp_backend.service.impl;

import aitt.trainingapp_backend.model.UserModel;
import aitt.trainingapp_backend.repository.UserRepository;
import aitt.trainingapp_backend.service.UserService;
import aitt.trainingapp_backend.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UserModel saveUser(UserModel user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @Override
    public UserModel findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
    @Override
    public UserModel findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    @Override
    public String loginUser(UserModel user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        UserDetails userDetails = loadUserByUsername(user.getEmail());
        return jwtTokenUtil.generateToken(userDetails);
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserModel user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
