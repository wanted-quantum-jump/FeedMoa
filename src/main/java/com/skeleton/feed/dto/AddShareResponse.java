package com.skeleton.feed.dto;

import com.skeleton.feed.entity.Post;
import lombok.Getter;


@Getter
public class AddShareResponse {

    private final Long id;
    private final int shareCount;
    public AddShareResponse(Post post) {
        this.id = post.getId();
        this.shareCount = post.getShareCount();
    }
}
