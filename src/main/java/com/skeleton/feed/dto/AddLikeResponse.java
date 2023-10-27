package com.skeleton.feed.dto;

import com.skeleton.feed.entity.Post;
import lombok.Getter;


@Getter
public class AddLikeResponse {

    private final Long id;
    private final int likeCount;

    public AddLikeResponse(Post post) {
        this.id = post.getId();
        this.likeCount = post.getLikeCount();
    }
}