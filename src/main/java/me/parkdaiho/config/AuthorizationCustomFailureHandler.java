package me.parkdaiho.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.parkdaiho.config.oauth2.OAuth2UserInfo;
import me.parkdaiho.exception.OAuth2AuthenticationCustomException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class AuthorizationCustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final String REDIRECT_PATH = "/";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        OAuth2AuthenticationCustomException oAuth2AuthenticationCustomException = (OAuth2AuthenticationCustomException) exception;
        OAuth2UserInfo oAuth2UserInfo = oAuth2AuthenticationCustomException.getOAuth2UserInfo();

        request.setAttribute("oAuth2UserInfo", oAuth2UserInfo);

        getRedirectStrategy().sendRedirect(request, response, REDIRECT_PATH);
    }
}
