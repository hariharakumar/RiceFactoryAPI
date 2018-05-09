package com.projects.ricefactory.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class TokenService {

    @Autowired
    private Environment env;

    private final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private String tokenSecret = env.getProperty("jwt.secret.key");
    
    public String createToken(Object userId) {
        // Each key-value pair in JWT Token is a claim.
        try {

            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            String token = JWT.create().withClaim("userId", userId.toString()).
                           withClaim("createdAt", new Date()).sign(algorithm);
            return token;
        }
        catch (UnsupportedEncodingException uee) {
            logger.error("Encoding Error while creating JWT Token :" + uee.getMessage());
            uee.printStackTrace();
        } catch (JWTCreationException jce) {
            logger.error("Error while creating JWT Token :" + jce.getMessage());
            jce.printStackTrace();
        }

        return null;
    }

    public String getUserIdFromToken(String jwtToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();

            DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
            return decodedJWT.getClaim("userId").toString();
        }
        catch (UnsupportedEncodingException uee) {
            logger.error("Encoding Error while creating JWT Token :" + uee.getMessage());
            uee.printStackTrace();
        } catch (JWTVerificationException jve) {
            logger.error("Error while verifying JWT Token :" + jve.getMessage());
            jve.printStackTrace();
        }
        return null;
    }

    
    public boolean isTokenValid(String jwtToken) {
        String userId = this.getUserIdFromToken(jwtToken);
        return userId!=null;
    }
}
