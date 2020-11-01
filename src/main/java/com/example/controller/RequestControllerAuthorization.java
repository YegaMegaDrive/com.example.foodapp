package com.example.controller;

import com.example.config.SecurityConstants;
import com.example.entities.UserAuth;
import com.example.repositories.AuthRepo;
import com.example.repositories.UserRepo;
import com.example.to.controller.LoginRequest;
import com.example.to.controller.SimpleResp;
import com.example.to.controller.UserDataRq;
import com.example.to.enums.RoleEnum;
import com.example.to.enums.SexEnum;
import com.example.to.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/Authorization")
public class RequestControllerAuthorization  {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthRepo authRepo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/Login")
    public ResponseEntity login(@RequestBody LoginRequest loginInfo){
        UserAuth userAuth = authRepo.findUserLoginByLogin(loginInfo.getLogin()).orElseThrow(()->
                new UsernameNotFoundException("User with login " + loginInfo.getLogin() + "not found"));
        if(encoder.matches(loginInfo.getPassword(),userAuth.getPassword())){
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginInfo.getLogin(),loginInfo.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else {
            throw new BadCredentialsException("passwords in database and in request body are different");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        String body = new String( Base64.getEncoder().encode((loginInfo.getLogin() + ":" + loginInfo.getPassword()).getBytes()), StandardCharsets.UTF_8);
        httpHeaders.set(SecurityConstants.TOKEN_HEADER, body);
        return ResponseEntity.ok().headers(httpHeaders).body(new SimpleResp());
    }
    @PostMapping("/Register")
    public ResponseEntity register(@RequestBody LoginRequest register){
        com.example.entities.User user = new com.example.entities.User();
        user.setSex(SexEnum.M);
        userRepo.save(user);
        UserAuth userAuth = new UserAuth();
        userAuth.setLogin(register.getLogin());
        userAuth.setPassword(encoder.encode(register.getPassword()));
        userAuth.setRole(RoleEnum.USER);
        userAuth.setStatus(StatusEnum.ACTIVE);
        userAuth.setUser(user);
        authRepo.save(userAuth);
       return ResponseEntity.ok(new SimpleResp());
    }

    @PostMapping("/Data")
    public ResponseEntity saveUserData(@RequestBody UserDataRq rq){
        com.example.entities.User user = authRepo.getUserAuthByLogin(rq.getLogin()).getUser();
        user.setSex(rq.getSex());
        user.setAge(rq.getAge());
        user.setHeight(rq.getHeight());
        user.setName(rq.getName());
        user.setWeight(rq.getWeight());
        userRepo.save(user);
        return ResponseEntity.ok(new SimpleResp());
    }
}
