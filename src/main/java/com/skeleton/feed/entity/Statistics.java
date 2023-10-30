package com.skeleton.feed.entity;

import com.skeleton.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@Entity
@Getter
@Table(name = "post_statistics")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
    @OneToMany(mappedBy = "statistics")
    private Post post;

    @Temporal(TemporalType.TIMESTAMP)
    private Date postTime;

    public int getViewCount() {
        return post.getViewCount();
    }

    public int getLikeCount() {
        return post.getLikeCount();
    }

    public int getShareCount() {
        return post.getShareCount();
    }
}
