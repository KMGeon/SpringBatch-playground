package com.project.springbatch.App;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<TransactionIdFilter> loggingFilter() {
        FilterRegistrationBean<TransactionIdFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new TransactionIdFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

}
