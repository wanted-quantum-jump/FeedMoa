package com.skeleton.feed.enums;

import com.skeleton.common.util.EnumMapperType;
import lombok.Getter;

@Getter
public enum SnsType implements EnumMapperType {
    FACEBOOK("facebook"),
    TWITTER("twitter"),
    INSTAGRAM("instagram"),
    THREADS("threads");

    private final String value;

    SnsType(String value) {
        this.value = value;
    }
}
