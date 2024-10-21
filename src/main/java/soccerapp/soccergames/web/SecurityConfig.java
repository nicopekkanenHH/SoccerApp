package soccerapp.soccergames.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/login", "/resources/**").permitAll()  
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")  
                        .defaultSuccessUrl("/games", true)  
                        .failureUrl("/login-error")  
                        .permitAll())
                .logout(logout -> logout
                        .permitAll())
                        .csrf(csrf -> csrf.disable())
                    .httpBasic(withDefaults());  
                  
        return http.build();
    }
}