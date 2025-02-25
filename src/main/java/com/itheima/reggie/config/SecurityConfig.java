package com.itheima.reggie.config;

import com.itheima.reggie.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *  Spring Security Configuration
 *  Disables sessions (stateless JWT authentication).
 *  Defines public and protected API endpoints.
 *  Adds JWT Authentication Filter.
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF since we use JWT
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session
                .and()
                .authorizeHttpRequests(
                        auth -> auth
                                .antMatchers(
                                        "/api/employee/login",
                                        "/api/employee/logout",
                                        "/api/backend/**",
                                        "/api/front/**",
                                        "/api/common/**",
                                        "/api/user/sendMsg",
                                        "/api/user/login"
                                ).permitAll() // Public endpoints
                                .anyRequest().authenticated() // Protect all other endpoints
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Use JWT filter

        return http.build();
    }
}
