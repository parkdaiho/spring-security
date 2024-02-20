package me.parkdaiho.project.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Provider {
    GOOGLE("GOOGLE"), SELF("SELF");

    private final String provider;
}
