package me.parkdaiho.config;

import lombok.RequiredArgsConstructor;
import me.parkdaiho.config.oauth2.OAuth2UserInfo;
import me.parkdaiho.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails, OAuth2User {

    private final User user;
    private OAuth2UserInfo oAuth2UserInfo;

    public PrincipalDetails(User user, OAuth2UserInfo oAuth2UserInfo) {
        this.user = user;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    public Long getUserId() {
        return user.getId();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2UserInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnabled();
    }

    @Override
    public String getName() {
        return oAuth2UserInfo.getEmail();
    }
}
