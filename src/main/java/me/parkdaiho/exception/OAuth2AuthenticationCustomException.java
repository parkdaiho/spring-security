package me.parkdaiho.exception;

import lombok.Getter;
import me.parkdaiho.config.oauth2.OAuth2UserInfo;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

@Getter
public class OAuth2AuthenticationCustomException extends OAuth2AuthenticationException {

    private OAuth2UserInfo oAuth2UserInfo;

    public OAuth2AuthenticationCustomException(String message, OAuth2UserInfo oAuth2UserInfo) {
        super(message);
        this.oAuth2UserInfo = oAuth2UserInfo;
    }
}
