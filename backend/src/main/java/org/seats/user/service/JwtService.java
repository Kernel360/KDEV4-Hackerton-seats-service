package org.seats.user.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.seats.user.entity.User;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtService {

    private static String secretKey = "java11SpringBootJWTTokenIssueMethod";

    public String create(
            Map<String, Object> claims,
            LocalDateTime expireAt
    ){

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
        var _expireAt = Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setExpiration(_expireAt)
                .compact();
    }

    public String tokenCreate(
            User user
    ){
        var claims = new HashMap<String, Object>();
        claims.put(String.valueOf(user.getId()), 923);

        var expireAt = LocalDateTime.now().plusMinutes(999);

        var jwtToken = this.create(claims, expireAt);

        System.out.println(jwtToken);
        return jwtToken;
    }

    public void validation(String token) {
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        var result = parser.parseClaimsJws(token);

        result.getBody().entrySet().forEach(value -> {
            log.info("key : {}, value : {}", value.getKey(), value.getValue());
        });
    }
}
