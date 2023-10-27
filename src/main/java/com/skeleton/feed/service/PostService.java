package com.skeleton.feed.service;

import com.skeleton.common.exception.CustomException;
import com.skeleton.common.exception.ErrorCode;
import com.skeleton.feed.dto.AddLikeResponse;
import com.skeleton.feed.dto.AddShareResponse;
import com.skeleton.feed.entity.Post;
import com.skeleton.feed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 외부 SNS API 호출 서비스, 요구 사항이라서 구현하였으나 실제 유효한 API URL을 사용하지 않으므로, 현재 호출시 오류시 오류 발생
    // private final SnsApiCallerService snsApiCallerService;

    @Transactional
    public AddLikeResponse addLike(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        //snsApiCallerService.clickLikeOnSns(post.getContentId(), post.getType());
        post.addLike();
        return new AddLikeResponse(post);
    }

    @Transactional
    public AddShareResponse addShare(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        //snsApiCallerService.clickShareOnSns(post.getContentId(), post.getType());
        post.addShare();
        return new AddShareResponse(post);
    }
}
