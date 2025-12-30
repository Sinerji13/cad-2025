package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
            .username("user")
            .password("{noop}password")
            .roles("USER")
            .build();
        UserDetails moderator = User.builder()
            .username("moderator")
            .password("{noop}password")
            .roles("MODERATOR")
            .build();
        return new InMemoryUserDetailsManager(user, moderator);
    }

    @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/addTask").permitAll()
            .requestMatchers("/deleteTask/**", "/updateTask/**", "/api")
                .hasAnyRole("USER", "MODERATOR")
            .anyRequest().authenticated()
        )
        .formLogin(form -> form.permitAll())
        .httpBasic(h -> h.realmName("Todo API")) // ← Параметр h вместо http
        .csrf(csrf -> csrf.disable());
    return http.build();
}
}