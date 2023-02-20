package com.example.appecommerce.security;

import com.example.appecommerce.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This class filters requests based on Token
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Get Token with "Bearer " part
        String authorization = request.getHeader("Authorization");

        //Check if authorization is valid
        if (authorization != null && authorization.startsWith("Bearer")){

            //Substring Authorization
            String token = authorization.substring(7);

            //Get username from token
            String username = jwtProvider.parseToken(token);

            //Get UserDetails by username
            UserDetails user = customUserDetailsService.loadUserByUsername(username);

            //Add it to SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        }
        filterChain.doFilter(request, response);
    }
}
