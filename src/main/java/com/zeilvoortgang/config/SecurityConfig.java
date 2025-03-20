package com.zeilvoortgang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for simplicity (not recommended for production)
            .authorizeRequests()
            .antMatchers("/api/**").authenticated() // Secure this endpoint
            .and()
            .httpBasic(); // Use basic authentication for simplicity

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        String sql = 
            "SELECT u.username, u.password, ar.roleName " +
            "FROM authorizationRoles AS ar " +
            "JOIN userRoles AS ur ON ar.authorizationRoleId = ur.authorizationRoleId " +
            "JOIN users AS u ON ur.userId = u.userId";        

        // Query for all users
        List<UserDetails> users = jdbcTemplate.query(sql, (rs, rowNum) -> {
            String username = rs.getString("username");
            String password = rs.getString("password");
            String role = rs.getString("roleName");

            return User.withUsername(username)
                       .password(password)
                       .roles(role)
                       .build();
        });

        if (users.isEmpty()) {
            throw new RuntimeException("No users found in the database");
        }

        return new InMemoryUserDetailsManager(users);
    }
}
