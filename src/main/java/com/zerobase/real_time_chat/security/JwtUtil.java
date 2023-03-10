package com.zerobase.real_time_chat.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtUtil {

	private static final Long expireTimeMilliSecond = 1000L * 60 * 60;
	private static final String claimName = "userName";
	private static String secretKey;
	@Value("${jwt.token.secret}")
	public void setSecretKey(String value){
		secretKey = value;
	}

	public static String getUserEmail(String token) {
		return parseClaims(token).get(claimName, String.class);
	}

	public static boolean validateToken(String token) {

		if (!StringUtils.hasText(token)) {
			return false;
		}

		return !parseClaims(token).getExpiration().before(new Date());
	}

	private static Claims parseClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	public static String createToken(String userEmail, String key) {
		Claims claims = Jwts.claims(); // 일종의 map
		claims.put(claimName, userEmail);

		long time = System.currentTimeMillis();

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date(time))
			.setExpiration(new Date(time + expireTimeMilliSecond))
			.signWith(SignatureAlgorithm.HS256, key)
			.compact();
	}

}
