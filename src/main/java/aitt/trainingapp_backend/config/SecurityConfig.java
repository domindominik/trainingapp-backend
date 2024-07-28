package aitt.trainingapp_backend.config;

import aitt.trainingapp_backend.filter.JwtAuthenticationFilter;
import aitt.trainingapp_backend.service.CustomUserDetailsService;
import aitt.trainingapp_backend.service.RoleHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private RoleHierarchyService roleHierarchyService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring security filter chain");
        http
                .csrf(csrf -> {
                    logger.debug("Disabling CSRF protection");
                    csrf.disable();
                })
                .authorizeHttpRequests(auth -> {
                    logger.debug("Configuring authorization rules");
                    auth
                            .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                            .requestMatchers("/api/users/**").hasRole("ADMIN")  // Only ADMIN can access endpoints under /api/users/*
                            .anyRequest().authenticated();
                })
                .sessionManagement(session -> {
                    logger.debug("Setting session management policy to STATELESS");
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        logger.info("Added JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter");
        return http.build();
    }

    /*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtFilter(jwtProvider, jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(entryPoint));

        return http.build();
    }

     */
    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        logger.info("Creating CustomUserDetailsService bean");
        return customUserDetailsService;
    }
    @Bean
    public RoleHierarchyService roleHierarchyService() {
        logger.info("Creating RoleHierarchyService bean");
        return roleHierarchyService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Creating PasswordEncoder bean");
        return new BCryptPasswordEncoder();
    }
}