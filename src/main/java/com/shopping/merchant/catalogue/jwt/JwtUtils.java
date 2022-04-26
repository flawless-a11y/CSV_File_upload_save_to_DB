package com.shopping.merchant.catalogue.jwt;

import com.shopping.merchant.catalogue.repository.UserRepository;
import com.shopping.merchant.catalogue.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Autowired
    private UserRepository userRepository;
    @Value("${jwtSecret}")
    private String jwtSecret;
    @Value("${jwtExpirationMs}")
    private Long jwtExpirationMs;
    @Value("${logFilePath}")
    private String logFilePath;

    public String generateJwtToken(Authentication authentication) {
        Map<String, Object> customClaim = new HashMap<String, Object>();
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        customClaim.put("Microservice",userRepository.findByUsernameContaining(userPrincipal.getUsername()).getName());
        return Jwts.builder().setClaims(customClaim)
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) throws FileNotFoundException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            final Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).getBody();

            PrintStream writeToLog = new PrintStream(new FileOutputStream(logFilePath, true));
            writeToLog.append((CharSequence) body.get("Microservice"));
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
