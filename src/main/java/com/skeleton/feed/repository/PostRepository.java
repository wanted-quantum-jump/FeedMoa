package com.skeleton.feed.repository;

import com.skeleton.feed.entity.Post;
import com.skeleton.feed.enums.SnsType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByHashtagAndTypeAndTitleContainingOrContentContaining(
            String hashtag, SnsType type, String title, String content, Pageable pageable);
}
