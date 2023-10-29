package com.skeleton.feed.dto;

import com.skeleton.feed.entity.Hashtag;
import com.skeleton.feed.entity.Post;
import com.skeleton.feed.entity.PostHashtag;
import com.skeleton.feed.enums.SnsType;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
public class PostResponse {
    private Long id;
    private String contentId;
    private SnsType type;
    private String title;
    private String content;
    private Set<String> hashtags;
    private int viewCount;
    private int likeCount;
    private int shareCount;

    public static PostResponse fromEntity(Post post, String limitedContent) {
        return PostResponse.builder()
                .id(post.getId())
                .contentId(post.getContentId())
                .type(post.getType())
                .title(post.getTitle())
                .content(limitedContent)
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .shareCount(post.getShareCount())
                .hashtags(post.getPostHashtags()
                        .stream()
                        .map(PostHashtag::getHashtag)
                        .map(Hashtag::getName)
                        .collect(Collectors.toSet()))
                .build();
    }
}
