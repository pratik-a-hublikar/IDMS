package com.idms.commons.utils;

import com.idms.commons.constant.ConstantMessages;
import com.idms.commons.exception.ApplicationError;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public  class JWTUtils {

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    @Value("${jwt.secret.key}")
    private String secretKey;


    public  String generateJwtToken(String username,String subject,long ttl){
        try{
            Key key = new SecretKeySpec(Base64.getDecoder().decode(secretKey),
                    SignatureAlgorithm.HS256.getJcaName());
            Instant now = Instant.now();
            String jwtToken = Jwts.builder()
                    .claim("userName", username)
                    .setSubject(subject)
                    .setId(UUID.randomUUID().toString())
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(now.plus(ttl, ChronoUnit.MINUTES)))
                    .signWith(key)
                    .compact();
            return tokenPrefix +" "+jwtToken;
        } catch (Exception e){
            throw new ApplicationError(ConstantMessages.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean validateToken(String token,String tokenType) {
        try{
            Key key = new SecretKeySpec(Base64.getDecoder().decode(secretKey),
                    SignatureAlgorithm.HS256.getJcaName());
            DefaultClaims body = (DefaultClaims) Jwts.parserBuilder().setSigningKey(key).build().parse(token).getBody();
            if(body == null || body.get("userName") == null || body.get("sub") == null || !ConstantMessages.LOGIN_TOKEN.equals(body.get("sub"))
                    || body.get("exp") == null){
                return false;
            }
            String exp = body.get("exp").toString();
            return  (Long.parseLong(exp) <= System.currentTimeMillis() && (Long.parseLong(exp) != System.currentTimeMillis()));
        }catch (Exception e){
            return false;
        }
    }
}
