package com.luckygarg.blog_service.Security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private JwtTokenProvider tokenProvider;
	 
 
    
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
    	this.tokenProvider = jwtTokenProvider;
        
	}

	private String extractJwtFromRequest(jakarta.servlet.http.HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }
   
    @Override
	protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
			throws jakarta.servlet.ServletException, IOException, java.io.IOException {
		  String token = extractJwtFromRequest(request);

	        if (token != null && tokenProvider.validateToken(token)) {
	            String username = tokenProvider.getUsernameFromToken(token);

	            // Create authentication token
	            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        }

	        filterChain.doFilter(request, response);
		
	}
}
