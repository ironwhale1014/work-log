package com.honeybee.work_log.service;

import com.honeybee.work_log.domain.RefreshToken;
import com.honeybee.work_log.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new IllegalArgumentException("Unexpected token")
        );
    }
}
