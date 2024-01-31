package me.parkdaiho.controller;

import lombok.RequiredArgsConstructor;
import me.parkdaiho.dto.AddUserRequest;
import me.parkdaiho.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class UserViewController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody AddUserRequest dto) {
        userService.createUser(dto);

        return "/";
    }
}
