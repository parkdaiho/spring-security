package me.parkdaiho.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, updatable = false, unique = true)
    private String username;

    private String password;

    @Column(length = 20, nullable = false, unique = true)
    private String nickname;

    @Column(updatable = false, nullable = false, unique = true)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private OAuth2Provider provider;

    @Column(nullable = false)
    private Boolean isEnabled;

    @PrePersist
    public void prePersist() {
        this.role = this.role == null ? Role.USER : this.role;
        this.isEnabled = this.isEnabled == null ? true : this.isEnabled;
    }

    @Builder
    public User(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }
}
