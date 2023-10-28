package com.skeleton.feed.repository;

import com.skeleton.feed.entity.Post;
import com.skeleton.feed.enums.SnsType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p " +
            "INNER JOIN p.postHashtags ph " +
            "INNER JOIN ph.hashtag h " +
            "WHERE (h.name = :hashtag OR :hashtag IS NULL) " +
            "AND (p.type = :type OR :type IS NULL) " +
            "AND (p.title LIKE %:title% OR :title IS NULL) " +
            "AND (p.content LIKE %:content% OR :content IS NULL)")
    Page<Post> findPostsByConditions(
            @Param("hashtag") String hashtag,
            @Param("type") SnsType type,
            @Param("title") String title,
            @Param("content") String content,
            Pageable pageable);
}

