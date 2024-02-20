package me.parkdaiho.project.config.token;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import me.parkdaiho.project.config.token.JwtProperties;
import me.parkdaiho.project.config.PrincipalDetails;
import me.parkdaiho.project.domain.user.User;
import me.parkdaiho.project.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final UserRepository userRepository;
    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration duration) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setSubject(user.getNickname())
                .setExpiration(new Date(now.getTime() + duration.toMillis()))
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        User user = userRepository.findById(getUserId(token))
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));

        Collection<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole().getAuthority()));

        return new UsernamePasswordAuthenticationToken(new PrincipalDetails(user), token, authorities);
    }

    public Long getUserId(String token) {
        return (Long) Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id");
    }

    public String getUserNickname(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
