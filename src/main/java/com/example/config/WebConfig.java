package com.example.config;

import com.example.i18n.MessageProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${food.app.cookie.max.age}")
    private Integer iCookieMaxAge;

    @Bean("localeResolver")
    public LocaleResolver getLocaleResolver(){
        CookieThenAcceptHeaderLocaleResolver resolver = new CookieThenAcceptHeaderLocaleResolver();
        resolver.setCookiePath("/");
        resolver.setCookieMaxAge(iCookieMaxAge);
        return resolver;
    }

    @Bean("messageSource")
    MessageSource messageSource(){
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("classpath:com/example/i18n/messages");
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    MessageProvider messageProvider(){
        return new MessageProvider();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        registry.addInterceptor(interceptor).addPathPatterns("/");
    }

}
