package com.example.CP2_Spring.Security_JWT.security;

import com.example.CP2_Spring.Security_JWT.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private AuthService authService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/diplomas/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/diplomas").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/cursos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/diplomados").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/cursos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/diplomados/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/diplomas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/cursos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/diplomados/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/diplomas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/cursos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/diplomados/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
