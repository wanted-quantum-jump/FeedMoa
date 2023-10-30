package com.skeleton.feed.entity;

import com.skeleton.common.entity.BaseTimeEntity;
import com.skeleton.feed.enums.SnsType;
import jakarta.persistence.*;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String contentId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SnsType type;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post")
    private Set<PostHashtag> postHashtags = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "statistics_id")
    private Statistics statistics;


    private int viewCount = 0;

    private int likeCount = 0;

    private int shareCount = 0;

    // == 비즈니스 로직 == //
    public void addLike() {
        this.likeCount += 1;
    }

    public void addShare() {
        this.shareCount += 1;
    }

    public void addView() {
        this.viewCount += 1;
    }
}

//    public List<String> getHashtagsAsStringList() {
//        return postHashtags.stream()
//                .map(postHashtag -> postHashtag.getHashtag().getName())
//                .toList();
//
//    }

