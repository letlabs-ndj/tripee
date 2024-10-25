package com.lameute.chat_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("*") // Allow requests from any origin
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific HTTP methods
            .allowedHeaders("*") ;// Allow all headers
  }
}
