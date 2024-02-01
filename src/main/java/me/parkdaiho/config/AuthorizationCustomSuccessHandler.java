package me.parkdaiho.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.parkdaiho.config.oauth2.OAuth2AuthorizationRequestRepositoryBasedOnCookie;
import me.parkdaiho.config.token.TokenProvider;
import me.parkdaiho.domain.RefreshToken;
import me.parkdaiho.repository.RefreshTokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
public class AuthorizationCustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final String REFRESH_TOKEN_NAME = "refresh_token";
    private final String REDIRECT_PATH = "/";
    private final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    private final Duration ACCESS_TOKEN_DURATION = Duration.ofHours(2);

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestRepositoryBasedOnCookie oAuth2AuthorizationRequestRepositoryBasedOnCookie;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        String refreshToken = tokenProvider.generateToken(principal, REFRESH_TOKEN_DURATION);
        saveRefreshToken(refreshToken, principal.getUserId());
        addRefreshTokenToCookie(request, response, refreshToken);


        String accessToken = tokenProvider.generateToken(principal, ACCESS_TOKEN_DURATION);
        String targetUrl = getTargetUrl(accessToken);

        clearAuthenticationAttributes(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        oAuth2AuthorizationRequestRepositoryBasedOnCookie.removeAuthorizationRequestCookie(request, response);
    }

    private String getTargetUrl(String accessToken) {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token", accessToken)
                .build()
                .toUriString();
    }

    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int expiry = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtils.deleteCookie(request, response, REFRESH_TOKEN_NAME);
        CookieUtils.addCookie(response, REFRESH_TOKEN_NAME, refreshToken, expiry);
    }

    private void saveRefreshToken(String newRefreshToken, Long userId) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }
}
