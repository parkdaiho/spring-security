package me.parkdaiho.project.dto;

import me.parkdaiho.project.domain.user.Provider;
import me.parkdaiho.project.domain.user.RefreshToken;
import me.parkdaiho.project.domain.user.User;
import me.parkdaiho.project.repository.RefreshTokenRepository;
import me.parkdaiho.project.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OAuth2SignUpRequestTest {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName("oauth2User sign-up test")
    public void createOAuth2User() {
        // given
        String username = "test";
        String password = "test";
        String email = "email";
        String nickname = "nickname";
        String provider = "GOOGLE";

        OAuth2SignUpRequest dto = new OAuth2SignUpRequest();
        dto.setEmail(email);
        dto.setNickname(nickname);
        dto.setProvider(provider);

        User user = dto.toEntity(username, password);

        // when
        Long savedUserId = refreshTokenRepository.save(new RefreshToken(user)).getId();
        User savedUser = userService.findById(savedUserId);

        // then
        assertThat(savedUser.getUsername()).isEqualTo(username);
        assertThat(savedUser.getPassword()).isEqualTo(passwordEncoder.encode(password));
        assertThat(savedUser.getEmail()).isEqualTo(email);
        assertThat(savedUser.getNickname()).isEqualTo(nickname);
        assertThat(savedUser.getProvider()).isEqualTo(Provider.valueOf(provider));
    }
}