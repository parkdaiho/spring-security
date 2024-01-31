package me.parkdaiho.config.token;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import me.parkdaiho.config.PrincipalDetails;
import me.parkdaiho.domain.User;
import me.parkdaiho.exception.UserNotFoundException;
import me.parkdaiho.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;

    public String generateToken(PrincipalDetails principal, Duration expiredAt) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiredAt.toMillis());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(principal.getUsername())
                .claim("id", principal.getUserId())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public boolean validCheck(String token) {
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
                .orElseThrow(() -> new UserNotFoundException("Unexpected user"));

        PrincipalDetails principal = new PrincipalDetails(user);

        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) principal.getAuthorities();

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(principal.getUsername(), "", authorities), token, authorities
        );
    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);

        return (Long) claims.get("id");
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
