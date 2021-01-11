package com.pit.solarserver.security.jwt;

import com.pit.solarserver.configuration.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Component
public final class JWTTokenHelper {

    private static Logger log = LoggerFactory.getLogger(JWTTokenHelper.class);

    public static String generateToken(Authentication authentication) {
        log.info("Generating token for user " + authentication.getName());
        Claims claims = Jwts.claims();
        claims.setSubject(authentication.getName());
        claims.setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME));
        claims.put("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(joining(",")));
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, Constants.JWT_SECRET).compact();
    }

    public static Authentication validateToken(String token) throws BadCredentialsException {
        log.info("Validating token " + token);
        try {
            Claims claims = Jwts.parser().setSigningKey(Constants.JWT_SECRET).parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            List<GrantedAuthority> authorities = Arrays.stream(((String) claims.get("authorities")).split(",")).map(SimpleGrantedAuthority::new).collect(toList());
            return new UsernamePasswordAuthenticationToken(username, token, authorities);
        } catch (Exception ex) {
            log.error(ex.toString());
            throw new BadCredentialsException("Invalid jwt token: " + ex.getMessage());
        }
    }
}
