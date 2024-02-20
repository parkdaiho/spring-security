package me.parkdaiho.project.config.oauth2;

import me.parkdaiho.project.domain.user.Provider;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

public class OAuth2AuthenticationCustomException extends OAuth2AuthenticationException {

    private final OAuth2UserInfo oAuth2UserInfo;

    public OAuth2AuthenticationCustomException(String errorCode, OAuth2UserInfo oAuth2UserInfo) {
        super(errorCode);
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    public OAuth2UserInfo getoAuth2UserInfo() {
        return oAuth2UserInfo;
    }
}
