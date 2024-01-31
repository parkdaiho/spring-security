package me.parkdaiho.service;

import lombok.RequiredArgsConstructor;
import me.parkdaiho.domain.User;
import me.parkdaiho.dto.AddUserRequest;
import me.parkdaiho.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
}
