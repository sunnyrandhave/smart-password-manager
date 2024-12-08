package com.smartpasswordmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests(auth -> auth
                        .requestMatchers("/user/login", "/user/register").permitAll() // Allow login and register page without authentication
                        .anyRequest().authenticated() // Require authentication for other pages
                )
                .formLogin(form -> form
                        .loginPage("/login.html") // Use your custom login page
                        .permitAll()
                        .defaultSuccessUrl("/homepage.html", true) // Redirect to homepage after successful login
                )
                .httpBasic(); // This allows HTTP Basic Auth as well, but might not be needed if using form login
        return httpSecurity.build();
    }
}
