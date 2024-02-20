package me.parkdaiho.project.service.user;

import lombok.RequiredArgsConstructor;
import me.parkdaiho.project.config.token.TokenProvider;
import me.parkdaiho.project.domain.user.User;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final UserService userService;

    private final static Duration ACCESS_TOKEN_DURATION = Duration.ofHours(1);

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unvalid token");
        }

        User user = userService.findById(tokenProvider.getUserId(refreshToken));

        return tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
    }
}
