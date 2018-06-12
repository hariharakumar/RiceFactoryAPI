package com.projects.ricefactory.service.impl;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private Environment env;

    private final Logger logger = LoggerFactory.getLogger(TokenService.class);

    public TokenService() {
    }

    public TokenService(Environment env) {
        this.env = env;
    }
    
    public String createToken(Object userId) {

        String tokenSecret = env.getProperty("jwt.secret.key");
        Long dayPlusOne = new Date().getTime() + TimeUnit.DAYS.toMillis(1);

        // Each key-value pair in JWT Token is a claim.
        try {

            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            String token = JWT.create().withClaim("userId", userId.toString()).
                           withClaim("iat", new Date()).
                           withClaim("exp", new Date(dayPlusOne)) // JWT Expires in 1 day
                           .sign(algorithm);
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

    public Map<String, Claim> getClaimsFromJwtToken(String jwtToken) throws Exception{

        String tokenSecret = env.getProperty("jwt.secret.key");

        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();

            DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
            Map<String, Claim> jwtPayload = decodedJWT.getClaims();
            return jwtPayload;

            //return decodedJWT.getClaim("userId").toString();
        }
        catch (UnsupportedEncodingException uee) {
            logger.error("Encoding Error while creating JWT Token :" + uee.getMessage());
            uee.printStackTrace();
            throw uee;
        } catch (JWTVerificationException jve) {
            logger.error("Error while verifying JWT Token :" + jve.getMessage());
            jve.printStackTrace();
            throw jve;
        }
    }

    
    public boolean isTokenValid(String jwtToken) throws Exception {
        try {
            Map<String, Claim> claimsFromJwtToken = this.getClaimsFromJwtToken(jwtToken);

            String userId = claimsFromJwtToken.get("userId").asString();

            if (userId == null) {
                return false;
            }
            return userId != null;
        }
        catch (Exception e) {
            throw e;
        }
    }
}
