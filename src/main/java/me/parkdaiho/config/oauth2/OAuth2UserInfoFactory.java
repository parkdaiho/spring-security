package me.parkdaiho.config.oauth2;

import me.parkdaiho.domain.OAuth2Provider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo generateOAuth2UserInfo(OAuth2Provider provider, Map<String, Object> attributes) {
        switch (provider) {
            case GOOGLE -> {
                return new GoogleUserInfo(attributes);
            }
            default -> throw new IllegalArgumentException("invalid provider: " + provider.getProvider());
        }
    }
}
