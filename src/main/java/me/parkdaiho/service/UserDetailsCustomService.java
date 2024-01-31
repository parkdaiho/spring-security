package me.parkdaiho.service;

import lombok.RequiredArgsConstructor;
import me.parkdaiho.config.PrincipalDetails;
import me.parkdaiho.domain.User;
import me.parkdaiho.exception.UserNotFoundException;
import me.parkdaiho.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsCustomService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return new PrincipalDetails(user);
    }
}
