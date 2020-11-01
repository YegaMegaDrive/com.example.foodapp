package com.example.services;

import com.example.entities.UserAuth;
import com.example.repositories.AuthRepo;
import com.example.to.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("authenticationService")
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AuthRepo repo;

    @Autowired
    private PasswordEncoder encoder;



    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserAuth userAuth = repo.findUserLoginByLogin(login).orElseThrow(()->
                new UsernameNotFoundException("Username not found"));
        return SecurityUser.fromUser(userAuth);
    }
}
