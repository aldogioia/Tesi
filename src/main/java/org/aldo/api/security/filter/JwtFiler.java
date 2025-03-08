package org.aldo.api.security.filter;

import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aldo.api.handler.JwtHandler;
import org.aldo.api.service.interfaces.InvalidTokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@NonNullApi
@RequiredArgsConstructor
public class JwtFiler extends OncePerRequestFilter {

    private final JwtHandler jwtHandler;
    private final UserDetailsService userDetailsService;
    private final InvalidTokenService invalidTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtHandler.getJwtFromRequest(request);
        if (!jwt.equals("invalid") && jwtHandler.isValidToken(jwt) && !invalidTokenService.isTokenInvalidated(jwt)) {
            String email = jwtHandler.getEmailFromToken(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email); //considero l'email come username
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
