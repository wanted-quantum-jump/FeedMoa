package com.skeleton.feed.dto;

import com.skeleton.feed.entity.Hashtag;
import com.skeleton.feed.entity.Post;
import com.skeleton.feed.enums.SnsType;
import java.time.LocalDateTime;
import java.util.List;

public record PostResponse(
        String contentId,
        SnsType type,
        String title,
        String content,
        List<String> hashtags,
        int viewCount,
        int likeCount,
        int shareCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

    public PostResponse(Post post) {
        this(
                post.getContentId(),
                post.getType(),
                post.getTitle(),
                post.getContent(),
                post.getHashtagsAsStringList(),
                post.getViewCount(),
                post.getLikeCount(),
                post.getShareCount(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}