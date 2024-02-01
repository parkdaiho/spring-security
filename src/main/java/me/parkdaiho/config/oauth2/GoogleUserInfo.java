package me.parkdaiho.config.oauth2;

import lombok.RequiredArgsConstructor;
import me.parkdaiho.domain.OAuth2Provider;

import java.util.Map;

public class GoogleUserInfo extends OAuth2UserInfo {

    public GoogleUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.GOOGLE;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("name");
    }
}
