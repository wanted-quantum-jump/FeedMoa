package com.skeleton.feed.dto;

import com.skeleton.feed.entity.Post;
import lombok.Getter;
import lombok.Setter;


@Getter
public class AddLikeResponse {

    Long id;
    private int likeCount;

    public AddLikeResponse(Post post) {
        this.id = post.getId();
        this.likeCount = post.getLikeCount();
    }
}