package me.parkdaiho.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.parkdaiho.config.oauth2.OAuth2UserInfo;
import me.parkdaiho.dto.AddUserRequest;
import me.parkdaiho.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserViewController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/sign-up")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/sign-up")
    public String signUp(AddUserRequest dto) {
        userService.createUser(dto);

        return "redirect:/login";
    }

    @GetMapping("/oauth2/sign-up")
    public String oauth2SignUpPage(HttpServletRequest request) {
        OAuth2UserInfo oAuth2UserInfo = (OAuth2UserInfo) request.getAttribute("oAuth2UserInfo");

        request.setAttribute("email", oAuth2UserInfo.getEmail());
        request.setAttribute("provider", oAuth2UserInfo.getProvider());

        return "oauth2_signUp";
    }

    @PostMapping("/oauth2/sign-up")
    public String oauth2SignUp(AddUserRequest dto) {
        userService.registerOAuth2User(dto);

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String signup(HttpServletRequest request, HttpServletResponse response){
        if(userService.logout(request, response)) {
            return "redirect:/login?logout=success";
        }else {
            return "redirect:/login?logout=fail";
        }
    }
}
