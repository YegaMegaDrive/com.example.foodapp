package com.example.to.security;

import com.example.entities.UserAuth;
import com.example.to.enums.StatusEnum;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {

    private String username;
    private String password;
    private List<SimpleGrantedAuthority> authorities;
    private Boolean isActive;

    public SecurityUser(String username, String password, List<SimpleGrantedAuthority> authorities, Boolean isActive) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromUser(UserAuth userAuth){
        return new User(
                userAuth.getLogin(),
                userAuth.getPassword(),
                userAuth.getStatus().equals(StatusEnum.ACTIVE),
                userAuth.getStatus().equals(StatusEnum.ACTIVE),
                userAuth.getStatus().equals(StatusEnum.ACTIVE),
                userAuth.getStatus().equals(StatusEnum.ACTIVE),
                userAuth.getRole().getAuthorities()
                );
    }
}
