package com.pit.solarserver.service;

import com.pit.solarserver.security.jwt.JWTTokenHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private static Logger logger = LoggerFactory.getLogger(LoginService.class);

    public String login(){
        return JWTTokenHelper.generateToken(SecurityContextHolder.getContext().getAuthentication());
    }

}
