package com.skeleton.feed.enums;

import com.skeleton.common.util.EnumMapperType;
import lombok.Getter;

@Getter
public enum OrderBy implements EnumMapperType {
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt"),
    LIKE_COUNT("likeCount"),
    SHARE_COUNT("shareCount"),
    VIEW_COUNT("viewCount");

    private final String value;

    OrderBy(String value) {
        this.value = value;
    }
}

