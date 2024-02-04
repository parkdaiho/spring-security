package me.parkdaiho.dto;

import lombok.Getter;
import lombok.Setter;
import me.parkdaiho.domain.OAuth2Provider;

@Getter
@Setter
public class AddUserRequest {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private String provider;
}
