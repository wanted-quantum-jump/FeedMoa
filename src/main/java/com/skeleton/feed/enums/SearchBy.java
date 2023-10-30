package com.skeleton.feed.enums;

import com.skeleton.common.util.EnumMapperType;
import lombok.Getter;

@Getter
public enum SearchBy implements EnumMapperType {
    TITLE("title"),
    CONTENT("content"),
    TITLE_CONTENT("title,content");

    private final String value;

    SearchBy(String value) {
        this.value = value;
    }
}
