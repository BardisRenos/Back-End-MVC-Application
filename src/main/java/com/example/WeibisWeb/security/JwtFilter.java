package com.example.WeibisWeb.security;

import com.example.WeibisWeb.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * The filter for Json web Token Class
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!request.getServletPath().equals(SecurityConstants.SIGN_UP_URL)) {
            final String authorizationHeader = request.getHeader(SecurityConstants.HEADER_STRING);

            String username = null;
            String jwt = null;

            if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                jwt = authorizationHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
                username = jwtUtil.extractUsername(jwt);
            }
            if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                UserDetails userDetails = userServiceImpl.loadUserByUsername(username);

                if (Boolean.TRUE.equals(jwtUtil.validateToken(jwt, userDetails))) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
