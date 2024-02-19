package com.example.demo.jwtsecure;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.naming.MalformedLinkException;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtTokenProvider {
	// nên lưu trong application.properties
	private final String JWT_SECRET = "jwt_serret_must_be_an_64_character_length_at_least_meaning_512_bit_aaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	// Thời gian có hiệu lực của chuỗi jwt
	private final long JWT_EXPIRATION = 24 * 60 * 60 * 1000; // 1 day

	// Tạo ra jwt từ thông tin user
	public String generateToken(CustomUserDetails userDetails) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		// Tạo chuỗi json web token từ id của user.
		return Jwts.builder().setSubject(Long.toString(userDetails.getUser().getId())).setIssuedAt(now)
				.setExpiration(expiryDate).signWith(getSigningKey(), SignatureAlgorithm.HS512).compact();
	}

	// Lấy thông tin user từ jwt
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

	private Key getSigningKey() {
		byte[] keyBytes = this.JWT_SECRET.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token) {
//		try {
		// Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
			return  !isTokenExpired(token);
/*		} catch (MalformedJwtException ex) {
			System.err.println("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			System.err.println("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			System.err.println("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			System.err.println("JWT claims string is empty.");
		} catch (SignatureException ex) {
			System.err.println("JWT cannot be trust.");
		}
		return false;*/
	}

}
