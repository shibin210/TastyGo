package com.itheima.reggie.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.Collections;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final String token;
    private final String userId;

    // Constructor with three parameters (existing one)
    public JwtAuthenticationToken(String userId, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userId = userId;
        this.token = token;
        setAuthenticated(true); // Mark authentication as successful
    }

    //  New Constructor with two parameters (for simpler authentication)
    public JwtAuthenticationToken(String userId, String token) {
        super(Collections.emptyList()); // No authorities assigned
        this.userId = userId;
        this.token = token;
        setAuthenticated(true); // Mark authentication as successful
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }
}
