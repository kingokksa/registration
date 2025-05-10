package com.hospital.registration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration(); // 允许跨域的源（开发环境可以设置为*，生产环境建议设置具体的域名）
        config.addAllowedOriginPattern("*");

        // 允许跨域的请求头
        config.addAllowedHeader("*");

        // 允许跨域的请求方法，确保包含OPTIONS
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");

        // 允许携带认证信息
        config.setAllowCredentials(true);

        // 暴露响应头，包括认证相关的头部
        config.addExposedHeader("Authorization");
        config.addExposedHeader("refresh-token");

        // 预检请求的有效期，单位秒
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
