package com.jihun.booksearcher.book.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Value("${cors.sourceUrl}")
    private String sourceUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*") // 허용할 오리진(도메인) 설정
                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowCredentials(true) // 쿠키를 허용할지 여부
                .maxAge(3600); // CORS preflight 요청의 유효 시간 설정 (초)
    }
}