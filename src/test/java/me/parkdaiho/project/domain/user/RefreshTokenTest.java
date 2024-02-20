package me.parkdaiho.project.domain.user;

import me.parkdaiho.project.config.token.TokenProvider;
import me.parkdaiho.project.repository.RefreshTokenRepository;
import me.parkdaiho.project.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RefreshTokenTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    @DisplayName("refresh_token create test")
    public void createRefreshToken() {
        // given
        User user = User.builder()
                .username("test")
                .password("test")
                .nickname("test")
                .email("test")
                .build();

        // when
        RefreshToken refreshToken = refreshTokenRepository.save(new RefreshToken(user));

        // then
        assertThat(refreshToken.getId()).isEqualTo(user.getId());
    }
}