package com.idms.commons.filter;

import ch.qos.logback.core.util.StringUtil;
import com.idms.commons.constant.ConstantMessages;
import com.idms.commons.constant.Constants;
import com.idms.commons.utils.JWTUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.Serial;


@Component
public class AuthenticationFilter extends OncePerRequestFilter {



    @Autowired
    private JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(isPublicURI(request.getRequestURI())){
            filterChain.doFilter(request,response);
            return;
        }
        String header = request.getHeader(Constants.AUTHORIZATION);
        if(StringUtils.isEmpty(header) || header.split(" ").length ==1 || StringUtils.isEmpty(header.split( " ")[1])){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        boolean authenticated = jwtUtils.validateToken(header.split(" ")[1], ConstantMessages.LOGIN_TOKEN);
        if(!authenticated){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        filterChain.doFilter(request,response);
    }

    boolean isPublicURI(String uri){

        if(uri.contains("/api/public/")){
            return true;
        }
        return false;
    }

}
