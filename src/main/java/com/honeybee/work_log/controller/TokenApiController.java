package com.honeybee.work_log.controller;

import com.honeybee.work_log.dto.CreateAccessTokenRequest;
import com.honeybee.work_log.dto.CreateAccessTokenResponse;
import com.honeybee.work_log.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        System.out.println("hou hou hou hou hou");
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());


        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponse(newAccessToken));
    }
}
