package com.pit.solarserver.controller;

import com.pit.solarserver.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RequestMapping(value = "/login")
@RestController
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @GetMapping(produces= TEXT_PLAIN_VALUE)
    public ResponseEntity login(){
        logger.debug("Rest-API call: login");
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.FORBIDDEN);

        String jwtToken = loginService.login();

        if (jwtToken != null && (jwtToken.length()> 0))  {
            responseEntity = new ResponseEntity(jwtToken, HttpStatus.OK);
        }
        return responseEntity;
    }


}
