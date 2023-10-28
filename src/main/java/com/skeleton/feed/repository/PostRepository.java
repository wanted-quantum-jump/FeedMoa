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
            "WHERE (h.name = :hashtag) " +
            "AND (:type IS NULL OR p.type = :type) " +
            "AND ((:keyword IS NULL) " +
            "OR (:searchInTitle = true AND p.title LIKE %:keyword%) " +
            "OR (:searchInContent = true AND p.content LIKE %:keyword%))")
    Page<Post> findPostsByConditions(
            @Param("hashtag") String hashtag,
            @Param("type") SnsType type,
            @Param("keyword") String keyword,
            @Param("searchInTitle") boolean searchInTitle,
            @Param("searchInContent") boolean searchInContent,
            Pageable pageable);
}

