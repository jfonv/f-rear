package com.chyld.filters;

import com.chyld.dtos.AuthDto;
import com.chyld.services.UserService;
import com.chyld.utilities.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.chyld.entities.*;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private String userNameTemp = "";
//    final UserService userService

    public JwtLoginFilter(String defaultFilterProcessesUrl, JwtUtil jwtUtil, UserDetailsService userDetailsService, AuthenticationManager authManager) {
        super(defaultFilterProcessesUrl);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        setAuthenticationManager(authManager);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        final AuthDto auth = new ObjectMapper().readValue(request.getInputStream(), AuthDto.class);
        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword());
        userNameTemp = auth.getUsername();
        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        User authenticatedUser = (User) userDetailsService.loadUserByUsername(authResult.getName());
        authenticatedUser.setAttempts(0);
        ((UserService)userDetailsService).saveUser(authenticatedUser);
        String token = jwtUtil.generateToken(authenticatedUser);
        response.setHeader("Authorization", "Bearer " + token);
        SecurityContextHolder.getContext().setAuthentication(jwtUtil.tokenFromStringJwt(token));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,	HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        User user = (User)userDetailsService.loadUserByUsername(userNameTemp);
        int attempts = user.getAttempts();
        user.setAttempts(attempts + 1);
        if ((attempts + 1) >= 3) {
            user.setEnabled(false);
            failed = new DisabledException("Account Locked & GFY");
        }
        ((UserService) userDetailsService).saveUser(user);

        super.unsuccessfulAuthentication(request, response, failed);
    }
}

