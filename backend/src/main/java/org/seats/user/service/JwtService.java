package org.seats.user.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.seats.user.entity.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static String secretKey = "java11SpringBootJWTTokenIssueMethod";

	private String create(
		Map<String, Object> claims,
		LocalDateTime expireAt
	) {

		var key = getSecretKey();
		var _expireAt = Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant());

		return Jwts.builder()
			.signWith(key, SignatureAlgorithm.HS256)
			.setClaims(claims)
			.setExpiration(_expireAt)
			.compact();
	}

	public String tokenCreate(
		User user
	) {
		Claims claims = Jwts.claims();
		claims.put("userId", user.getId());

		var expireAt = LocalDateTime.now().plusHours(999);

		var jwtToken = this.create(claims, expireAt);

		return jwtToken;
	}

	public Long extractId(String token) {
		var key = getSecretKey();

		var parser = Jwts.parserBuilder()
			.setSigningKey(key)
			.build();

		var result = parser.parseClaimsJws(token);

		return result.getBody().get("userId", Long.class);
	}

	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}
}
