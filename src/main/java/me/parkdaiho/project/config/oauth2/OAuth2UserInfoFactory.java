package me.parkdaiho.project.config.oauth2;

import me.parkdaiho.project.domain.user.Provider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo createOAuth2UserInfo(Provider provider, Map<String, Object> attributes)
            throws IllegalArgumentException {
        switch (provider) {
            case GOOGLE -> {
                return new GoogleUserInfo(attributes);
            }

            default -> throw new IllegalArgumentException("Please Check Provider: " + provider);
        }
    }
}
