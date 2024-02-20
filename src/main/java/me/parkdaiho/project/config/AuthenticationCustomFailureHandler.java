package me.parkdaiho.project.config;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.parkdaiho.project.config.oauth2.OAuth2AuthenticationCustomException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class AuthenticationCustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        OAuth2AuthenticationCustomException oAuth2AuthenticationCustomException = (OAuth2AuthenticationCustomException)  exception;

        request.setAttribute("oauth2UserInfo", oAuth2AuthenticationCustomException.getoAuth2UserInfo());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/sign-up");
        dispatcher.forward(request, response);
    }
}
