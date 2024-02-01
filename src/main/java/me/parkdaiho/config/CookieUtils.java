package me.parkdaiho.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

public class CookieUtils {

    public static void addCookie(HttpServletResponse response, String cookieName,
                                String value, int expiry) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setMaxAge(expiry);

        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie[] cookies = request.getCookies();

        if(cookies == null) {
            return;
        }

        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);

                response.addCookie(cookie);
                return;
            }
        }
    }

    public static String serialize(Object object) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(object));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
                Base64.getDecoder().decode(cookie.getValue())
        );
    }
}
