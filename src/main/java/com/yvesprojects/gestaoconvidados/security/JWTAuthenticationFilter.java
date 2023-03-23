package com.yvesprojects.gestaoconvidados.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yvesprojects.gestaoconvidados.exceptions.GlobalExceptionHandler;
import com.yvesprojects.gestaoconvidados.models.User;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	private JwtUtil jwtUtil;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.setAuthenticationFailureHandler(new GlobalExceptionHandler());
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
			User userCredentials = new ObjectMapper().readValue(request.getInputStream(), User.class);
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword(), new ArrayList<>());
			
			Authentication authentication = this.authenticationManager.authenticate(authToken);
			return authentication;
		} catch(Exception e) {
			throw new RuntimeException("Autentificação inválida!");
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException, ServletException {
		UserSpringSecurity userSpringSecurity = (UserSpringSecurity) authentication.getPrincipal();
		String username = userSpringSecurity.getUsername();
		String password = userSpringSecurity.getPassword();
	}
}