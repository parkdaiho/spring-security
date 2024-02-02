package me.parkdaiho.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {

    ADMIN("admin"), MANAGER("manager"), USER("user");

    private final String role;
}
