package com.honeybee.work_log.service;


import com.honeybee.work_log.config.jwt.TokenProvider;
import com.honeybee.work_log.domain.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    public String createNewAccessToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        SiteUser user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
