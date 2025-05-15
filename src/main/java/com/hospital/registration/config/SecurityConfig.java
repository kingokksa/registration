package com.hospital.registration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        @Autowired
        private JwtRequestFilter jwtRequestFilter;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.securityMatcher("/**")
                                .cors(cors -> cors.configure(http)) // 启用CORS支持
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(authorize -> authorize
                                                // 公开接口
                                                .requestMatchers("/auth/login", "/auth/register", "/auth/refresh",
                                                                "/swagger-ui/**",
                                                                "/swagger-ui.html", "/v3/api-docs/**")
                                                .permitAll().requestMatchers("/departments").permitAll()
                                                .requestMatchers("/doctors").permitAll()
                                                // 用户接口
                                                .requestMatchers("/me/**").authenticated()
                                                .requestMatchers("/appointments/**").authenticated() // 医生接口 - 诊断相关
                                                .requestMatchers(request -> request.getMethod().matches("POST|PUT") &&
                                                                request.getRequestURI().startsWith("/diagnosis"))
                                                .hasAnyAuthority("doctor", "ROLE_DOCTOR")
                                                // 其他诊断接口允许认证用户访问
                                                .requestMatchers("/diagnosis/**").authenticated()
                                                // 管理员接口
                                                .requestMatchers("/admin/**", "/users/**")
                                                .hasAnyAuthority("admin", "ROLE_ADMIN")
                                                .anyRequest().authenticated())
                                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
                return authConfig.getAuthenticationManager();
        }
}
