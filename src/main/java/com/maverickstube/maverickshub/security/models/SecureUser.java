package com.maverickstube.maverickshub.security.models;

import com.maverickstube.maverickshub.models.Authority;
import com.maverickstube.maverickshub.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class SecureUser implements UserDetails {

    private final User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List< SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for (Authority authority : user.getAuthorities()) {
//            var grantedAuthority = new SimpleGrantedAuthority(authority.name());
//            authorities.add(grantedAuthority);
//
//            return authorities;
//        }

        return user.getAuthorities()
                .stream()
                .map(authority ->
                new SimpleGrantedAuthority(authority.name())).toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
