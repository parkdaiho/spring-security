package me.parkdaiho.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.parkdaiho.config.CookieUtils;
import me.parkdaiho.domain.User;
import me.parkdaiho.dto.AddUserRequest;
import me.parkdaiho.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Long createUser(AddUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(User.builder()
                        .username(dto.getUsername())
                        .password(encoder.encode(dto.getPassword()))
                        .nickname(dto.getNickname())
                        .email(dto.getEmail())
                        .build()).getId();
    }

    public boolean logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, "refresh_token");

        new SecurityContextLogoutHandler().logout(
                request, response, SecurityContextHolder.getContext().getAuthentication()
        );

        return true;
    }
}
