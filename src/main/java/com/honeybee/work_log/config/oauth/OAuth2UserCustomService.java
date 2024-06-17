package com.honeybee.work_log.config.oauth;

import com.honeybee.work_log.domain.SiteUser;
import com.honeybee.work_log.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        saveOrUpdate(oAuth2User);

        return oAuth2User;
    }

    private SiteUser saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attribute = oAuth2User.getAttributes();
        String email = (String) attribute.get("email");
        String name = (String) attribute.get("name");
        SiteUser byEmail = userRepository.findByEmail(email).map(e -> e.update(name))
                .orElse(SiteUser.builder().email(email).username(name).build());

        return userRepository.save(byEmail);
    }
}
