package com.skeleton.auth.enums;

public enum TokenType {
    REFRESH(60 * 60 * 24 * 1000),
    ACCESS(60 * 60 * 1000);

    private final long expirationTime;

    TokenType(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public long getExpirationTime() {
        return expirationTime;
    }
}