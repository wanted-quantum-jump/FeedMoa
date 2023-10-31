package com.skeleton.feed.service;

import com.skeleton.common.exception.CustomException;
import com.skeleton.common.exception.ErrorCode;
import com.skeleton.feed.entity.Post;
import com.skeleton.feed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CountService {
    private final PostRepository postRepository;

    @Transactional
    public void incrementViewCount(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        post.addView();
        postRepository.save(post);
    }
}
