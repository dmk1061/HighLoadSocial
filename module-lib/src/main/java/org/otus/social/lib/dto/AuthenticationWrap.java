package org.otus.social.lib.dto;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
public class AuthenticationWrap extends UsernamePasswordAuthenticationToken {

    Long userId;

    public AuthenticationWrap(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public AuthenticationWrap(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
