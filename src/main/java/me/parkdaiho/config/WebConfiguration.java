package me.parkdaiho.config;

import lombok.RequiredArgsConstructor;
import me.parkdaiho.config.oauth2.OAuth2AuthorizationRequestRepositoryBasedOnCookie;
import me.parkdaiho.config.token.TokenProvider;
import me.parkdaiho.repository.RefreshTokenRepository;
import me.parkdaiho.repository.UserRepository;
import me.parkdaiho.service.OAuth2UserCustomService;
import me.parkdaiho.service.UserDetailsCustomService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
public class WebConfiguration {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/js/**", "/css/**", "/img/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=unexpected")

                .usernameParameter("username")
                .passwordParameter("password")

                .successHandler(authorizationCustomSuccessHandler());

        http.oauth2Login()
                .loginPage("/login")

                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(authorizationRequestRepositoryBasedOnCookie())
                .and()

                .userInfoEndpoint()
                .userService(oAuth2UserCustomService())
                .and()

                .successHandler(authorizationCustomSuccessHandler())
                .failureHandler(authorizationCustomFailureHandler());

        http.logout()
                .disable();

        return http.build();
    }

    @Bean
    public AuthorizationCustomFailureHandler authorizationCustomFailureHandler() {
        return new AuthorizationCustomFailureHandler();
    }

    @Bean
    public AuthorizationCustomSuccessHandler authorizationCustomSuccessHandler() {
        return new AuthorizationCustomSuccessHandler(tokenProvider, refreshTokenRepository, authorizationRequestRepositoryBasedOnCookie());
    }

    @Bean
    public OAuth2UserCustomService oAuth2UserCustomService() {
        return new OAuth2UserCustomService(userRepository);
    }

    @Bean
    public OAuth2AuthorizationRequestRepositoryBasedOnCookie authorizationRequestRepositoryBasedOnCookie() {
        return new OAuth2AuthorizationRequestRepositoryBasedOnCookie();
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter(tokenProvider);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                        UserDetailsCustomService userDetailsCustomService,
                                                        BCryptPasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsCustomService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
