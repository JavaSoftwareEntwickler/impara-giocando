package com.imparagiocando.imparagiocando.config;

import com.imparagiocando.imparagiocando.user.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public static final String LOGIN_MAPPING = "/login";

    @Bean
    public UserDetailsService userDetailsService(){
        return new MyUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        return auth.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.csrf(AbstractHttpConfigurer::disable)
        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            antMatcher(LOGIN_MAPPING),
                            antMatcher("/logout"),
                            antMatcher("/register"),
                            antMatcher("/css/**"),
                            antMatcher("/js/**"),
                            antMatcher("/images/**"),
                            antMatcher("/answer"),
                            antMatcher("/game"),
                            antMatcher("/result"),
                            antMatcher("/activate-account")
                            ).permitAll();
                    auth.anyRequest().authenticated();
                    });
        http.formLogin(form->form.loginPage(LOGIN_MAPPING)
                        .permitAll()
                        .loginProcessingUrl(LOGIN_MAPPING)
                        .defaultSuccessUrl("/game" ,true))
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

}
