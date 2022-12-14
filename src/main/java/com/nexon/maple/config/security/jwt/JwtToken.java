package com.nexon.maple.config.security.jwt;

import com.nexon.maple.config.security.auth.PrincipalDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@ConfigurationProperties(prefix="jwt")
@Getter
public class JwtToken {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.grade}")
    private String grade;
    @Value("${jwt.type}")
    private String type;
    @Value("${jwt.accessTokenName}")
    private String accessTokenName;
    @Value("${jwt.refreshTokenName}")
    private String refreshTokenName;
    @Value("_inf")
    private String tokenFlagName;

    private Key hashKey;

    public static final int ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000;              // 30분
    public static final int REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;    // 7일 ( 1주일 )

    private void initHashKey() {
        if(Objects.nonNull(hashKey)) {
            return ;
        }
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.hashKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(PrincipalDetails principalDetails) {
        initHashKey();

        // 권한 가져오기
        String authorities = principalDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        return Jwts.builder()
                .setSubject(principalDetails.getUsername())
                .claim(grade, authorities)
                .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(hashKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken() {
        initHashKey();
        // 권한 가져오기
        long now = (new Date()).getTime();

        // Refresh Token 생성
        return Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(hashKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserName(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(grade) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        return claims.getSubject();
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(grade) == null) {
            throw new BadCredentialsException("자격 증명에 실패했습니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(grade).toString().split(","))
                        .map((data) -> new SimpleGrantedAuthority("ROLE_" + data))
                        .collect(Collectors.toList());

        if(Objects.isNull(claims.getSubject()) || Objects.isNull(authorities) ) {
            throw new BadCredentialsException("자격증명에 실패했습니다.");
        }

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(hashKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {

        try {
            Assert.notNull(token, "token null");

            Jwts.parserBuilder()
                    .setSigningKey(hashKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
//            new RuntimeException("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
//            new RuntimeException("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
//            new RuntimeException("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
//            new RuntimeException("JWT claims string is empty.", e);
        } 
        return false;
    }

    public boolean isExpired(String token) {

        try {
            Assert.notNull(token, "token null");

            Jwts.parserBuilder()
                    .setSigningKey(hashKey)
                    .build()
                    .parseClaimsJws(token);

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
//            new RuntimeException("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            return true;
//            new RuntimeException("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
//            new RuntimeException("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
//            new RuntimeException("JWT claims string is empty.", e);
        }
        return false;
    }

    public String typeRemove(String token) {
        if(token.indexOf(type + " ") != 0) {
            throw new IllegalArgumentException("Token type 이 존재하지 않습니다.");
        }
        return token.substring(type.length()+1);
    }

    public String getExpiration(String accessToken) {
        // accessToken 남은 유효시간
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(hashKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getExpiration();

        return Objects.toString(expiration.getTime());
    }
}
