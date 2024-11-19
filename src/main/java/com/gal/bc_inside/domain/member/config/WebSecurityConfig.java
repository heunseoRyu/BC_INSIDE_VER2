package com.gal.bc_inside.domain.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public WebSecurityCustomizer configure(){
        return web -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers(
                        new AntPathRequestMatcher("/css/**"),
                        new AntPathRequestMatcher("/js/**"),
                        new AntPathRequestMatcher("/img/**")
                );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                new AntPathRequestMatcher("/auth/login"),
                                new AntPathRequestMatcher("/auth/join"),
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/boards/{id}")
                        )
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/"))
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
