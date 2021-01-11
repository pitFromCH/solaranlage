package com.pit.solarserver.security.jwt;

import com.pit.solarserver.configuration.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader(Constants.AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(Constants.JWT_BEARER)) {
            logger.debug("JWT token found in authorization header, try to validate");
            String jwtToken = httpServletRequest.getHeader(Constants.AUTHORIZATION_HEADER).replace(Constants.JWT_BEARER, "").trim();
            try {
                Authentication authentication = authenticationManager.authenticate(new JWTAuthenticationToken(null, jwtToken, null));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("User [" + authentication.getName() + "] successfully authenticated");
            } catch (BadCredentialsException e) {
                SecurityContextHolder.clearContext();
                logger.debug("Invalid JWT Token " + e.getMessage());
            }
        } else {
            logger.debug("No jwt token found in authorization header, pass to the next filter");
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

