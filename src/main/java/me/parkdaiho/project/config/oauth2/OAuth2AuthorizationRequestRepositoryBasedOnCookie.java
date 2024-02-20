package me.parkdaiho.project.config.oauth2;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.parkdaiho.project.util.CookieUtils;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.util.WebUtils;

import java.time.Duration;

public class OAuth2AuthorizationRequestRepositoryBasedOnCookie
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_user_request";
    private final int OAUTH2_AUTHORIZATION_REQUEST_COOKIE_DURATION = 180;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        System.out.println("loadAuthorizationRequest");
        Cookie cookie = WebUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);

        return CookieUtils.deserialize(cookie.getValue(), OAuth2AuthorizationRequest.class);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
                                            HttpServletRequest request, HttpServletResponse response) {
        System.out.println("saveAuthorizationRequest");
        if(authorizationRequest == null) {
            removeAuthorizationRequest(request, response);

            return;
        }

        CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
                CookieUtils.serialize(authorizationRequest), OAUTH2_AUTHORIZATION_REQUEST_COOKIE_DURATION);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("removeAuthorizationRequest");
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookie(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    }
}
