package com.pf.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.pf.api.common.BaseInterceptor;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

	@Bean
	BaseInterceptor baseInterceptor() {
         return new BaseInterceptor();
    }
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(baseInterceptor()).excludePathPatterns("/upload/**", "/static/**", "/error");
	}
}