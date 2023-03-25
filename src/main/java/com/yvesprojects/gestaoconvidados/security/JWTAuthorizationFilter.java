package com.yvesprojects.gestaoconvidados.security;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JwtUtil jwtUtil;
	
	private UserDetailsService userDetailsService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager,JwtUtil jwtUtil, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}
	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		String authorizationHeadler = request.getHeader("Authorization");
		if(Objects.nonNull(authorizationHeadler) && authorizationHeadler.startsWith("Bearer")) {
			String token = authorizationHeadler.substring(7);
			UsernamePasswordAuthenticationToken auth = this.getAuthentication(token);
			if(Objects.nonNull(auth)) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
			filterChain.doFilter(request, response);
		}
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if (this.jwtUtil.isValid(token)) {
			String username = this.jwtUtil.getUsername(token);
			UserDetails user = this.userDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticatedUser = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			return authenticatedUser;
		}
		return null;
	}
}
