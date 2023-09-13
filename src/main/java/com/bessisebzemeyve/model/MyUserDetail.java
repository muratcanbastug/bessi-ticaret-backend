package com.bessisebzemeyve.model;

import com.bessisebzemeyve.entity.Role;
import com.bessisebzemeyve.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class MyUserDetail implements UserDetails {
    private final User user;

    public MyUserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(Objects.nonNull(user)) {
            return Collections.singleton(new SimpleGrantedAuthority(user.getRole().getName()));
        }
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}