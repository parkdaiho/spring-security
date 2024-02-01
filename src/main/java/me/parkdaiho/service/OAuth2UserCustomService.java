package me.parkdaiho.service;

import lombok.RequiredArgsConstructor;
import me.parkdaiho.config.PrincipalDetails;
import me.parkdaiho.config.oauth2.OAuth2UserInfo;
import me.parkdaiho.config.oauth2.OAuth2UserInfoFactory;
import me.parkdaiho.domain.OAuth2Provider;
import me.parkdaiho.domain.User;
import me.parkdaiho.exception.OAuth2AuthenticationCustomException;
import me.parkdaiho.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequiredArgsConstructor
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2Provider provider = OAuth2Provider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.generateOAuth2UserInfo(provider, oAuth2User.getAttributes());
        User user = userRepository.findByEmail(oAuth2UserInfo.getEmail())
                .orElseThrow(() -> new OAuth2AuthenticationCustomException("Unexpected user", oAuth2UserInfo));

        return new PrincipalDetails(user, oAuth2UserInfo);
    }
}
