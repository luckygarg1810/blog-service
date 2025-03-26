package com.luckygarg.blog_service.Security;

import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenProvider {

	private SecretKey secretKey;

	public JwtTokenProvider() throws Exception {
		// Initialize the secret key
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512");
		keyGenerator.init(256); // Use 256-bit key for HS512
		secretKey = keyGenerator.generateKey();
	}

	// Generate JWT token
	public String generateToken(String username) {
		long expirationTime = 86400000; // 1 day in milliseconds

		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime)).signWith(secretKey).compact();
	}

	// Validate the token
	public boolean validateToken(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
			return !claims.getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	// Extract username from the token
	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
}
