package carsharing_system.car_sharing.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login","/api/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/cars/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/cars/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/cars/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/cars/**").hasAuthority("ADMIN")

                        .requestMatchers("/api/users/all").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/rentals/all").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception{
        return  configuration.getAuthenticationManager();
    }
}

