package me.parkdaiho.project.dto;

import lombok.Getter;
import lombok.Setter;
import me.parkdaiho.project.domain.user.Provider;
import me.parkdaiho.project.domain.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Setter
@Getter
public class OAuth2SignUpRequest {

    private String nickname;
    private String email;
    private String provider;

    public User toEntity(String username, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .email(email)
                .provider(Provider.valueOf(provider))
                .build();
    }
}
