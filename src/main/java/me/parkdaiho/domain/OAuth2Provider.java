package me.parkdaiho.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OAuth2Provider {

    GOOGLE("google");

    private final String provider;
}
