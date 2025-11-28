package com.example.eindproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        // publiek:
                        .requestMatchers("/", "/catalog", "/register", "/login",
                                "/css/**", "/js/**", "/images/**").permitAll()

                        // enkel ingelogde gebruikers mogen winkelmandje/checkout
                        .requestMatchers("/cart/**", "/checkout/**").authenticated()

                        // rest: voorlopig ook toestaan
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")          // eigen loginview
                        .defaultSuccessUrl("/catalog", true) // na login naar catalog
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/catalog")
                        .permitAll()
                );

        return http.build();
    }
}
