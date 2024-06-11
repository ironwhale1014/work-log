package com.honeybee.work_log.config.jwt;

import com.honeybee.work_log.domain.SiteUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtProperties jwtProperties;

    //TODO: 토큰 생성
    public String generateToken(SiteUser user, Duration expireDuration) {
        Date date = new Date();
        return makeToken(user, new Date(date.getTime() + expireDuration.toMillis()));
    }

    private SecretKey getSecretKey() {
        String secretKey = jwtProperties.getSecretKey();
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    private String makeToken(SiteUser siteUser, Date expiredAt) {

        Date now = new Date();

        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .issuer(jwtProperties.getIssuer())
                .subject(siteUser.getEmail())
                .issuedAt(now)
                .expiration(expiredAt)
                .claim("id", siteUser.getId())
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    //TODO: 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //TODO: 인증 객체 생성
    public Authentication getAuthentication(String token) {

        Claims claims = getClaims(token);

        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
                new User(claims.getSubject(), "", authorities), token, authorities
        );
    }

    //TODO: 토큰에서 유저 아이디를 가져오기
    public Long getUserIdFromToken(String token) {

        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token) {
        Claims claims = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
        return claims;
    }


    //

}