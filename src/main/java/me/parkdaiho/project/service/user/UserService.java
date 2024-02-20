package me.parkdaiho.project.service.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.parkdaiho.project.config.AuthenticationCustomSuccessHandler;
import me.parkdaiho.project.config.token.TokenProvider;
import me.parkdaiho.project.domain.user.RefreshToken;
import me.parkdaiho.project.domain.user.User;
import me.parkdaiho.project.dto.OAuth2SignUpRequest;
import me.parkdaiho.project.dto.SignUpRequest;
import me.parkdaiho.project.repository.RefreshTokenRepository;
import me.parkdaiho.project.repository.UserRepository;
import me.parkdaiho.project.util.CookieUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationCustomSuccessHandler loginSuccessHandler;

    public User signUp(User user) {
        Long savedUserId = refreshTokenRepository.save(new RefreshToken(user)).getId();

        return findById(savedUserId);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, "refresh_token");
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public String findUserNicknameByRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            return null;
        }

        String refreshToken = null;
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("refresh_token")) {
                if(tokenProvider.validToken(cookie.getValue())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if(refreshToken == null) {
            return  null;
        }

        return tokenProvider.getUserNickname(refreshToken);
    }
}
