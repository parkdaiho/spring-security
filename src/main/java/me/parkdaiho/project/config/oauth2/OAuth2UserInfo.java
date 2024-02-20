package me.parkdaiho.project.config.oauth2;

import lombok.Getter;
import me.parkdaiho.project.domain.user.Provider;

import java.util.Map;

@Getter
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public abstract String getEmail();

    public abstract String getProvider();

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
