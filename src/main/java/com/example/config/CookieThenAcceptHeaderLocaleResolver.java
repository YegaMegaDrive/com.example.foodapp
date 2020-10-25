package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class CookieThenAcceptHeaderLocaleResolver extends CookieLocaleResolver {

    @Value("${food.app.default.locale}")
    private String iDefaultLocale;

    @Override
    protected Locale determineDefaultLocale(HttpServletRequest request) {

        String acceptLanguage = request.getHeader("Accept-Language");
        if (acceptLanguage == null || acceptLanguage.trim().isEmpty()) {
            return new Locale(iDefaultLocale);
        }
        return request.getLocale();
    }
}
