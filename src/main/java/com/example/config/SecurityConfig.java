package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;


import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableSpringHttpSession
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationService")
    private UserDetailsService userDetailsService;

    @Value("${server.servlet.session.timeout}")
    private Integer maxInactiveIntervalInSeconds;

    @Autowired
    private AuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

  /*  @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    @Bean
    public SessionRepository sessionRepository() {
        SessionRepository sessionRepository = new MapSessionRepository(new ConcurrentHashMap<>());
        ((MapSessionRepository) sessionRepository)
                .setDefaultMaxInactiveInterval(maxInactiveIntervalInSeconds);
        return sessionRepository;
    }*/

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected DaoAuthenticationProvider getAuthenticationProvider(){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setPasswordEncoder(passwordEncoder());
        dao.setUserDetailsService(userDetailsService);
        return dao;
    }


   @Bean
   public FoodAppAuthenticationFilter getFilter(){
       return new FoodAppAuthenticationFilter();
   }


   public static final String[] PERMIT_ALL_URLS = {"/Authorization/Login","/Authorization/Register"};

   public static final String LOGOUT_URL = "/Authorization/Logout";

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(PERMIT_ALL_URLS).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .formLogin().disable()/*permitAll()
                .loginPage("/login")*/
                .logout(logout -> logout.permitAll()
                        .logoutUrl("/Authorization/Logout")
                        .addLogoutHandler(new SecurityContextLogoutHandler())
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies(SecurityConstants.SESSION_HEADER)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            if(SecurityContextHolder.getContext().getAuthentication() == null) {
                                response.setStatus(HttpServletResponse.SC_OK);
                            }else{
                                log.info("{}",SecurityContextHolder.getContext());
                                try {
                                    throw new Exception("when logout context must be null");
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                }));
               // .maximumSessions(1).sessionRegistry(sessionRegistry());
        http.addFilterBefore(getFilter(), UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getAuthenticationProvider()).userDetailsService(userDetailsService);
        super.configure(auth);
    }
}
