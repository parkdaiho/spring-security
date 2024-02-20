package me.parkdaiho.project.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.parkdaiho.project.config.AuthenticationCustomSuccessHandler;
import me.parkdaiho.project.domain.user.User;
import me.parkdaiho.project.dto.OAuth2SignUpRequest;
import me.parkdaiho.project.dto.SignUpRequest;
import me.parkdaiho.project.dto.SignUpResponse;
import me.parkdaiho.project.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest dto) {
        userService.signUp(dto.toEntity());

        return ResponseEntity.ok(new SignUpResponse(dto.getUsername(), dto.getPassword()));
    }

    @PostMapping("/oauth2/sign-up")
    public ResponseEntity<SignUpResponse> oAuth2SignUp(@Valid @RequestBody OAuth2SignUpRequest dto) {
        String username = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        userService.signUp(dto.toEntity(username, password));

        return ResponseEntity.ok(new SignUpResponse(username, password));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request, response);

        return ResponseEntity.created(URI.create("/")).build();
    }

}
