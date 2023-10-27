package com.skeleton.feed.service;

import com.skeleton.common.exception.CustomException;
import com.skeleton.common.exception.ErrorCode;
import com.skeleton.feed.dto.AddLikeResponse;
import com.skeleton.feed.dto.PostResponse;
import com.skeleton.feed.entity.Post;
import com.skeleton.feed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final SnsApiCallerService snsApiCallerService;

    @Transactional
    public AddLikeResponse addLike(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        //외부 SNS API 호출로직, 요구 사항이라서 구현하였으나 실제 유효한 API URL을 사용하지 않으므로 주석 처리해둠. 필요시 주석 해제하고 사용할 것.
        //snsApiCallerService.clickLikeOnSns(post.getContentId(), post.getType());
        post.addlike();
        return new AddLikeResponse(post);
    }

    public PostResponse getPostDetail(Long id) {
        Post post = getPost(id);
        incrementViewCount(id);
        return new PostResponse(post);
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    @Transactional
    public void incrementViewCount(Long postId) {
        Post post = getPost(postId);
        post.addView();
        postRepository.save(post);
    }
}
