package com.zerobase.real_time_chat.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtUtil {

	private static final Long expireTimeMilliSecond = 1000L * 60 * 60 * 24;
	private static final String CLAIM_KEY = "userEmail";
	private static final String ROLE = "USER";
	@Value("${jwt.token.secret}")
	private String secretKey;

	public String getUserEmail(String token) {
		return parseClaims(token).get(CLAIM_KEY, String.class);
	}

	public boolean validateToken(String token) {

		if (!StringUtils.hasText(token)) {
			return false;
		}

		return !parseClaims(token).getExpiration().before(new Date());
	}

	public Claims parseClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	public String createToken(String userEmail) {
		Claims claims = Jwts.claims(); // 일종의 map
		claims.put(CLAIM_KEY, userEmail);

		long time = System.currentTimeMillis();

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date(time))
			.setExpiration(new Date(time + expireTimeMilliSecond))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}
	public UsernamePasswordAuthenticationToken getAuthentication(String token) {
		return new UsernamePasswordAuthenticationToken(
			getUserEmail(token), null, List.of(new SimpleGrantedAuthority(ROLE))
		);
	}
}
