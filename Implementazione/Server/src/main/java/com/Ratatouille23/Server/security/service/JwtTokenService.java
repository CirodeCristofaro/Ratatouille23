package com.Ratatouille23.Server.security.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class JwtTokenService {

    @Value("${security.jwt.secret:INGSW2023}")
    private String passwordJWT;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostConstruct
    protected void init() {
        this.passwordJWT = Base64.getEncoder().encodeToString(this.passwordJWT.getBytes());
    }

    public String generateToken(final UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration( new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))//token di durata di sette giorni
                .signWith(SignatureAlgorithm.HS512, passwordJWT)
                .compact();
    }

    public String getBearer(final HttpServletRequest request) {
        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.substring(7);
    }


    public Authentication getAuthenticationByToken(String token)   {
        String email = getEmailFromToken(token);
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());

    }

    private String getEmailFromToken(String token) {
        Claims claims = extractAllClaims(token);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(claims.get("sub"), String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(passwordJWT)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(passwordJWT).parseClaimsJws(token);
            return true;
        } catch (final ExpiredJwtException expiredEx) {
            log.warn("Token è  scaduto:  " + expiredEx.getMessage());
            return false;
        } catch (final JWTVerificationException verificationEx) {
            log.warn("verifica del token fallita : " + verificationEx.getMessage());
            return false;
        } catch (SignatureException signatureException) {
            log.warn("signatura del token fallita : " + signatureException.getMessage());
            return false;
        }

    }
}