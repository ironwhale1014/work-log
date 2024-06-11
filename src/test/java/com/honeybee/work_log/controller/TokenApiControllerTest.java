package com.honeybee.work_log.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.honeybee.work_log.config.jwt.JwtFactory;
import com.honeybee.work_log.config.jwt.JwtProperties;
import com.honeybee.work_log.domain.RefreshToken;
import com.honeybee.work_log.domain.SiteUser;
import com.honeybee.work_log.dto.CreateAccessTokenRequest;
import com.honeybee.work_log.repository.RefreshTokenRepository;
import com.honeybee.work_log.repository.UserRepository;
import com.honeybee.work_log.service.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TokenApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper mapper;
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    protected JwtProperties jwtProperties;
    @Autowired
    protected UserRepository userRepo;
    @Autowired
    protected RefreshTokenService refreshTokenService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        userRepo.deleteAll();
    }

    @DisplayName("createNewAccessToken")
    @Test
    public void createNewToken() throws Exception {
        final String url = "/api/token";


        SiteUser testUser = userRepo.save(SiteUser.builder()
                .email("user@gamil.com")
                .password("test")
                .build());

        String refreshToken = JwtFactory.builder().claims(Map.of("id", testUser.getId())).build().createToken(jwtProperties);

        refreshTokenRepository.save(new RefreshToken(testUser.getId(), refreshToken));

        CreateAccessTokenRequest request = new CreateAccessTokenRequest();
        request.setRefreshToken(refreshToken);

        final String requestBody = mapper.writeValueAsString(request);


        ResultActions resultActions = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody));

        resultActions.andExpect(status().isCreated()).andExpect(jsonPath("$.accessToken").isNotEmpty());


    }
}
