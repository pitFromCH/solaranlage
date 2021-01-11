package com.pit.solarserver.security.jwt;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationProvider implements AuthenticationProvider {
        @Override
        public Authentication authenticate(Authentication auth) throws AuthenticationException {
            Authentication authentication = null;
            try {
                authentication = JWTTokenHelper.validateToken(auth.getCredentials().toString());
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid JWT Token");
            }
            //return fully populated authentication object
            return authentication;
        }

        @Override
        public boolean supports(Class<?> auth) {
            return auth.equals(JWTAuthenticationToken.class);
        }
}
