package me.parkdaiho.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.parkdaiho.dto.AddUserRequest;
import me.parkdaiho.service.UserService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
    public String signup(AddUserRequest dto) {
        userService.createUser(dto);

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
