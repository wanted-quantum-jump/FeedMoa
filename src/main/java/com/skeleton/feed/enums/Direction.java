package com.skeleton.feed.enums;

import com.skeleton.common.util.EnumMapperType;
import lombok.Getter;

@Getter
public enum Direction implements EnumMapperType {
    ASC("asc"),
    DESC("desc");

    private final String value;

    Direction(String value) {
        this.value = value;
    }
}
