package com.skeleton.feed.dto;

import com.skeleton.feed.entity.Post;
import com.skeleton.feed.enums.SnsType;
import java.time.LocalDateTime;
import java.util.List;

public record PostDetailResponse(
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

    public PostDetailResponse(Post post) {
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