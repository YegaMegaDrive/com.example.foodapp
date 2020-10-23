package com.example.config;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

public class ExportConfig {
    @Bean
    MessageSource messageSource(){
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("classpath:com/example/i18n/messages");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

}
