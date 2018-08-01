package com.projects.ricefactory.filter;


import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.ricefactory.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Configuration
public class JWTFilter extends GenericFilterBean {

    @Autowired
    Environment env;

    @Autowired
    TokenService tokenService;

    private static final String UNAUTHORIZED = "Unauthorized token";
    private static final String INVALID_TOKEN_FORMAT_MSG = "Invalid token format. Correct format: Bearer XXXX";

    JWTFilter() {
        //this.tokenService = new TokenService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse httpServletResponse = null;

        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            httpServletResponse = (HttpServletResponse) response;

            String jwtToken = httpServletRequest.getHeader("Authorization");

            if (httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
                httpServletResponse.sendError(HttpServletResponse.SC_OK,"OK");
            }

            if (allowRequestWithoutToken(httpServletRequest)) {
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                filterChain.doFilter(request, response);
            } else {

                // Disabling JWT Filtering temporarily
                if(Boolean.parseBoolean(env.getProperty("enable.jwt.token")) == false) {
                    filterChain.doFilter(request, response);
                }

                String[] tokens = jwtToken.split(" ");

                if (tokens.length != 2) {
                    logger.debug("JWT Authentication:  invalid token format, length not 2");
                    throw new Exception(INVALID_TOKEN_FORMAT_MSG);
                }
                if (!tokens[0].trim().equalsIgnoreCase("Bearer")) {
                    logger.debug("JWT Authentication:  invalid token format, no Bearer");
                    throw new Exception(INVALID_TOKEN_FORMAT_MSG);
                }

                String token = tokens[1];


                if (token == null || !tokenService.isTokenValid(token)) {
                    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                } else {

                    Map<String, Claim> claimsFromJwtToken = tokenService.getClaimsFromJwtToken(token);
                    String userId = null;


                    userId = claimsFromJwtToken.get("userId").asString();

                    request.setAttribute("userId", userId);
                    filterChain.doFilter(request, response);
                }
            }
        }
        catch (Exception e) {
            try {

                JSONObject obj = new JSONObject();

                obj.put("status", "fail");
                obj.put("message", "Unauthorized token");
                obj.put("data", e.getMessage());

                String jsonRepresentation = obj.toString();
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().write(jsonRepresentation);
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            catch (Exception e1) {
                logger.debug(e1.getLocalizedMessage());
            }
        }
    }

    private boolean allowRequestWithoutToken(HttpServletRequest request) {
        if(request.getRequestURI().contains("/users") && request.getMethod().equalsIgnoreCase("POST")) {
            return true;
        }
        return false;
    }
}
