package com.cqueltech.playingaround.config;

import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
  
    registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/")
        .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).noTransform().cachePublic().mustRevalidate());
    registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/")
        .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).noTransform().cachePublic());
    registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/")
        .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).noTransform().cachePublic());
  }
  
}
