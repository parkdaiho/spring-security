package me.parkdaiho.config.oauth2;

import lombok.RequiredArgsConstructor;
import me.parkdaiho.domain.OAuth2Provider;

import java.util.Map;

@RequiredArgsConstructor
public abstract class OAuth2UserInfo {

    protected final Map<String, Object> attributes;

    public abstract Map<String, Object> getAttributes();
    public abstract OAuth2Provider getProvider();
    public abstract String getEmail();
    public abstract String getNickname();
}
