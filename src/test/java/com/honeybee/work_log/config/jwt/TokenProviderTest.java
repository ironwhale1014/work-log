package com.honeybee.work_log.config.jwt;


import com.honeybee.work_log.domain.SiteUser;
import com.honeybee.work_log.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtProperties jwtProperties;


    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecretKey()));
    }

    @DisplayName("generateToken(): 유저 정보와 만료 기간을 전달해 토큰을 만들수 있다.")
    @Test
    public void generateToken() {
        SiteUser testUser = userRepo.save(SiteUser.builder().email("abcabc@gmail.com").password("test").build());

        System.out.println("testUser = " + testUser.getId());
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));
        System.out.println("token = " + token);

        Claims claims = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
        System.out.println("claims.getId() = " + claims.getId());
        assertThat(claims.getSubject()).isEqualTo("abcabc@gmail.com");
        assertThat(claims.get("id",Long.class)).isEqualTo(testUser.getId());
    }
}
