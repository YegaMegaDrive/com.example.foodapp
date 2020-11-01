package com.example.config;

import com.example.to.controller.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class FoodAppAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    @Qualifier("authenticationService")
    private UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager manager;

    public LoginRequest getUsernameAndPasswordFromToken(String token) {
                byte[] base64Token = token.trim().getBytes(StandardCharsets.UTF_8);
                byte[] decoded;
                try {
                    decoded = Base64.getDecoder().decode(base64Token);
                } catch (IllegalArgumentException var8) {
                    throw new BadCredentialsException("Failed to decode basic authentication token");
                }
                LoginRequest loginRequest = new LoginRequest();
                String decode = new String(decoded, StandardCharsets.UTF_8);
                loginRequest.setLogin(decode.substring(0,decode.indexOf(":")));
                loginRequest.setPassword(decode.substring(decode.indexOf(":")+1));
                log.info("decoded username: {}, password: {}",loginRequest.getLogin(),loginRequest.getPassword());
                return loginRequest;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String header = httpServletRequest.getHeader(SecurityConstants.TOKEN_HEADER);
        String userName = null;
        String authToken = null;
        String userPassword = null;
        if (header != null) {
            authToken = header.replace(SecurityConstants.TOKEN_HEADER, "");
            log.info("AUTHORIZATION TOKEN = {}",authToken);
            try {
                LoginRequest info = getUsernameAndPasswordFromToken(authToken);
                userName = info.getLogin();
                userPassword = info.getPassword();
            } catch (IllegalArgumentException e) {
                logger.error("An error occurred during getting username from token.", e);
                throw new ServletException(e.getLocalizedMessage());
            }
        } else {
            log.error("header = null");
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           // UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            Authentication auth= manager.authenticate(new UsernamePasswordAuthenticationToken(userName,userPassword));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

