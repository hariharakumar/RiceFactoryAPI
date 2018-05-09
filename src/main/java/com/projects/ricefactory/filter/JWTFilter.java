package com.projects.ricefactory.filter;


import com.projects.ricefactory.service.impl.TokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class JWTFilter extends GenericFilterBean {

    private TokenService tokenService;

    JWTFilter() {
        this.tokenService = new TokenService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String jwtToken = httpServletRequest.getHeader("Authorization");

        if(httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }

        if(allowRequestWithoutToken(httpServletRequest)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        }
        else {
            if(jwtToken == null || !tokenService.isTokenValid(jwtToken)) {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            else {
                Object userId = tokenService.getUserIdFromToken(jwtToken);
                request.setAttribute("userId", userId);
                filterChain.doFilter(request, response);
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
