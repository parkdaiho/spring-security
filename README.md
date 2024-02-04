# spring-security

- gradle 설정
```groovy
implementation('org.springframework.boot:spring-boot-starter-security') // 스프링 시큐리티를 사용하기 위한 스타터
implementation('io.jsonwebtoken:jjwt:0.9.1') // 토큰을 이용한 로그인 구현
implementation('javax.xml.bind:jaxb-api:2.3.1') // java 11 이후 버전을 사용하기 때문에 java EE 관련 모듈 추가
implementation('org.springframework.boot:spring-boot-starter-oauth2-client') // OAuth2 로그인 구현
```

- application.yml
```
spring:
  jpa:
    show-sql: true
    properties:
      hibernate:
        format_sql: true
    defer-datasource-initialization: true

  datasource:
    url: jdbc:h2:mem:testdb
    username: sa

  h2:
    console:
      enabled: true

  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ~
            client-secret: ~
            scope:
              - email
              - profile

  mvc:
    view:
      prefix: "/WEB-INF/views/"
      suffix: ".jsp"

    static-path-pattern:

jwt:
  issuer: eogh7204@gmail.com
  secret-key: token-secret-key

server:
  servlet:
    jsp:
      init-parameters:
        development: true
```

- SecurityFilterChain 설정
```java
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable();

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // 토큰 인증을 위한 필터 설정
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

            .authorizationEndpoint() // 서버에 OAuth2 요청 관련
            .baseUri("/oauth2/authorization")
            .authorizationRequestRepository(authorizationRequestRepositoryBasedOnCookie())
            .and()

            .userInfoEndpoint() // OAuth2 요청을 통해 서버에서 전달받은 OAuth2Request 처리 관련
            .userService(oAuth2UserCustomService)
            .and()

            .successHandler(authorizationCustomSuccessHandler())
            .failureHandler(authorizationCustomFailureHandler());

    http.logout()
            .disable();

    return http.build();
}
```

- 모든 Entity 가 공유해야 하는 컬럼은 상속을 이용하자.
```java
@MappedSuperclass // 공통되는 컬럼을 지정하는 조상 클래스에 사용한다.
@EntityListeners(AuditingEntityListener.class) 
public abstract class BaseEntity {

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime modifiedTime;
}
```

- Entity 를 데이터베이스에 저장하기 전에 실행
```java
@PrePersist
public void prePersist() {
    this.role = this.role == null ? Role.USER : this.role;
    this.isEnabled = this.isEnabled == null ? true : this.isEnabled;
}
```

- 열거형 값을 데이터베이스에 문자열로 저장
```java
@Enumerated(value = EnumType.STRING)
private Role role; // Role.USER 은 데이터베이스에 "USER" 로 저장된다.
```

- 쿠키를 생성할 때 path 설정을 해야 하는 것에 주의하자.
```java
public static void addCookie(HttpServletResponse response, String cookieName,
                             String value, int expiry) {
    Cookie cookie = new Cookie(cookieName, value);
    cookie.setPath("/");
    cookie.setMaxAge(expiry);

    response.addCookie(cookie);
}
```

- static resource 경로 설정
```java
@Configuration
public class ResourceConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
    }
}
```

- redirect 페이지 이동이 있을 때, 새로운 request 객체를 생성된다.
```java
public void redirect(HttpServletRequest request, HttpServletResponse response) {
    ...
    request.setAttribute("name", new Value());

    getRedirectStrategy().sendRedirect(request, response, targetUrl);
    // 새로운 request 객체의 생성으로 name 속성은 이동하는 페이지에서 request 객체를 통해 사용할 수 없다.
}

public void forward(HttpServletRequest request, HttpServletResponse response) {
    ...
    request.setAttribute("name", new Value());

    RequestDispatcher dispatcher = request.getRequestDispatcher(REDIRECT_PATH);
    dispatcher.forward(request, response);
    // 새로운 request 객체 생성을 하지 않기 때문에 이동하는 페이지에서도 request 객체를 통해 사용할 수 있다.
} 
```

- request 에 값으로 지정한 객체가 jsp 에서 값을 제대로 불러오기 위해서는 변수의 Getter 메서드가 필요하다.