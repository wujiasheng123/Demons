package com.example.legendary.config.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author 吴嘉晟
 */
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
